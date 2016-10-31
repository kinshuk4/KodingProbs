package com.vaani.downloader.mq.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.vaani.downloader.model.DownloadResource;

/**
 * Created by kchandra on 31/10/16.
 */
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
}
