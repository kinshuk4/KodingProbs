package com.goeuro.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.goeuro.dao.DataProviderFactory;
import com.goeuro.dao.FileDataProvider;
import com.goeuro.dao.IDataProvider;
import com.goeuro.model.BusStop;
import com.goeuro.model.response.Response;

@Service
@EnableAutoConfiguration
public class BusRouteService {

	/**
	 * logger for logging
	 */
	final static Logger logger = Logger.getLogger(BusRouteService.class);

    public Response isThereDirectRoute(int departureStationId, int arrivalStationId) {		
        try {        	
        	BusStop departure = BusStop.of(departureStationId);
        	BusStop arrival = BusStop.of(arrivalStationId);
        	boolean isThereDirectBusRoute = DataProviderFactory.getDataProvider().fetch().stream()
                    .anyMatch(x -> matchBusStops(x, departure, arrival));
            return new Response(departureStationId, arrivalStationId, isThereDirectBusRoute);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean matchBusStops(List<BusStop> busStops, BusStop departure, BusStop arrival) {
    	int indexOfDeparture = busStops.indexOf(departure);
		int indexOfArrival = busStops.indexOf(arrival);

		if (indexOfDeparture != -1 && indexOfArrival != -1 && indexOfDeparture < indexOfArrival) {
			
			logger.debug(String.format("Found route for departureStationId = %s and arrivalStationId = %s",departure.toString(), arrival.toString() ));
			return true;
		}
		return false;
    }
    
    @Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}