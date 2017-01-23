package com.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;



public class WebServer extends Thread {
	
	/**
	 * This method is invoked on HTTP GET/sleep?timeout=20&connid=1
	 * 
	 * @param paramMap
	 * @param clientSocket
	 * 
	 * You can use the following method to write to a socket - 
	 * PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
	 * 
	 * You need to write to the output after the specified "timeout" amount of seconds. 
	 * 
	 * You will need to close the clientSocket in order to respond to the client
	 * 
	 * NOTE - Your implementation needs to be able to handle multiple client connecting simultaneously.
	 */
	private ConcurrentHashMap<String, WebServer> connectionMap  = new ConcurrentHashMap<>();
	private AtomicInteger timeLeft;
	PrintWriter output ;
	private AtomicBoolean killed = new AtomicBoolean(false);
	public void implementSleepRequest(Map<String, String> paramMap, Socket clientSocket) {
		/*
		 * You need to Retrieve connid and timeout from paramsMap and implement
		 * the handling for the GET /sleep method
		 */

		// Your code goes here.
		this.connectionMap.put(paramMap.get("connid"), this);
		timeLeft = new AtomicInteger(Integer.parseInt(paramMap.get("timeout")));
		while (timeLeft.get()>0) {
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			timeLeft.decrementAndGet();
		}
		connectionMap.remove(paramMap.get("connid"));
		
		try {
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			if (killed.get()) {
				output.write("{\"stat\":\"killed\"}");
			}else {
				output.write("{\"stat\":\"ok\"}");
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called when the request is GET /server­status
	 * 
	 * Returns the status of all the connections currently connected and their
	 * time left to sleep
	 * @param clientSocket 
	 */
	//
	public void implementGetServerStatusRequest(Socket clientSocket) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			for (String key : this.connectionMap.keySet()) {
				WebServer ws = connectionMap.get(key);
				int timeleft = ws.timeLeft.get();
				map.put(key, timeleft);
			}
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			output.write(map.toString());
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method is called when the request is POST /kill
	 * 
	 * Kills a given connection id
	 * 
	 * @param requestBody
	 * @param clientSocket
	 */
	public void implementKillConnectionRequest(String requestBody, Socket clientSocket) {
		/*
		 * You need to retrieve connid from the requestBody and kill the
		 * corresponding connection.
		 */
		
		// Your code goes here.
		String connid = requestBody.replace("connid=", "");
		WebServer ws =  this.connectionMap.get(connid);
		if (ws == null) {
			try {
				output = new PrintWriter(clientSocket.getOutputStream(), true);
				output.write("connection not found");
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		this.killed = new AtomicBoolean(true);
		try {
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			output.write("{\"stat\":\"killed\"}");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callMethod(WebServerRequestBody webServerRequestBody, Socket clientSocket) {
		if (WebServerUtils.isPostRequest(webServerRequestBody)) {
			if (webServerRequestBody.getRequestUri() != null && webServerRequestBody.getRequestUri().contains("kill")) {
				implementKillConnectionRequest(webServerRequestBody.getRequestBody(), clientSocket);
			}
		} else if (WebServerUtils.isGetRequest(webServerRequestBody)) {
			if (webServerRequestBody.getRequestUri() != null
					&& webServerRequestBody.getRequestUri().contains("sleep")) {
				implementSleepRequest(webServerRequestBody.getRequestParams(), clientSocket);
			} else if (webServerRequestBody.getRequestUri() != null
					&& webServerRequestBody.getRequestUri().contains("server­status")) {
				implementGetServerStatusRequest(clientSocket);
			}
		}
	}
	public static void main(String[] args) throws IOException {
		WebServer webServer = new WebServer();
		int port = 8080;
		ServerSocket server = new ServerSocket(port);
		while (true) {
			// Create the Web server
			// Parse the Web request and populate the WebServerRequestBody
			// object
			/*
			 * WebServerRequestBody.java is present in the same folder which
			 * getter methods for the following attributes 
			 * private String method;
			 * private Map<String, String> requestParams; 
			 * private String requestBody; 
			 * private String requestUri;
			 * 
			 */
			
			Socket clientSocket = server.accept();
			InputStream input = clientSocket.getInputStream();
			WebServerRequestBody webServerRequestBody = WebServerUtils.parseRequest(input);
			
			// Handle the Web request
			webServer.callMethod(webServerRequestBody, clientSocket);
		}
		
	}
}