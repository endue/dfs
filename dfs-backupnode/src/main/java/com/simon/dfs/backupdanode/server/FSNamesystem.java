package com.simon.dfs.backupdanode.server;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/29 7:14
 * @Version: 1.0
 */
public class FSNamesystem {

    private FSDirectory directory;

    public FSNamesystem() {
        this.directory = new FSDirectory();
    }

    /**
     * 创建目录
     * @param path
     * @return
     */
    public boolean mkdir(String path) {
        this.directory.mkdir(path);
        return true;
    }

    public FSDirectory getDirectory() {
        return directory;
    }
}
