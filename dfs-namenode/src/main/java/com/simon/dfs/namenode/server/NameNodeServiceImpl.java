package com.simon.dfs.namenode.server;

import cn.hutool.json.JSONUtil;
import com.simon.dfs.common.constants.StatusConstant;
import com.simon.dfs.namenode.datanodeinfo.DataNodeManager;
import com.simon.dfs.namenode.directory.Editlog;
import com.simon.dfs.namenode.directory.FSNamesystem;
import com.simon.dfs.namenode.rpc.model.*;
import com.simon.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/29 7:17
 * @Version: 1.0
 */
public class NameNodeServiceImpl extends NameNodeServiceGrpc.NameNodeServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(NameNodeServiceImpl.class);
    private FSNamesystem namesystem;
    private DataNodeManager dataNodeManager;
    private volatile boolean running;
    private List<Editlog> cachedEditlogs = new LinkedList<>();

    public NameNodeServiceImpl(FSNamesystem namesystem, DataNodeManager datanodeManager) {
        this.namesystem = namesystem;
        this.dataNodeManager = datanodeManager;
        this.running = true;
    }

    /**
     * 创建目录
     * @param path
     * @return
     */
    public boolean makdir(String path){
        return this.namesystem.mkdir(path);
    }

    /**
     * 注册
     * @param request
     * @param responseObserver
     */
    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        logger.info("收到服务{}:{}注册",request.getHostname(),request.getIp());
        dataNodeManager.register(request.getIp(),request.getHostname());

        RegisterResponse response = RegisterResponse.newBuilder()
                .setStatus(StatusConstant.STATUS_SUCCESS)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 心跳
     * @param request
     * @param responseObserver
     */
    @Override
    public void heartbeat(HeartbeatRequest request, StreamObserver<HeartbeatResponse> responseObserver) {
        logger.info("收到服务{}:{}心跳",request.getHostname(),request.getIp());
        dataNodeManager.heartbeat(request.getIp(),request.getHostname());

        HeartbeatResponse response = HeartbeatResponse.newBuilder()
                .setStatus(StatusConstant.STATUS_SUCCESS)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 创建目录
     * @param request
     * @param responseObserver
     */
    @Override
    public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
        logger.info("创建目录{}",request.getPath());
        MkdirResponse response;
        if(running){
            this.namesystem.mkdir(request.getPath());
            response = MkdirResponse.newBuilder()
                    .setStatus(StatusConstant.STATUS_SUCCESS)
                    .build();
        }else{
            response = MkdirResponse.newBuilder()
                    .setStatus(StatusConstant.STATUS_FAILURE)
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 关闭
     * @param request
     * @param responseObserver
     */
    @Override
    public void shutdown(ShutdownRequest request, StreamObserver<ShutdownResponse> responseObserver) {
        logger.info("shutdown");
        this.running = false;
        // 优雅关闭将内存数据刷入磁盘
        this.namesystem.shutdown();

        ShutdownResponse response = ShutdownResponse.newBuilder()
                .setStatus(StatusConstant.STATUS_SUCCESS)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 同步editlog
     * @param request
     * @param responseObserver
     */
    @Override
    public void fetchEditlogs(FetchEditlogsRequest request, StreamObserver<FetchEditlogsResponse> responseObserver) {
        List<Editlog> result = new ArrayList<>();
        int fetchCount = 0;
        long fetchedMaxTxid = request.getFetchedMaxTxid();
        // 缓存没有，从editlogBuffer拉取
        if(cachedEditlogs.isEmpty()){
            List<Editlog> editlogs = namesystem.getEditlogs(fetchedMaxTxid);
            cachedEditlogs.addAll(editlogs);
        }
        Iterator<Editlog> iterator = cachedEditlogs.iterator();
        while (iterator.hasNext()){
            Editlog editlog = iterator.next();
            if(editlog.getTxid() <= fetchedMaxTxid){
                continue;
            }
            if(++fetchCount > 10){
                break;
            }
            fetchedMaxTxid = editlog.getTxid();
            result.add(editlog);
        }


        // 不够fetch的数据量，从editlogBuffer拉取
        if(fetchCount < 10){
            cachedEditlogs.clear();
            List<Editlog> editlogs = namesystem.getEditlogs(fetchedMaxTxid);
            cachedEditlogs.addAll(editlogs);

            iterator = cachedEditlogs.iterator();
            while (iterator.hasNext()){
                Editlog editlog = iterator.next();
                if(editlog.getTxid() <= fetchedMaxTxid){
                    continue;
                }
                if(++fetchCount > 10){
                    break;
                }
                fetchedMaxTxid = editlog.getTxid();
                result.add(editlog);
            }
        }

       FetchEditlogsResponse response = FetchEditlogsResponse.newBuilder()
                .setEditlogs(JSONUtil.parseArray(result).toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
