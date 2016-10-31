package com.vaani.downloader.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;

import com.vaani.downloader.model.DownloadResource;

public class IOUtil {
	private IOUtil(){
		
	}
	public static void downloadFromChannel(DownloadResource downloadResource, URL url, FileOutputStream fos, ReadableByteChannel rbc, Logger LOGGER) throws IOException{
		try {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            LOGGER.info("Downloading file {} from {} , total size: {} " ,
                    downloadResource.getOutputFile(), url, fos.getChannel().size());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            Path path = Paths.get(downloadResource.getOutputFile());
            try {
                Files.delete(path);
            } catch (IOException ex1) {
                LOGGER.error(ex.getMessage(), ex1);
            }
            throw ex;
        }
	}
	
	
}
