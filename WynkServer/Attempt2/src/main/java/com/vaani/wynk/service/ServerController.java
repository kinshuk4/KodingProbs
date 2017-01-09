package com.vaani.wynk.service;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.vaani.wynk.command.Command;

public class ServerController {

	private static ScheduledExecutorService executorService;

	public ServerController() {

	}

	private static Map<String, Command> connectionMap = new HashMap<String, Command>();

	public static void main(String[] args) throws Exception {
		ServerSocket Server = new ServerSocket(9090, 10,
				InetAddress.getByName("127.0.0.1"));
		System.out.println("Server waiting for client on port 9090");
		executorService = Executors.newScheduledThreadPool(2);
		while (true) {
			Socket socket = Server.accept();
			ConnectionHandler connectionHandler = new ConnectionHandler(socket,
					connectionMap);
			executorService.execute(connectionHandler);
		}
	}
}
