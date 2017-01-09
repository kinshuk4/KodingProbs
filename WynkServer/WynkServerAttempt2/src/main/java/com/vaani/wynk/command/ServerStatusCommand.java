package com.vaani.wynk.command;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class ServerStatusCommand extends Command {

	public ServerStatusCommand(Socket socket) {
		super(socket);
		commandName = "server-status";
	}

	@Override
	public String execute(Socket socket) {
		System.out.println("Inside ServerStatusCommand:execute()");
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			for (String key : this.connectionMap.keySet()) {
				SleepCommand command = (SleepCommand) connectionMap.get(key);
				int timeleft = command.getTimeLeft();
				map.put(key, timeleft);
			}
			output.writeBytes((new Gson()).toJson(map));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
