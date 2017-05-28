package com.vaani.downloader.mq.processor;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.vaani.downloader.model.DownloadResource;
import com.vaani.downloader.mq.DownloadManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SftpDownloader  extends AbstractProtocolDownloader {
    final static Logger LOGGER = LoggerFactory.getLogger(DownloadManager.class);
    
    public SftpDownloader(DownloadResource downloadResource) throws MalformedURLException, IOException {
        super(downloadResource);
    }
    
    protected URL initiateURL() throws MalformedURLException{
    	return new URL(downloadResource.getDownloadUrl().replaceAll("sftp", "ftp"));
    }

    private void downloadHelper() throws JSchException, SftpException, IOException{
        Session session     = null;
        Channel channel     = null;
        ChannelSftp channelSftp = null;

        JSch jsch = new JSch();
        
        session = jsch.getSession(downloadResource.getUsername(),downloadResource.getHostname(),downloadResource.getPort());
        session.setPassword(downloadResource.getPassword());
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp)channel;
//		channelSftp.cd(SFTPWORKINGDIR);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(channelSftp.get(downloadResource.getTargetFile()));
        File newFile = new File(downloadResource.getOutputFile());
        OutputStream os = new FileOutputStream(newFile);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int readCount;
        //System.out.println("Getting: " + theLine);
        while( (readCount = bis.read(buffer)) > 0) {
            System.out.println("Writing: " );
            bos.write(buffer, 0, readCount);
        }
        bis.close();
        bos.close();
    }

	@Override
	public void download() {
		// TODO Auto-generated method stub
		try {
			downloadHelper();
		} catch (JSchException | SftpException | IOException e) {
			// TODO Auto-generated catch block
			cleanup(e);
		}
	}

}
