package com.junglee.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JungleeWebSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 5610501345675935366L;
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(JungleeWebSocketServlet.class);
	public static final Set<ConnectionManager> users = new CopyOnWriteArraySet<ConnectionManager>();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getNamedDispatcher("default").forward(request,
				response);
	}

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		return new ConnectionManager(users);
	}

}
