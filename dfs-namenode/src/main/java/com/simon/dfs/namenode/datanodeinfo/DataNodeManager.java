package com.simon.dfs.namenode.datanodeinfo;

import com.simon.dfs.common.constants.TimesConstant;
import com.simon.dfs.namenode.utils.DataNodeUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:
 * @Description: 负责dataNode节点管理
 * @Date: 2020/12/29 7:15
 * @Version: 1.0
 */
public class DataNodeManager {
    // 保存datanode节点信息
    private Map<String, DataNodeInfo> datanodes = new ConcurrentHashMap<>();

    public DataNodeManager() {
        new Thread(new DataNodeAliveMonitor()).start();
    }

    /**
     * 注册
     * @param ip
     * @param hostname
     */
    public boolean register(String ip, String hostname) {
        DataNodeInfo dataNodeInfo = new DataNodeInfo(ip,hostname);
        datanodes.put(DataNodeUtil.getDataNodeIdentify(ip,hostname),dataNodeInfo);
        return true;
    }

    /**
     * 心跳
     * @param ip
     * @param hostname
     */
    public boolean heartbeat(String ip, String hostname) {
        String dataNodeIdentify = DataNodeUtil.getDataNodeIdentify(ip, hostname);
        datanodes.getOrDefault(dataNodeIdentify,new DataNodeInfo(ip,hostname))
                .setLatestHeartbeatTime(System.currentTimeMillis());
        return true;
    }

    class DataNodeAliveMonitor implements Runnable{
        @Override
        public void run() {
            while (true){

                try {
                    Thread.sleep(TimesConstant.THIRTY_SECONDS);

                    Iterator<DataNodeInfo> iterator = datanodes.values().iterator();
                    while (iterator.hasNext()){
                        DataNodeInfo dataNodeInfo = iterator.next();
                        if(System.currentTimeMillis() - dataNodeInfo.getLatestHeartbeatTime() > TimesConstant.LATEST_HEARTBEAT_TIME){
                            iterator.remove();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
