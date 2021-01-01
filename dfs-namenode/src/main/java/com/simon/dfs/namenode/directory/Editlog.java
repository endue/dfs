package com.simon.dfs.namenode.directory;

import cn.hutool.json.JSONUtil;

/**
 * @Author:
 * @Description: editlog
 * @Date: 2020/12/30 23:17
 * @Version: 1.0
 */
public class Editlog {

    protected Long txid;
    protected String operation;

    public Editlog(Long txid, String operation) {
        this.txid = txid;
        this.operation = JSONUtil.parseObj(operation).set("txid",this.txid).toString();
    }

    public Long getTxid() {
        return txid;
    }

    public String getOperation() {
        return operation;
    }
}