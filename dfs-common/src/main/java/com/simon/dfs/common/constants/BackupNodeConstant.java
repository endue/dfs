package com.simon.dfs.common.constants;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 16:18
 * @Version: 1.0
 */
public class BackupNodeConstant {

    public static final long EDITLOG_FETCHER_INTERVAL = TimesConstant.SECOND * 10;

    public static final long EDITLOG_CHECKPOINT_INTERVAL = TimesConstant.SECOND * 60;

    public static final String CHECKPOINT_FILE_PATH = "D:\\TF\\checkpoint\\";

    public static final String CHECKPOINT_FILE_SUFFIX = ".meta";


}
