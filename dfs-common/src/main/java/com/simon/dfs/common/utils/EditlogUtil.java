package com.simon.dfs.common.utils;

import cn.hutool.core.util.StrUtil;
import com.simon.dfs.common.constants.NameNodeConstant;

import java.io.File;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 7:50
 * @Version: 1.0
 */
public class EditlogUtil {

    public static final String OP_MKDIR = "MKDIR";

    public static final String LOG = ".log";


    public static Long getEditlogMinTxid(String filePath){
        String[] txidArr = getEditlogTxidRang(filePath).split(StrUtil.DASHED);
        return Long.valueOf(txidArr[0]);
    }

    public static Long getEditlogMaxTxid(String filePath){
        String[] txidArr = getEditlogTxidRang(filePath).split(StrUtil.DASHED);
        return Long.valueOf(txidArr[1]);
    }

    public static String getEditlogTxidRang(String filePath){
        File editlogs = new File(filePath);
        if(!editlogs.isFile() || !editlogs.exists()){
            return null;
        }
        String fileName = editlogs.getName();
        String txidRang = fileName.replace(NameNodeConstant.EDITLOG_PATH, "");
        return txidRang.replace(LOG,"");
    }
}
