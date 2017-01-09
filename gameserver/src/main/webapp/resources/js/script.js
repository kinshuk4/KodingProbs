var username;
var mygame;
$(document).ready(function(){
	initializeWebSocket();
	initializePage();
	$("#login").click(function(){
		console.log("login clicked");
		username = $("#username").val();
		console.log("user:" + username);
		if (!username){
			alert("Please enter a username");
			return;
		}
		var obj = {};
		obj.command="login";
		obj.username = username;
		send(obj);
		$("#creategamediv").show();
		$("#logindiv").html("Welcome " + username);
		$("#beforelogin").hide();
		$("#afterlogin").show();
	});

	$("#creategame").click(function(){
		console.log("creategame clicked");
		var obj = {};
		obj.command="creategame";
		obj.username = username;
		$("#creategamediv").hide();
		send(obj);
	});
});

function initializePage(){
	$("#creategamediv").hide();
	$("#beforelogin").show();
	$("#afterlogin").hide();
}

function send(obj) {
	console.log(JSON.stringify(obj));
	ws.send(JSON.stringify(obj));
		
}

function processResponse(str) {
	var obj = eval("(" + str + ")");
	if (obj.clazz === "GamesWrapper") {
		var html = '<table class="table">';
		html = html + "<thead><td>Game</td><td>Players</td><td>Game state</td><td>Action</td></thead>"
		console.log("games received");
		console.log(obj);
		mygame = "";

		$.each(obj.games, function(index, value){
			html = html + "<tr>"
			html = html + "<td>" + value.gameid + "</td>";
			html = html + "<td>" + value.players + "</td>";
			html = html + "<td>" + value.gamestate + "</td>";
			html = html + "<td>";
			if ($.inArray(username, value.players) > -1) {
				console.log("My game is :" + value.gameid);
				mygame = value.gameid;
				html = html + '<a class="leave" onclick="leavegame(' + value.gameid + ')">leave</a>';
			}else {				
				html = html + '<a class="join" onclick="joingame(' + value.gameid + ')">join</a>';
			}
			html = html + "</td></tr>"
		});
		html = html + "</table>";
		$("#games").html(html);
	}
}

function joingame(gameid){
	console.log('joingame:' + gameid);
	var obj = {};
		obj.command="joingame";
		obj.username = username;
		obj.gameid = gameid;
		send(obj);
}

function leavegame(gameid){
	console.log('leavegame:' + gameid);
	var obj = {};
		obj.command="exitgame";
		obj.username = username;
		obj.gameid = gameid;
		send(obj);
		$("#creategamediv").show();
}

function initializeWebSocket() {
		if (!("WebSocket" in window)) {
			alert("The browser does not support websockets. Please install latest chrome or firefox.");
			return;
		}
		var wsURL = "ws://localhost:9999/sample/anything";
		ws = new WebSocket(wsURL);
		ws.onopen = function(event) {
			// ws.send("Hi there from client !!");
		}
		ws.onmessage = function(event) {
			console.log(event.data);
			processResponse(event.data);						
		}
		ws.onclose = function(event) {
			console.log("Lost connection to server");
		}
	}
