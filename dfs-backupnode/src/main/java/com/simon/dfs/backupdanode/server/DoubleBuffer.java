package com.simon.dfs.backupdanode.server;

import com.simon.dfs.common.constants.NameNodeConstant;

import java.io.IOException;

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
}
