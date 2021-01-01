package com.simon.dfs.datanode.server;

import cn.hutool.json.JSON;
import com.simon.dfs.common.constants.DataNodeConstant;
import com.simon.dfs.common.constants.TimesConstant;
import com.simon.dfs.namenode.rpc.model.HeartbeatRequest;
import com.simon.dfs.namenode.rpc.model.HeartbeatResponse;
import com.simon.dfs.namenode.rpc.model.RegisterRequest;
import com.simon.dfs.namenode.rpc.model.RegisterResponse;
import com.simon.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.simon.dfs.common.constants.NameNodeConstant.NAMENODE_HOSTNAME;
import static com.simon.dfs.common.constants.NameNodeConstant.NAMENODE_PORT;

/**
 * @Author:
 * @Description: 负责与一个namenode进行通信
 * @Date: 2020/12/28 22:43
 * @Version: 1.0
 */
public class NameNodeServiceActor {

    private final Logger logger = LoggerFactory.getLogger(NameNodeServiceActor.class);
    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;

    public NameNodeServiceActor() {
        logger.info("init NameNodeServiceActor");
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    public void register() {
        logger.info("start register");
        new Thread(new Register()).start();

    }

    public void hearbeat() {
        logger.info("start hearbeat");
        new Thread(new Hearbeat()).start();
    }

    class Register implements Runnable{

        @Override
        public void run() {
            logger.info("start register{}",DataNodeConstant.DATANODE_IP);
            RegisterRequest request = RegisterRequest.newBuilder()
                    .setIp(DataNodeConstant.DATANODE_IP)
                    .setHostname(DataNodeConstant.DATANODE_HOSTNAME)
                    .build();
            RegisterResponse response = namenode.register(request);
            logger.info("register response {}", response.getStatus());
        }
    }

    class Hearbeat implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(TimesConstant.THIRTY_SECONDS);
                    logger.info("start hearbeat{}",DataNodeConstant.DATANODE_IP);
                    HeartbeatRequest request = HeartbeatRequest.newBuilder()
                            .setIp(DataNodeConstant.DATANODE_IP)
                            .setHostname(DataNodeConstant.DATANODE_HOSTNAME)
                            .build();
                    HeartbeatResponse response = namenode.heartbeat(request);
                    logger.info("hearbeat response {}", response.getStatus());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
