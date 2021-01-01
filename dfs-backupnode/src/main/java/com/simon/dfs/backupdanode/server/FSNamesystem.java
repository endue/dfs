package com.simon.dfs.backupdanode.server;

import com.simon.dfs.common.utils.EditlogUtil;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/29 7:14
 * @Version: 1.0
 */
public class FSNamesystem {

    private FSDirectory directory;

    private FSEditlog editlog;

    public FSNamesystem() {
        this.directory = new FSDirectory();
        this.editlog = new FSEditlog();
    }

    /**
     * 创建目录
     * @param path
     * @return
     */
    public boolean mkdir(String path) {
        this.directory.mkdir(path);
        this.editlog.logEdit(EditlogUtil.mkdir(path));
        return false;
    }

    public void shutdown() {
        this.editlog.flush();
    }
}
