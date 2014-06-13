/*
 * Copyright (C) 2011 ritwik.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.torben.androidchat.JSONRPC.server;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.torben.androidchat.JSONRPC.commons.JsonRpcErrorCodes;
import com.torben.androidchat.JSONRPC.commons.JsonRpcException;
import com.torben.androidchat.JSONRPC.commons.JsonRpcRemoteException;
import com.torben.androidchat.JSONRPC.commons.RpcIntroSpection;
import com.torben.androidchat.JSONRPC.commons.TypeChecker;


public final class JsonRpcExecutor implements RpcIntroSpection {

    private static final Pattern METHOD_PATTERN = Pattern
            .compile("([_a-zA-Z][_a-zA-Z0-9]*)\\.([_a-zA-Z][_a-zA-Z0-9]*)");

    private final Map<String, HandleEntry<?>> handlers;

    private final TypeChecker typeChecker;
    private volatile boolean locked;

    @SuppressWarnings("unchecked")
    public JsonRpcExecutor(TypeChecker typeChecker) {
        this.typeChecker = typeChecker;
        this.handlers = new HashMap<String, HandleEntry<?>>();
        addHandler("system", this, RpcIntroSpection.class);
    }

    public boolean isLocked() {
        return locked;
    }

    public <T> void addHandler(String name, T handler, Class<T>... classes) {
        if (locked) {
            throw new JsonRpcException("executor has been locked, can't add more handlers");
        }

        synchronized (handlers) {
            HandleEntry<T> handleEntry = new HandleEntry<T>(typeChecker, handler, classes);
            if (this.handlers.containsKey(name)) {
                throw new IllegalArgumentException("handler already exists");
            }
            this.handlers.put(name, handleEntry);
        }
    }

    public void execute(JsonRpcServerTransport transport) throws JSONException {
        if (!locked) {
            synchronized (handlers) {
                locked = true;
            }
        }

        String methodName = null;
        JSONArray params = null;

        JSONObject resp = new JSONObject();
        resp.put("jsonrpc", "2.0");

        String errorMessage = null;
        Integer errorCode = null;
        String errorData = null;

        JSONObject req = null;
        try {
            String requestData = transport.readRequest();
            req = new JSONObject(requestData);
        } catch (Throwable t) {
            errorCode = JsonRpcErrorCodes.PARSE_ERROR_CODE;
            errorMessage = "unable to parse json-rpc request";
            errorData = getStackTrace(t);

            sendError(transport, resp, errorCode, errorMessage, errorData);
            return;
        }


        try {
            assert req != null;
            resp.put("id", req.get("id"));

            methodName = req.getString("method");
            params = (JSONArray) req.get("params");
            if (params == null) {
                params = new JSONArray();
            }
        } catch (Throwable t) {
            errorCode = JsonRpcErrorCodes.INVALID_REQUEST_ERROR_CODE;
            errorMessage = "unable to read request";
            errorData = getStackTrace(t);

            sendError(transport, resp, errorCode, errorMessage, errorData);
            return;
        }

        try {
            Object result = executeMethod(methodName, params);
            resp.put("result", result);
        } catch (Throwable t) {
            if (t instanceof JsonRpcRemoteException) {
                sendError(transport, resp, (JsonRpcRemoteException) t);
                return;
            }
            errorCode = JsonRpcErrorCodes.getServerError(1);
            errorMessage = t.getMessage();
            errorData = getStackTrace(t);
            sendError(transport, resp, errorCode, errorMessage, errorData);
            return;
        }

        try {
            String responseData = resp.toString();
            transport.writeResponse(responseData);
        } catch (Exception e) {
        }
    }

    private void sendError(JsonRpcServerTransport transport, JSONObject resp, JsonRpcRemoteException e) {
        //sendError(transport, resp, e.getCode(), e.getMessage(), e.getData());
    }

    private void sendError(JsonRpcServerTransport transport, JSONObject resp, Integer code, String message, String data) throws JSONException {
        JSONObject error = new JSONObject();
        if (code != null) {
            error.put("code", code);
        }

        if (message != null) {
            error.put("message", message);
        }

        if (data != null) {
            error.put("data", data);
        }

        resp.put("error", error);
        resp.remove("result");
        String responseData = resp.toString();

        try {
            transport.writeResponse(responseData);
        } catch (Exception e) {
        }
    }

    private String getStackTrace(Throwable t) {
        StringWriter str = new StringWriter();
        PrintWriter w = new PrintWriter(str);
        t.printStackTrace(w);
        w.close();
        return str.toString();
    }

    private Object executeMethod(String methodName, JSONArray params) throws Throwable {
    	try {
            Matcher mat = METHOD_PATTERN.matcher(methodName);
            if (!mat.find()) {
                throw new JsonRpcRemoteException(JsonRpcErrorCodes.INVALID_REQUEST_ERROR_CODE, "invalid method name", null);
            }

            String handleName = mat.group(1);
            methodName = mat.group(2);

            HandleEntry<?> handleEntry = handlers.get(handleName);
            if (handleEntry == null) {
                throw new JsonRpcRemoteException(JsonRpcErrorCodes.METHOD_NOT_FOUND_ERROR_CODE, "no such method exists", null);
            }

            Method executableMethod = null;
            for (Method m : handleEntry.getMethods()) {
                if (!m.getName().equals(methodName)) {
                    continue;
                }

                if (canExecute(m, params)) {
                    executableMethod = m;
                    break;
                }
            }

            if (executableMethod == null) {
                throw new JsonRpcRemoteException(JsonRpcErrorCodes.METHOD_NOT_FOUND_ERROR_CODE, "no such method exists", null);
            }

            Object result = executableMethod.invoke(
                    handleEntry.getHandler(), getParameters(executableMethod, params));

            return result;
        } catch (Throwable t) {
            if (t instanceof InvocationTargetException) {
                t = ((InvocationTargetException) t).getTargetException();
            }
            if (t instanceof JsonRpcRemoteException) {
                throw (JsonRpcRemoteException) t;
            }
            throw new JsonRpcRemoteException(JsonRpcErrorCodes.getServerError(0), t.getMessage(), getStackTrace(t));
        }
    }

    public boolean canExecute(Method method, JSONArray params) {
        if (method.getParameterTypes().length != params.length()) {
            return false;
        }

        return true;
    }

    public Object[] getParameters(Method method, JSONArray params) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        Class<?>[] types = method.getParameterTypes();
        for (int i = 0; i < types.length; i++) {
            list.add(params.get(i));

        }
        return list.toArray();
    }

    public String[] listMethods() {
        Set<String> methods = new TreeSet<String>();
        for (String name : this.handlers.keySet()) {
            HandleEntry<?> handleEntry = this.handlers.get(name);
            for (String method : handleEntry.getSignatures().keySet()) {
                methods.add(name + "." + method);
            }
        }
        String[] arr = new String[methods.size()];
        return methods.toArray(arr);
    }

    public String[] methodSignature(String method) {
        if (method == null) {
            throw new NullPointerException("method");
        }

        Matcher mat = METHOD_PATTERN.matcher(method);
        if (!mat.find()) {
            throw new IllegalArgumentException("invalid method name");
        }

        String handleName = mat.group(1);
        String methodName = mat.group(2);

        Set<String> signatures = new TreeSet<String>();

        HandleEntry<?> handleEntry = handlers.get(handleName);
        if (handleEntry == null) {
            throw new IllegalArgumentException("no such method exists");
        }

        for (Method m : handleEntry.getMethods()) {
            if (!m.getName().equals(methodName)) {
                continue;
            }

            String[] sign = handleEntry.getSignatures().get(m.getName());

            StringBuffer buff = new StringBuffer(sign[0]);
            for (int i = 1; i < sign.length; i++) {
                buff.append(",").append(sign[i]);
            }

            signatures.add(buff.toString());
        }

        if (signatures.size() == 0) {
            throw new IllegalArgumentException("no such method exists");
        }

        String[] arr = new String[signatures.size()];
        return signatures.toArray(arr);
    }


}
