package com.goeuro.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.goeuro.dao.DataProviderType;

@SpringBootApplication
public class Application {
	final static Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("Application Started !. Setting the command line configurations");
		BusRoutesChallengeConfiguration.getInstance().setBusRoutesFile(args[0]);
		BusRoutesChallengeConfiguration.getInstance().setDataProviderType(DataProviderType.FILE);
		SpringApplication.run(Application.class, args);
	}

}