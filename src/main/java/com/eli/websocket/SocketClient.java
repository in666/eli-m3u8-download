package com.eli.websocket;

import javax.websocket.Session;
import java.io.Serializable;

public class SocketClient implements Serializable {

    private static final long serialVersionUID = 8957107006902627635L;

    private String username;

    private Session session;

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SocketClient(String username, Session session) {
        this.username = username;
        this.session = session;
    }

    public SocketClient() {
    }
}
