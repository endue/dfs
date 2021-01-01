package com.simon.dfs.namenode.datanodeinfo;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/29 7:44
 * @Version: 1.0
 */
public class DataNodeInfo {

    private String ip;
    private String hostname;
    private long latestHeartbeatTime = System.currentTimeMillis();

    public DataNodeInfo(String ip, String hostname) {
        this.ip = ip;
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    public long getLatestHeartbeatTime() {
        return latestHeartbeatTime;
    }
    public void setLatestHeartbeatTime(long latestHeartbeatTime) {
        this.latestHeartbeatTime = latestHeartbeatTime;
    }
}
