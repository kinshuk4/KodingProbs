package com.vaani.downloader.mq.processor;


import com.vaani.downloader.model.DownloadResource;
import com.vaani.downloader.mq.DownloadManager;
import com.vaani.downloader.utils.IOUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenericDownloader extends AbstractProtocolDownloader {
    final static Logger LOGGER = LoggerFactory.getLogger(DownloadManager.class);


    public GenericDownloader(DownloadResource downloadResource) throws MalformedURLException, IOException {
        super(downloadResource);
    }
    
    private void downloadHelper() throws IOException{
    	boolean hasExceptionOccured = false;
        ReadableByteChannel rbc=Channels.newChannel(url.openStream());
		IOUtil.downloadFromChannel(downloadResource, url, fos, rbc, LOGGER);
    }

    public void download() {
    	try {
			downloadHelper();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
