package com.simon.dfs.client;

import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.namenode.rpc.model.MkdirRequest;
import com.simon.dfs.namenode.rpc.model.ShutdownRequest;
import com.simon.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 8:31
 * @Version: 1.0
 */
public class FileSystemImpl implements FileSystem {

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;

    public FileSystemImpl() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NameNodeConstant.NAMENODE_HOSTNAME, NameNodeConstant.NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void mkdir(String path) throws Exception {
        MkdirRequest mkdirRequest = MkdirRequest.newBuilder()
                .setPath(path)
                .build();
        namenode.mkdir(mkdirRequest);
    }

    @Override
    public void shutdown() throws Exception {
        ShutdownRequest request = ShutdownRequest.newBuilder()
                .setCode(1)
                .build();
        namenode.shutdown(request);
    }
}
