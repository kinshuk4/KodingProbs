package com.vaani.downloader.config;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AppConfig {

    @Value("${download.max.thread}")
    private int maxThreads;

    @Value("${download.queue.file}")
    private String downloadQueueFile;

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getDownloadQueueFile() {
        return downloadQueueFile;
    }

    public void setDownloadQueueFile(String downloadQueueFile) {
        this.downloadQueueFile = downloadQueueFile;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
