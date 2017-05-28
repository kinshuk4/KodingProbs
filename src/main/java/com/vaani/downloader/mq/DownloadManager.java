package com.vaani.downloader.mq;

import com.vaani.downloader.model.DownloadResource;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;


@Component
public class DownloadManager {

    final static Logger LOGGER = LoggerFactory.getLogger(DownloadManager.class);

    @Autowired
    private ThreadPoolTaskExecutor downloadExecutor;

    private int maxThreads = 5;

    private final List<DownloadResource> downloadList = new LinkedList<>();

    public DownloadManager() {
    }

    public DownloadManager(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public ThreadPoolTaskExecutor getDownloadExecutor() {
        return downloadExecutor;
    }

    public void setDownloadExecutor(ThreadPoolTaskExecutor downloadExecutor) {
        this.downloadExecutor = downloadExecutor;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void queueDownload(DownloadResource downloadResource) {
        this.downloadList.add(downloadResource);

    }

    public List<DownloadResource> getDownloadList() {
        return downloadList;
    }

    public void start() throws Exception {
        if (downloadList.isEmpty()) {
            throw new Exception("Download queue is empty.");
        }

        downloadList.stream().forEach((config) -> {
            try {
                DownloadWorker downloadWorker=null;
				try {
					downloadWorker = new DownloadWorker(config);
				} catch (KeyManagementException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                downloadExecutor.execute(downloadWorker);

            } catch (IOException ex) {
                LOGGER.error(ex.getMessage()+"More:{}", ex);
            }
        });
    }

    public void stop() {
        if (hasActiveDownload()) {
            downloadExecutor.shutdown();
        }
    }

    public boolean hasActiveDownload() {
        return (downloadExecutor.getActiveCount() > 0);
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
