package com.vaani.wynk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.StringTokenizer;

import com.vaani.wynk.command.Command;
import com.vaani.wynk.command.KillCommand;
import com.vaani.wynk.command.ServerStatusCommand;
import com.vaani.wynk.command.SleepCommand;
import com.vaani.wynk.command.UnSupportedCommand;

public class CommandResolver {

	public static Command resolveCommand(Socket socket, String requestString,
			Map<String, Command> connectionMap) throws IOException {
		System.out.println("Inside CommandResolver:resolveCommand()");
		System.out.println("requestString:" + requestString);
		StringTokenizer tokenizer = new StringTokenizer(requestString);
		String httpMethod = tokenizer.nextToken();
		String httpQueryString = tokenizer.nextToken();
		if (!httpMethod.equalsIgnoreCase("GET")) {
			return new UnSupportedCommand(socket);
		}
		System.out.println("QueryString:" + httpQueryString);
		Command command = parseQuery(socket, httpQueryString);
		command.setConnectionMap(connectionMap);
		return command;
	}

	private static Command parseQuery(Socket socket, String httpQueryString) {
		String params[] = httpQueryString.split("\\?");
		System.out.println("params length:" + params.length);
		String commandString = null;
		Command command = null;
		if (params != null && params.length > 0) {
			commandString = params[0];
			System.out.println("commandString:" + commandString);
			if (commandString.equalsIgnoreCase("/sleep")) {
				command = new SleepCommand(socket);
			} else if (commandString.equalsIgnoreCase("/server-status")) {
				command = new ServerStatusCommand(socket);
			} else if (commandString.equalsIgnoreCase("/kill")) {
				command = new KillCommand(socket);
			} else {
				command = new UnSupportedCommand(socket);
			}
		}
		if (params.length > 1) {
			String rest = params[1];
			String otherParams[] = rest.split("&");
			if (otherParams != null && otherParams.length > 0) {
				System.out.println("otherParams:" + otherParams);
				for (String string : otherParams) {
					System.out.println("iterating otherParams:" + string);
					String paramValue[] = string.split("=");
					if (paramValue != null && paramValue.length == 2) {
						command.addParam(paramValue[0], paramValue[1]);
					}
				}
			}
		}

		return command;
	}

}
