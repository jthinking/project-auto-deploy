package com.jthinking.deploy.service;

import com.jthinking.deploy.web.websocket.EchoClientEndpoint;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * WebSocket工具类
 * @author JiaBochao
 * @version 2017-11-22 10:00:35
 */
public class WebSocketManager {

    private WebSocketContainer container;
    private URI uri;
    private Session session;

    /**
     * @param url e.g. ws://localhost:8420/websocket/echo
     */
    public WebSocketManager(String url) {
        try {
            container = ContainerProvider.getWebSocketContainer();
            uri = URI.create(url);
            session = container.connectToServer(EchoClientEndpoint.class, uri);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
