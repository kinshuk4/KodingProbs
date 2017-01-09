# Game server bootstrap

A game server bootstrap. 
Multiple clients can connect to the server, game created by any client would be visible to all the servers. Initially the game state would be WAITING_FOR_PLAYERS, as soon as the players count in a game reaches 3, the game state would change to IN_GAME. If all players leave the game, game would be finished and removed from the stack

Technologies Used - 
 - Server - Jetty, Hibernate, MySQL
 - Client - HTML, bootstrap, jQuery, websockets, json

  - Jetty application server (No need to install, embedded)
  - Maven required to build
  - MySQL required to install
  - Uses hibernate to store information in database
  - Uses jetty for client/server communication


To get you up and running with the application, storing information in MYSQL database using hibernate. This is a minimalistic project with small memory footprint and doesn't use spring or any other container.    
The application accepts messages from client via websockets and store those in database.   


### Installation & running
1. Install mysql and create a database 'test'. 
2. Run the script.sql in db folder
3. Configure the database credentials in hibernate.cfg.xml
4. Build the project
```sh
$ cd gameserver
$ mvn clean install
```
5. Run 
```sh
$ mvn -Djetty.port=9999 jetty:run 
```
6. Check the magic on http://localhost:9999/

Demonstration video 

[![GameServer Demo](http://img.youtube.com/vi/kf7m1-Skm1I/0.jpg)](http://www.youtube.com/watch?v=kf7m1-Skm1I "GameServer demo")


