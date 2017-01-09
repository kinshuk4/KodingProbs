package com.vaani.wynk.command;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

public class SleepCommand extends Command {

	private int timeLeft;
	boolean killed = false;

	public SleepCommand(Socket socket) {
		super(socket);
		commandName = "sleep";
		this.killed = false;
	}

	@Override
	public String execute(Socket socket) {
		this.connectionMap.put(paramsMap.get("connid"), this);
		timeLeft = Integer.parseInt(paramsMap.get("timeout"));
		while (timeLeft > 0) {
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			timeLeft--;
		}
		connectionMap.remove(paramsMap.get("connid"));
		try {
			if (killed) {
				output.writeBytes((new Gson()).toJson(new Response("killed")));
			}else {
				output.writeBytes((new Gson()).toJson(new Response("ok")));
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public void kill() {
		this.timeLeft = 0;
		this.killed = true;
	}

}
