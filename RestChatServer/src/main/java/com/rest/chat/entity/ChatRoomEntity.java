package com.rest.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ChatRoomEntity")
public class ChatRoomEntity {

	@Id
	@Column(name = "roomId", unique = true, nullable = false)
	private long roomId;
	
	@Column(name="roomName")
	private String roomName;

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
