package com.vaani.wynk.command;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

public class KillCommand extends Command {

	public KillCommand(Socket socket) {
		super(socket);
		commandName = "kill";
	}

	@Override
	public String execute(Socket socket) {
		String connid = paramsMap.get("connid");
		SleepCommand command = (SleepCommand) this.connectionMap.get(connid);
		if (command == null) {
			try {
				output.writeBytes((new Gson()).toJson(new Response(
						"connection not found")));
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		command.kill();
		try {
			output.writeBytes((new Gson()).toJson(new Response("ok")));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
