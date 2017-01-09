package com.goeuro.dao;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goeuro.controller.BusRoutesChallengeConfiguration;
import com.goeuro.model.BusStop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Repository
public class FileDataProvider implements IDataProvider {

    private static final String DELIMITER = " ";
    private Path path;
    final static Logger logger = Logger.getLogger(FileDataProvider.class);

    private List<List<BusStop>> routes;
    private static class SingletonHelper{
        private static final FileDataProvider INSTANCE = new FileDataProvider();
    }
    
    public static FileDataProvider getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    @Autowired
    private FileDataProvider(Path path) {
        this.path = path;
    }
    
    private FileDataProvider() {
    }
    

    public Path getPath() {
		return path;
	}
    
    public void setPath(String path){
    	this.path = Paths.get(path);
    }
    
    //ApplicationArguments args
    //String[] argsArray = args.getSourceArgs();
    @PostConstruct
    public void init() throws IOException{
    	logger.info("Initializing the FileDataProvider");
		setPath(BusRoutesChallengeConfiguration.getInstance().getBusRoutesFile());

		try (Stream<String> lines = Files.lines(path)) {
            final ArrayList<List<BusStop>> busStops = lines
                    .skip(1)
                    .map(this::toBusRoute)
                    .collect(toCollection(ArrayList::new));
            routes = busStops;
        }catch(Exception ex){
        	logger.info("Initialization failed. Reason: "+ ex.getMessage()+" .Details : "+ex.getStackTrace());
        }
    }

	@Override
    public List<List<BusStop>> fetch() throws IOException {
		if(routes==null){
			try{
				init();
			}catch(Exception ex){
				
			}
			if(routes == null )
				routes = new ArrayList<List<BusStop>>();
		}
        
        return routes;
    }

    private List<BusStop> toBusRoute(String line) {
        final String[] busStops = line.split(DELIMITER);
        final List<BusStop> busStopsList = Stream.of(busStops)
                                                 .skip(1)
                                                 .map(Integer::valueOf)
                                                 .map(BusStop::of)
                                                 .collect(toList());
        return busStopsList;
    }
    
    @Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
