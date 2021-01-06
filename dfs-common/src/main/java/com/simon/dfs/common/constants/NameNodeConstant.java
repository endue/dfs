package com.simon.dfs.common.constants;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/28 22:51
 * @Version: 1.0
 */
public class NameNodeConstant {

    public static final String NAMENODE_HOSTNAME = "localhost";
    public static final Integer NAMENODE_PORT = 50070;

    public static final Long EDIT_LOG_BUFFER_LIMIT = 5 * 1024L;

    public static final String EDITLOG_PATH = "D:\\TF\\editlogs\\";
    public static final String EDITLOG_FILE_PATH = "D:\\TF\\editlogs\\%s-%s.log";

    public static final String CHECKPOINT_FILE_PATH = "D:\\TF\\editlogs\\checkpoint\\";

    public static final String CHECKPOINT_UPLOAD_IP = "127.0.0.1";
    public static final Integer CHECKPOINT_UPLOAD_PORT = 9000;
}
