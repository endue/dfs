package com.simon.dfs.backupdanode.server;

/**
 * @Author:
 * @Description: editlog
 * @Date: 2020/12/30 23:17
 * @Version: 1.0
 */
public class Editlog {

    protected Long txid;
    protected String path;
    protected String op;

    public Editlog(Long txid, String path,String op) {
        this.txid = txid;
        this.path = path;
        this.op = op;
    }

    public Long getTxid() {
        return txid;
    }

    public String getPath() {
        return path;
    }

    public String getOp() {
        return op;
    }

    public void setTxid(Long txid) {
        this.txid = txid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOp(String op) {
        this.op = op;
    }
}