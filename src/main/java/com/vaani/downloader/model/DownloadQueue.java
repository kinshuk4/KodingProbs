package com.vaani.downloader.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

public class DownloadQueue {

    private int numDownloads;
    private String outputFolder;
    private List<DownloadResource> downloadList;

    public int getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
    
    public List<DownloadResource> getDownloadList() {
        return downloadList;
    }

    public void setDownloadList(List<DownloadResource> downloadList) {
        this.downloadList = downloadList;
    }
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
