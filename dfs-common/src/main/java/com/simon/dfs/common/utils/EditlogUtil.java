package com.simon.dfs.common.utils;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 7:50
 * @Version: 1.0
 */
public class EditlogUtil {

    public static final String OP_MKDIR = "MKDIR";
    public static final String OP = "OP";
    public static final String PATH = "PATH";

    public static String mkdir(String path){
        return "{'" + OP + "':'"+ OP_MKDIR +"','" + PATH + "':'" + path + "'}";
    }
}
