package com.simon.dfs.namenode.directory;

import cn.hutool.json.JSONUtil;

/**
 * @Author:
 * @Description: editlog
 * @Date: 2020/12/30 23:17
 * @Version: 1.0
 */
public class EditLog {

    protected Long txid;
    protected String operation;

    public EditLog(Long txid, String operation) {
        this.txid = txid;
        this.operation = JSONUtil.parseObj(operation).set("txid",this.txid).toString();
    }
}