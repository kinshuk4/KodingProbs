package com.vaani.downloader.mq;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.vaani.downloader.model.DownloadResource;
import com.vaani.downloader.mq.processor.AbstractProtocolDownloader;
import com.vaani.downloader.mq.processor.ProtocolDownloaderFactory;
import com.vaani.downloader.protocolhelper.TrustAllX509TrustManager;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;

public class DownloadWorker implements Runnable {
    
    final static Logger LOGGER = LoggerFactory.getLogger(DownloadWorker.class);
    
    private final DownloadResource downloadResource;
    
    private final AbstractProtocolDownloader downloader;
    public DownloadWorker(DownloadResource downloadResource) throws MalformedURLException, IOException, KeyManagementException, NoSuchAlgorithmException {
        this.downloadResource = downloadResource;
        this.downloader = ProtocolDownloaderFactory.getProtocolDownloader(downloadResource);
//        System.out.println(this.url.getProtocol());
//        if("https".equals(this.downloadResource.getProtocol().toLowerCase())){
//        	this.url = new URL(downloadResource.getDownloadUrl());
//        	SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
//                public boolean verify(String string,SSLSession ssls) {
//                    return true;
//                }
//            });
//            HttpsURLConnection connection = (HttpsURLConnection) this.url.openConnection();
//            this.rbc = Channels.newChannel(connection.getInputStream());
//            
//        }else if("sftp".equals(this.downloadResource.getProtocol().toLowerCase())) {
//        	String url2 = downloadResource.getDownloadUrl().replaceAll("sftp", "ftp")+";type=i";
//            System.out.println(url2);
////        	this.url = this.url+";type=i";
//        	this.url  = new URL(url2);
////            URLConnection conn = url.openConnection();
////            InputStream inputStream = conn.getInputStream();
////            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
////
////            String line = null;
////            System.out.println("--- START ---");
////            while ((line = reader.readLine()) != null) {
////                System.out.println(line);
////            }
////            System.out.println("--- END ---");
//        	InputStream in = url.openStream();
//        	System.out.println("hell");
//            this.rbc = Channels.newChannel(in);
//            System.out.println("hell");
//        }
//        else{
//        	this.url = new URL(downloadResource.getDownloadUrl());
//        	this.rbc = Channels.newChannel(url.openStream());
//        }
//
//        this.fos = new FileOutputStream(downloadResource.getOutputFile());
    }
    
    public DownloadResource getDownloadResource() {
        return downloadResource;
    }
    
    @Override
    public void run() {
    	downloader.download();
        LOGGER.info("Download complete");
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
