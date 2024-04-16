package com.gene.IM.util.WebSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket") //暴露的ws应用的路径
public class WebSocket {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New session opened: " + session.getId());
    }

    /**
     * 向所有客户端发送消息
     *
     * @param message 消息内容
     * @throws IOException
     */

    public void sendMessage(String message) throws IOException {
        for (Session session : sessions) {
            synchronized (session)
            {
                session.getBasicRemote().sendText(message);
            }
        }
    }
}

