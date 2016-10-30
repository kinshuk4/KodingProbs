package com.vaani.downloader.utils;

import com.vaani.downloader.constant.SupportedProtocol;
import org.apache.commons.lang3.EnumUtils;

public class DownloadConstant {
    //Shouldn't be initialized
    private DownloadConstant(){}
    public static final int PORT_MAX = 65535;
    public static final int PORT_MIN = 0;

    public static final String MSG_PORT_CANNOT_BE_NULL = "Port cannot be empty/null.";
    public static final String MSG_PORT_OUT_OF_RANGE = "Port out of range - 0-65535, actual port:";
    public static final String MSG_HOSTNAME_CANNOT_BE_NULL="Hostname cannot be empty/null.";
    public static final String MSG_TARGET_CANNOT_BE_NULL = "Target File cannot be empty/null.";
    public static final String MSG_OUTPUT_CANNOT_BE_NULL = "Output File cannot be empty/null.";
    public static final String MSG_PROTOCOL_CANNOT_BE_NULL = "Protocol cannot be empty.";
    public static final String MSG_PROTOCOL_NOT_ALLOWED = "Protocol: %s is not allowed. ";
    public static final String MSG_ONLY_PROTOCOLS_ALLWED = "Supported protocols are: "+ EnumUtils.getEnumList(SupportedProtocol.class);


}
