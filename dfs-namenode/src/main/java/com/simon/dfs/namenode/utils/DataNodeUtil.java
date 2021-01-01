package com.simon.dfs.namenode.utils;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/29 7:45
 * @Version: 1.0
 */
public class DataNodeUtil {

    public static String getDataNodeIdentify(String ip,String hostname){
        return ip + "-" + hostname;
    }
}
