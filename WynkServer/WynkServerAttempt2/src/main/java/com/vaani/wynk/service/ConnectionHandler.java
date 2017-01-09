package com.vaani.wynk.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.vaani.wynk.command.Command;

public class ConnectionHandler implements Runnable {

	private String requestString;
	private Socket socket;
	BufferedReader input = null;
	DataOutputStream output = null;
	private Map<String, Command> connectionMap;

	public ConnectionHandler(Socket socket, Map<String, Command> connectionMap) {
		this.socket = socket;
		this.connectionMap = connectionMap;
	}

	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			requestString = input.readLine();
			System.out
					.println("Inside ConnectionHandler:call(), request string:"
							+ requestString);
			Command command = CommandResolver.resolveCommand(socket,
					requestString, connectionMap);
			command.execute(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
