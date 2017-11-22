/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.jthinking.deploy.web.websocket;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/websocket/echo")
public class EchoAnnotation {

    private static final Log log = LogFactory.getLog(EchoAnnotation.class);

    private static final String ADMIN_PREFIX = "Admin";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Set<EchoAnnotation> connections = new CopyOnWriteArraySet<EchoAnnotation>();

    private final String nickname;
    private String project;
    private String module;
    private Session session;

    public EchoAnnotation() {
        nickname = ADMIN_PREFIX + connectionIds.getAndIncrement();
    }


    @OnOpen
    public void start(Session session) {
        project = getRequestParameterValue(session, "project");
        module = getRequestParameterValue(session, "module");
        this.session = session;
        connections.add(this);
        String message = String.format("%s %s", nickname, "has joined.");
        broadcast(message, project, module);
    }

    private String getRequestParameterValue(Session session, String parameterName) {
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        for (Map.Entry<String, List<String>> requestParameter : requestParameterMap.entrySet()) {
            if (requestParameter.getKey().equals(parameterName)) {
                List<String> value = requestParameter.getValue();
                if (value == null || value.isEmpty()) {
                    return null;
                } else {
                    return value.get(0);
                }
            }
        }
        return null;
    }


    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("%s %s", nickname, "has disconnected.");
        broadcast(message, project, module);
    }


    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        String filteredMessage = String.format("%s", message.toString());
        broadcast(filteredMessage, project, module);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("Chat Error: " + t.toString(), t);
    }

    private static void broadcast(String msg, String project, String module) {
        for (EchoAnnotation client : connections) {
            try {
                synchronized (client) {
                    if (client.project == null) continue;
                    if (client.module == null) continue;
                    if (client.project.equals(project) && client.module.equals(module)) {
                        client.session.getBasicRemote().sendText(msg);
                    }
                }
            } catch (IOException e) {
                log.debug("Chat Error: Failed to send message to client", e);
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("%s %s", client.nickname, "has been disconnected.");
                broadcast(message, project, module);
            }
        }
    }
}
