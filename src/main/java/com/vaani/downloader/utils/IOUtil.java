package com.vaani.downloader.utils;

import java.io.File;
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
	private IOUtil() {

	}

	public static void downloadFromChannel(DownloadResource downloadResource, URL url, FileOutputStream fos,
			ReadableByteChannel rbc, Logger LOGGER) throws IOException, InterruptedException {
		int count = 0;
		long bytesDownloaded = 0;
		while (count < 5) {
			try {				
				fos.getChannel().transferFrom(rbc, bytesDownloaded, Long.MAX_VALUE);
				LOGGER.info("Downloading file {} from {} , total size: {} ", downloadResource.getOutputFile(), url,
						fos.getChannel().size());
			} catch (IOException ex) {
				count++;
				
				if(count==5)
					throw ex;
				LOGGER.error(ex.getMessage());
				Thread.sleep(1000);
				
				File file = new File(downloadResource.getOutputFile());
				
				if(file.exists())
					bytesDownloaded = file.length();				
				
			}
		}

	}
}
