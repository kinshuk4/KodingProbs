package com.vaani.downloader.mq;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.vaani.downloader.model.DownloadResource;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadWorker implements Runnable {
    
    final static Logger LOGGER = LoggerFactory.getLogger(DownloadWorker.class);
    
    private final DownloadResource downloadResource;
    
    private final URL url;
    private final ReadableByteChannel rbc;
    private final FileOutputStream fos;
    
    public DownloadWorker(DownloadResource downloadResource) throws MalformedURLException, IOException {
        this.downloadResource = downloadResource;
        this.url = new URL(downloadResource.getDownloadUrl());
        this.rbc = Channels.newChannel(url.openStream());
        this.fos = new FileOutputStream(downloadResource.getOutputFile());
    }
    
    public DownloadResource getDownloadResource() {
        return downloadResource;
    }
    
    @Override
    public void run() {
        try {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            LOGGER.info("Downloading file {} from {} , total size: {} " ,
                    downloadResource.getOutputFile(), this.url, fos.getChannel().size());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            Path path = Paths.get(downloadResource.getOutputFile());
            try {
                Files.delete(path);
            } catch (IOException ex1) {
                LOGGER.error(ex.getMessage(), ex1);
            }
        }
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
