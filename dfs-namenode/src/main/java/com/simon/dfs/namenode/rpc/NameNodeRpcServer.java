package com.simon.dfs.namenode.rpc;

import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.namenode.datanodeinfo.DataNodeManager;
import com.simon.dfs.namenode.directory.FSNamesystem;
import com.simon.dfs.namenode.server.NameNodeServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author:
 * @Description: 对外服务接口
 * @Date: 2020/12/29 7:16
 * @Version: 1.0
 */
public class NameNodeRpcServer {
    private final Logger logger = LoggerFactory.getLogger(NameNodeRpcServer.class);
    private FSNamesystem namesystem;

    private DataNodeManager datanodeManager;

    private Server server;

    public NameNodeRpcServer(FSNamesystem namesystem, DataNodeManager datanodeManager) {
        this.namesystem = namesystem;
        this.datanodeManager = datanodeManager;
    }

    public void start() throws IOException {
        server = ServerBuilder
                .forPort(NameNodeConstant.NAMENODE_PORT)
                .addService(new NameNodeServiceImpl(namesystem, datanodeManager))
                .build()
                .start();

        logger.info("NameNodeRpcServer启动，监听端口号：{}", NameNodeConstant.NAMENODE_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                NameNodeRpcServer.this.stop();
            }
        });
    }

    public void stop(){
        if(server != null){
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if(server != null){
            server.awaitTermination();
        }
    }
}
