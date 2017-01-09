package com.junglee.db.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	static int id = 1;
	private static int MAX_PLAYERS = 3;

	private List<String> players = Collections
			.synchronizedList(new ArrayList<String>());

	public enum GameState {
		WAITING_FOR_PLAYERS, IN_GAME, FINISHED
	}

	private int gameid;
	private GameState gamestate = GameState.WAITING_FOR_PLAYERS;

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public GameState getGamestate() {
		return gamestate;
	}

	public void setGamestate(GameState gamestate) {
		this.gamestate = gamestate;
	}

	public Game(String username) {
		gameid = ++id;
		addPlayer(username);
	}

	public void addPlayer(String username) {
		if (players.size() == MAX_PLAYERS) {
			throw new RuntimeException("Max players reached");
		}
		players.add(username);
		if (players.size() == MAX_PLAYERS) {
			gamestate = GameState.IN_GAME;
		}
	}

	public void removePlayer(String username) {
		players.remove(username);
		if (players.size() < MAX_PLAYERS) {
			gamestate = GameState.WAITING_FOR_PLAYERS;
		}

		if (players.size() == 0) {
			gamestate = GameState.FINISHED;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(gameid + ":").append(players);
		return sb.toString();
	}

}
