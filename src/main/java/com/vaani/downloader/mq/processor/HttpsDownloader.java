package com.vaani.downloader.mq.processor;

import com.vaani.downloader.model.DownloadResource;
import com.vaani.downloader.mq.DownloadManager;
import com.vaani.downloader.protocolhelper.TrustAllX509TrustManager;
import com.vaani.downloader.utils.IOUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kchandra on 31/10/16.
 */
public class HttpsDownloader  extends AbstractProtocolDownloader {
    final static Logger LOGGER = LoggerFactory.getLogger(DownloadManager.class);

    
    public HttpsDownloader(DownloadResource downloadResource) throws MalformedURLException, IOException {
        super(downloadResource);

    }
    
    private void downloadHelper() throws NoSuchAlgorithmException, KeyManagementException, IOException{
    	SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
            public boolean verify(String string,SSLSession ssls) {
                return true;
            }
        });
        HttpsURLConnection connection = (HttpsURLConnection) this.url.openConnection();
        ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
        IOUtil.downloadFromChannel(downloadResource, url, fos, rbc, LOGGER);
    }
	@Override
	public void download() {
		try {
			downloadHelper();
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			cleanup(e);
		}
		
	}
}
