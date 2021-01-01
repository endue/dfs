package com.simon.dfs.backupdanode.rpc;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.simon.dfs.backupdanode.server.Editlog;
import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest;
import com.simon.dfs.namenode.rpc.model.FetchEditlogsResponse;
import com.simon.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 15:48
 * @Version: 1.0
 */
public class NameNodeRpcClient {
    private final Logger logger = LoggerFactory.getLogger(NameNodeRpcClient.class);

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;

    public NameNodeRpcClient() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NameNodeConstant.NAMENODE_HOSTNAME, NameNodeConstant.NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    /**
     * 拉取editlog
     * @param fetchedMaxTxid
     * @return
     */
    public List<Editlog> fetchBatchEditlog(long fetchedMaxTxid){
        FetchEditlogsRequest request = FetchEditlogsRequest.newBuilder()
                .setCode(1)
                .setFetchedMaxTxid(fetchedMaxTxid)
                .build();
        FetchEditlogsResponse response = namenode.fetchEditlogs(request);
        String editlogs = response.getEditlogs();
        JSONArray editlogJSONArray = JSONUtil.parseArray(editlogs);
        List<Editlog> editlogList = new ArrayList<>();
        for (int i = 0; i < editlogJSONArray.size(); i++) {
            editlogList.add(editlogJSONArray.get(i, Editlog.class));
        }
        return editlogList;
    }
}
