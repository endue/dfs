package com.simon.dfs.backupdanode.fetcher;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.simon.dfs.backupdanode.BackupNode;
import com.simon.dfs.backupdanode.rpc.NameNodeRpcClient;
import com.simon.dfs.backupdanode.server.Editlog;
import com.simon.dfs.backupdanode.server.FSNamesystem;
import com.simon.dfs.common.constants.BackupNodeConstant;
import com.simon.dfs.common.utils.EditlogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author:
 * @Description: 定时拉取namenode数据
 * @Date: 2021/1/1 15:32
 * @Version: 1.0
 */
public class EditlogFetcher extends Thread {
    private final Logger logger = LoggerFactory.getLogger(EditlogFetcher.class);
    private BackupNode backupNode;
    private FSNamesystem fsNamesystem;
    private NameNodeRpcClient rpcClient;
    private long fetchedMaxTxid = 0L;


    public EditlogFetcher(BackupNode backupNode, FSNamesystem namesystem) {
        this.backupNode = backupNode;
        this.fsNamesystem = namesystem;
        this.rpcClient = new NameNodeRpcClient();
    }

    @Override
    public void run() {
        while (backupNode.isRunning()){
            try {
                Thread.sleep(BackupNodeConstant.EDITLOG_FETCHER_INTERVAL);

                logger.info("start fetcher with fetchedMaxTxid {}",fetchedMaxTxid);
                List<Editlog> editlogList = rpcClient.fetchBatchEditlog(fetchedMaxTxid);
                for (Editlog editlog : editlogList) {
                    JSONObject operationJSONObject = JSONUtil.parseObj(editlog.getOperation());
                    String op = operationJSONObject.getStr(EditlogUtil.OP);
                    String path = operationJSONObject.getStr(EditlogUtil.PATH);
                    if(EditlogUtil.OP_MKDIR.equals(op)){
                        fsNamesystem.mkdir(path);
                    }
                    this.fetchedMaxTxid = editlog.getTxid();
                }
            } catch (InterruptedException e) {
                logger.error("fetcher error fetchedMaxTxid {}",fetchedMaxTxid);
                e.printStackTrace();
            }
        }
    }
}
