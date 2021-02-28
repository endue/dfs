package com.simon.dfs.namenode;

import com.simon.dfs.namenode.nio.CheckpointUploadServer;
import com.simon.dfs.namenode.rpc.NameNodeRpcServer;
import com.simon.dfs.namenode.datanodeinfo.DataNodeManager;
import com.simon.dfs.namenode.directory.FSNamesystem;

import java.io.IOException;

/**
 * @Author:
 * @Description: namenode服务类
 * @Date: 2020/12/29 7:13
 * @Version: 1.0
 */
public class NameNode {

    private FSNamesystem namesystem;

    private DataNodeManager datanodeManager;

    private NameNodeRpcServer rpcServer;

    private CheckpointUploadServer uploaderServer;

    private void initialize(){
        this.namesystem = new FSNamesystem();
        this.datanodeManager = new DataNodeManager();
        this.rpcServer = new NameNodeRpcServer(this.namesystem,this.datanodeManager);

        this.uploaderServer = new CheckpointUploadServer(this.namesystem);
        // 恢复checkpoint文件中的editlogs
        this.uploaderServer.recoverCheckpointEditLogs();
        // 恢复磁盘editlog文件中的数据
        this.namesystem.recoverEditLogs();
    }

    private void start() throws IOException, InterruptedException {
        this.uploaderServer.start();
        this.rpcServer.start();
        this.rpcServer.blockUntilShutdown();
    }

    public static void main(String[] args) {
        try {
            NameNode nameNode = new NameNode();
            nameNode.initialize();
            nameNode.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
