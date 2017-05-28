package com.vaani.downloader.main;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Temp2 {
    public static void main(String[] args) {
    	int BUFFER_SIZE = 4096;
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        String host = "www.yourserver.com";
        String user = "tom";
        String pass = "secret";
        String filePath = "/project/2012/Project.zip";
        String savePath = "E:/Download/Project.zip";
 
        ftpUrl = "ftp://demo:password@test.rebex.net:22/readme.txt;type=i";
        System.out.println("URL: " + ftpUrl);
 
        try {
            URL url = new URL(ftpUrl);
            
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
 
            //FileOutputStream outputStream = new FileOutputStream(System.out);
 
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
 
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                System.out.println(bytesRead);
            }
 
            // outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } catch (IOException ex) {
        	System.out.println("err");
            System.out.println(ex.toString());
        }
        System.out.println("done");
    }
}
