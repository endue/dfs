package com.simon.dfs.namenode.directory;

import cn.hutool.json.JSONUtil;
import com.simon.dfs.common.constants.NameNodeConstant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Author:
 * @Description: editlog双缓存区
 * @Date: 2020/12/30 23:16
 * @Version: 1.0
 */
public class DoubleBuffer {

    /**
     * 当前buffer
     */
    private EditlogBuffer currentBuffer = new EditlogBuffer();
    /**
     * 需要刷磁盘buffer
     */
    private EditlogBuffer flushBuffer = new EditlogBuffer();

    // 已刷盘的txid组
    private Map<Long,Long> flushedTxidMap = new HashMap<>();

    /**
     * 将edits log写到内存缓冲里去
     * @param log
     */
    public void write(Editlog log) throws IOException {
        currentBuffer.write(log);
    }

    /**
     * 交换两块缓冲区，为了同步内存数据到磁盘做准备
     */
    public void setReadyToFlush() {
        flushedTxidMap.put(currentBuffer.getFulshMinTxid(),currentBuffer.getFlushMaxTxid());

        EditlogBuffer tmp = currentBuffer;
        currentBuffer = flushBuffer;
        flushBuffer = tmp;
    }

    /**
     * 获取Buffer缓冲区里最大的txid
     * @return
     */
    public Long getFlushMaxTxid() {
        return flushBuffer.getFlushMaxTxid();
    }

    /**
     * 将Buffer刷入磁盘中
     */
    public void flush() {
        flushBuffer.flush();
        flushBuffer.clear();
    }

    /**
     * 是否需要刷磁盘
     * @return
     */
    public boolean shouldFlush() {
        return currentBuffer.size() >= NameNodeConstant.EDIT_LOG_BUFFER_LIMIT;
    }

    public Map<Long, Long> getFlushedTxidMap() {
        return this.flushedTxidMap;
    }

    /**
     * 获取一段范围的editlog
     * @param startTxid
     * @return
     */
    public List<Editlog> getEditlogs(Long startTxid) {
        List<Editlog> list = new ArrayList<>();
        try {
            if(flushedTxidMap.isEmpty()){
                ByteArrayOutputStream arrayOutputStream = currentBuffer.getBuffer();
                if(arrayOutputStream.size() <= 0){
                    return Collections.emptyList();
                }
                String editlogsStr = new String(arrayOutputStream.toByteArray());
                for (String editlogStr : editlogsStr.split("\n")) {
                    list.add(JSONUtil.toBean(editlogStr,Editlog.class));
                }
                return list;
            }
            for (Map.Entry<Long, Long> txidMapEntry : flushedTxidMap.entrySet()) {
                if(txidMapEntry.getValue() <= startTxid){
                    continue;
                }
                List<String> editlogsStr = Files.readAllLines(Paths.get(String.format(NameNodeConstant.EDITLOG_FILE_PATH, txidMapEntry.getKey().toString(), txidMapEntry.getValue().toString())));
                for (String editlogStr : editlogsStr) {
                    list.add(JSONUtil.toBean(editlogStr,Editlog.class));
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
