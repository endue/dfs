package com.simon.dfs.namenode.directory;

import com.simon.dfs.common.utils.EditlogUtil;

import java.util.List;

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
        this.editlog.logEdit(path,EditlogUtil.OP_MKDIR);
        return true;
    }

    /**
     * 关闭当前服务
     */
    public void shutdown() {
        this.editlog.readyToFlush();
        this.editlog.flush();
    }

    public List<Editlog> getEditlogs(Long fetchedMaxTxid) {
        return this.editlog.getEditlogs(fetchedMaxTxid);
    }

    /**
     * 删除Editlog
     * @param checkpointTxid
     */
    public void deleteEditlog(long checkpointTxid) {
        this.editlog.deleteEditlog(checkpointTxid);
    }

    /**
     * 恢复磁盘上的editlogs文件
     */
    public void recoverEditLogs() {
        this.directory.recoverEditLogs();
    }

    public void resetNodeDirectory(FSDirectory.Node node) {
        this.directory.resetNodeDirectory(node);
    }
}
