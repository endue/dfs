package com.simon.dfs.backupdanode;

import com.simon.dfs.backupdanode.checkpoint.EditlogCheckpoint;
import com.simon.dfs.backupdanode.fetcher.EditlogFetcher;
import com.simon.dfs.backupdanode.nio.CheckpointUploaderClient;
import com.simon.dfs.backupdanode.server.FSNamesystem;

import java.io.IOException;

/**
 * @Author:
 * @Description: backupNode启动类
 * @Date: 2021/1/1 15:31
 * @Version: 1.0
 */
public class BackupNode {

    private FSNamesystem namesystem;
    private volatile boolean running = false;
    private EditlogFetcher editlogFetcher;
    private EditlogCheckpoint editlogCheckpoint;
    private CheckpointUploaderClient checkpointUploaderClient;

    private void initialize(){
        this.running = true;
        this.namesystem = new FSNamesystem();
        // 创建log定时fetch任务
        this.editlogFetcher = new EditlogFetcher(this,namesystem);
        // 创建log定时checkpoint任务
        this.editlogCheckpoint = new EditlogCheckpoint(this,namesystem,editlogFetcher);
        // 创建log定时upload任务
        this.checkpointUploaderClient = new CheckpointUploaderClient(this);

        loadMaxTxid();
    }

    private void loadMaxTxid() {
        // 读取log已checkpoint的maxtxid，进行恢复
        long checkpointMaxTxid = this.editlogCheckpoint.recoverCheckpointMaxTxid();
        this.editlogFetcher.setFetchedMaxTxid(checkpointMaxTxid);
    }

    private void start() {
        // 启动log定时fetch任务
        this.editlogFetcher.start();
        // 启动log定时checkpoint任务
        this.editlogCheckpoint.start();
        // 启动log定时upload任务
        checkpointUploaderClient.start();

        while (running){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        BackupNode backupNode = new BackupNode();
        backupNode.initialize();
        backupNode.start();
    }


}
