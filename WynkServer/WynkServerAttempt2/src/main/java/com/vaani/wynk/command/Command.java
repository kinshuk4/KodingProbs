package com.vaani.wynk.command;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class Command {

	protected String commandName = "BLANK";
	protected Map<String, String> paramsMap = new HashMap<String, String>();
	protected Map<String, Command> connectionMap = new HashMap<String, Command>();

	protected DataOutputStream output;
	protected Socket socket;

	public Command(Socket socket) {
		this.socket = socket;
		System.out.println("Constructor of abstract command");
		try {
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract String execute(Socket socket);

	public void addParam(String key, String value) {
		System.out.println("Command:" + commandName + ", adding:" + key + "="
				+ value);
		paramsMap.put(key, value);
	}

	public Map<String, Command> getConnectionMap() {
		return connectionMap;
	}

	public void setConnectionMap(Map<String, Command> connectionMap) {
		this.connectionMap = connectionMap;
	}

}
