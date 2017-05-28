package com.vaani.downloader.mq.processor;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.activity.InvalidActivityException;

import com.vaani.downloader.model.DownloadResource;

/**
 * Created by kchandra on 31/10/16.
 */
public class ProtocolDownloaderFactory {
    public static AbstractProtocolDownloader getProtocolDownloader(DownloadResource downloadResource) throws MalformedURLException, IOException {
        switch (downloadResource.getProtocol()) {
            case "http":
            case "ftp":
                return new GenericDownloader(downloadResource);
		case "sftp":
                return new SftpDownloader(downloadResource);
		case "https":
                return new HttpsDownloader(downloadResource);
            default:
                throw new InvalidActivityException(String.format("Protocol %s is not allowed", downloadResource.getProtocol()));
        }
    }
}
