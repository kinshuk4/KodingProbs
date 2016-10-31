package com.vaani.downloader.mq.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaani.downloader.model.DownloadResource;
import com.vaani.downloader.mq.DownloadManager;

public abstract class AbstractProtocolDownloader {
    protected final DownloadResource downloadResource;
    protected final URL url;
    protected final FileOutputStream fos;
    
    public AbstractProtocolDownloader(DownloadResource downloadResource) throws MalformedURLException, IOException{
        this.downloadResource = downloadResource;
        this.url = initiateURL();
        fos = new FileOutputStream(downloadResource.getOutputFile());
    }
    
    protected URL initiateURL() throws MalformedURLException{
    	return new URL(downloadResource.getDownloadUrl());
    }
    
    public abstract void download();
    
    public void cleanup(Exception ex){        
        Path path = Paths.get(downloadResource.getOutputFile());
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            Files.delete(path);
        } catch (IOException ex1) {
        	logger.error("No need to cleanup. Reason:"+ex.getMessage()+"More:{}", ex1);
        }
        logger.error("Unable to download the file:{}, Reason:{}, More:{}",downloadResource.getOutputFile(),ex.getMessage(),ex);
        
    }
}
