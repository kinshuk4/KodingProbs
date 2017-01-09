package com.vaani.wynk.command;

public class Response {
	private String stat;

	public Response(String stat) {
		this.stat = stat;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
}
