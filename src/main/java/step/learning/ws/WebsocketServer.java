package step.learning.ws;


import com.google.inject.Inject;
import step.learning.dao.ChatDao;
import step.learning.dto.entities.ChatMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(
        value = "/chat", // url
        configurator = WebsocketConfigurator.class
)

public class WebsocketServer
{
    private final static Set<Session> sessions =
            Collections.synchronizedSet(new HashSet<>());
    private final ChatDao chatDao;

    @Inject
    public WebsocketServer(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @OnOpen
    public void onOpen( Session session, EndpointConfig sec )
    {
        //chatDao.install();
        session.getUserProperties().put(
                "user",
                sec.getUserProperties().get("user")
        );
        sessions.add(session);
    }
    @OnClose
    public void onClose( Session session )
    {
        sessions.remove(session);
    }

    ExecutorService executor = Executors.newCachedThreadPool();

    @OnMessage
    public void onMessage( String message, Session session )
    {
        String user = session.getUserProperties().get("user").toString();
        executor.execute(() ->
        {
            chatDao.add(new ChatMessage(user, message));
        });
        sendToAll(user + ": " + message);

//        sendToAll(session.getUserProperties().get("user") + ": " + message);
    }
    @OnError
    public void onError( Throwable ex, Session session )
    {
        System.err.println("onError : " + ex.getMessage());
    }

    private void sendToAll( String message )
    {
        for (Session session : sessions)
        {
            try
            {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException ex)
            {
                System.err.println("sendToAll : " + ex.getMessage());
            }
        }
    }
}
