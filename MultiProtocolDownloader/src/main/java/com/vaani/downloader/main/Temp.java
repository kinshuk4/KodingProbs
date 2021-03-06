package com.vaani.downloader.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
 
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
public class Temp {
	public static void main(String[] args) {
		String SFTPHOST = "test.rebex.net";
		int    SFTPPORT = 22;
		String SFTPUSER = "demo";
		String SFTPPASS = "password";
		String SFTPWORKINGDIR = "readme.txt";
		 
		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;
		 
		try{
		JSch jsch = new JSch();
		session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
		session.setPassword(SFTPPASS);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		channel = session.openChannel("sftp");
		channel.connect();
		channelSftp = (ChannelSftp)channel;
//		channelSftp.cd(SFTPWORKINGDIR);
		byte[] buffer = new byte[1024];
		BufferedInputStream bis = new BufferedInputStream(channelSftp.get("readme.txt"));
		File newFile = new File("Test.java");
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
		}catch(Exception ex){
		ex.printStackTrace();
		}
		System.out.println("Writing: " );
		 
		}
}
