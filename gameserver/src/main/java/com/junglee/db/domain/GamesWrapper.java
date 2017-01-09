package com.junglee.db.domain;

import java.util.List;

public class GamesWrapper {
	private List<Game> games;

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public GamesWrapper(List<Game> games) {
		this.games = games;
	}
}
