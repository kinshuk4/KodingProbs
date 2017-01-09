package com.junglee.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.junglee.db.domain.Game;
import com.junglee.db.domain.GamesWrapper;
import com.junglee.db.domain.User;
import com.junglee.db.domain.Game.GameState;


public class ConnectionManager implements OnTextMessage {

	private Connection connection;
	private Set<ConnectionManager> connections;
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(ConnectionManager.class);
	private static String GUEST = "guest";
	private static Gson gson = new Gson();
	private static Map<String, Game> userGame = new ConcurrentHashMap<String, Game>();
	private static List<Game> games = Collections
			.synchronizedList(new ArrayList<Game>());
	// private List<User> users = Collections
	// .synchronizedList(new ArrayList<User>());
	private static Map<String, User> userMap = new ConcurrentHashMap<String, User>();
	private static Map<Integer, Game> gameMap = new ConcurrentHashMap<Integer, Game>();

	public ConnectionManager() {

	}

	public ConnectionManager(Set<ConnectionManager> users) {
		this.connections = users;
	}

	public void onMessage(String data) {
		logger.debug("onMessage connection:" + connection.toString()
				+ ", data:" + data);
		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(data).getAsJsonObject();
			// String command = gson.fromJson(obj.get("command"), String.class);
			Command command = Command.valueOf(gson.fromJson(obj.get("command"),
					String.class));
			String username = null;
			Integer gameid = null;
			Game game = null;
			User user = null;
			switch (command) {
			case login:
				username = gson.fromJson(obj.get("username"), String.class);
				user = new User(username);
				userMap.put(username, user);
				broadcastGames();
				break;
			case creategame:
				username = gson.fromJson(obj.get("username"), String.class);
				game = new Game(username);
				user = userMap.get(username);
				userGame.put(username, game);
				games.add(game);
				gameMap.put(game.getGameid(), game);
				broadcastGames();
				break;
			case joingame:
				gameid = gson.fromJson(obj.get("gameid"), Integer.class);
				username = gson.fromJson(obj.get("username"), String.class);
				game = gameMap.get(gameid);
				game.addPlayer(username);
				userGame.put(username, game);
				broadcastGames();
				break;
			case exitgame:
				gameid = gson.fromJson(obj.get("gameid"), Integer.class);
				username = gson.fromJson(obj.get("username"), String.class);
				game = gameMap.get(gameid);
				game.removePlayer(username);
				if (game.getGamestate() == GameState.FINISHED) {
					games.remove(game);
					userGame.remove(username);
					gameMap.remove(game.getGameid());
				}
				broadcastGames();
				break;
			default:
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void broadcastGames() {
		sendObject(new GamesWrapper(games));
	}

	void sendObject(Object obj) {
		try {
			JsonElement jsonElement = gson.toJsonTree(obj);
			jsonElement.getAsJsonObject().addProperty("clazz",
					obj.getClass().getSimpleName());
			for (ConnectionManager connectionManager : connections) {
				connectionManager.getConnection().sendMessage(
						gson.toJson(jsonElement));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onOpen(Connection connection) {
		logger.debug("onOpen called, users count before:" + connections.size());
		this.connection = connection;
		this.connection.setMaxIdleTime(Integer.MAX_VALUE);
		connections.add(this);

	}

	@Override
	public void onClose(int closeCode, String message) {
		connections.remove(this);
	}

	public Connection getConnection() {
		return connection;
	}
}
