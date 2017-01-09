package com.vaani.wynk.command;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

public class UnSupportedCommand extends Command {

	public UnSupportedCommand(Socket socket) {
		super(socket);
		commandName = "unsupported";
	}

	@Override
	public String execute(Socket socket) {
		try {
			output.writeBytes((new Gson()).toJson(new Response("unsupported")));
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
