package com.simon.dfs.namenode.utils;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 7:50
 * @Version: 1.0
 */
public class EditLogUtil {

    public static final String OP_MKDIR = "MKDIR";

    public static String mkdir(String path){
        return "{'OP':'"+ OP_MKDIR +"','PATH':'" + path + "'}";
    }
}
