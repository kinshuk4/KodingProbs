package com.vaani.downloader.model;

import com.vaani.downloader.protocolhelper.SupportedProtocol;
import com.vaani.downloader.utils.DownloadConstant;
import org.apache.commons.lang3.StringUtils;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public class DownloadResource {

    private String protocol;
    private String hostname;
    private Integer port;
    private String targetFile;
    private String outputFile;
    private String username;
    private String password;
    private boolean needAuthentication;

    public DownloadResource() {
    }

    public DownloadResource(String protocol, String hostname, Integer port, String targetFile, String outputFile) {
        if (checkProtocol(protocol)) {
            this.protocol = protocol;
        }

        if (checkPort(port)) {
            this.port = port;
        }

        if (checkHostName(hostname)) {
            this.hostname = hostname;
        }

        if (checkTargetFilePath(targetFile)) {
            this.targetFile = targetFile;
        }

        if (checkOutputFilePath(outputFile)) {
            this.outputFile = outputFile;
        }
        
        this.needAuthentication = false;
    }

    public DownloadResource(String protocol, String hostname, Integer port, String username, String password, String targetFile, String outputFile) {
        this(protocol, hostname, port, targetFile, outputFile);
        this.username = username;
        this.password = password;
        this.needAuthentication = true;
    }

    private static boolean checkProtocol(String protocol) {
        if(StringUtils.isBlank(protocol)){
            throw new IllegalArgumentException(DownloadConstant.MSG_PROTOCOL_CANNOT_BE_NULL);
        }
        if (!(SupportedProtocol.HTTP.name().equalsIgnoreCase(protocol)
                || SupportedProtocol.HTTPS.name().equalsIgnoreCase(protocol)
                || SupportedProtocol.FTP.name().equalsIgnoreCase(protocol)
                || SupportedProtocol.SFTP.name().equalsIgnoreCase(protocol))) {
            throw new IllegalArgumentException(String.format(
                    DownloadConstant.MSG_PROTOCOL_NOT_ALLOWED,protocol)
                    + DownloadConstant.MSG_ONLY_PROTOCOLS_ALLWED);
        }

        return true;
    }



    private static boolean checkPort(Integer port) {
        if(port==null){
            throw new IllegalArgumentException(DownloadConstant.MSG_PORT_CANNOT_BE_NULL);
        }
        if (port.compareTo(DownloadConstant.PORT_MIN) < 0 || port.compareTo(DownloadConstant.PORT_MAX) > 0) {
            throw new IllegalArgumentException(DownloadConstant.MSG_PORT_OUT_OF_RANGE + port);
        }
        return true;
    }

    private static boolean checkHostName(String hostname) {
        if (StringUtils.isBlank(hostname)) {
            throw new IllegalArgumentException(DownloadConstant.MSG_HOSTNAME_CANNOT_BE_NULL);
        }
        return true;
    }

    private static boolean checkTargetFilePath(String targetFile) {
        if (StringUtils.isBlank(targetFile)) {
            throw new IllegalArgumentException(DownloadConstant.MSG_TARGET_CANNOT_BE_NULL);
        }
        return true;
    }

    private static boolean checkOutputFilePath(String outputFile) {
        if (StringUtils.isBlank(outputFile)) {
            throw new IllegalArgumentException(DownloadConstant.MSG_OUTPUT_CANNOT_BE_NULL);
        }
        return true;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNeedAuthentication() {
        return needAuthentication;
    }

    public void setNeedAuthentication(boolean needAuthentication) {
        this.needAuthentication = needAuthentication;
    }

    public String getDownloadUrl() {
        StringBuilder sb = new StringBuilder();

        sb.append(protocol);
        sb.append("://");
        sb.append(hostname);
        sb.append(":");
        sb.append(port);
        sb.append("/");
        sb.append(targetFile);

        return sb.toString();
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
