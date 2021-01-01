package com.simon.dfs.namenode.directory;

import java.io.IOException;

/**
 * @Author:
 * @Description:
 * @Date: 2020/12/29 7:41
 * @Version: 1.0
 */
public class FSEditlog {

    /**
     * 当前递增到的txid的序号
     */
    private long txid = 0L;
    /**
     * 内存双缓冲区
     */
    private DoubleBuffer editLogBuffer = new DoubleBuffer();
    /**
     * 是否需要刷磁盘
     */
    private volatile Boolean shouldFlush = false;
    /**
     * 正在刷磁盘
     */
    private volatile Boolean flushing = false;
    /**
     * 同步到磁盘中最大txid
     */
    private volatile Long flushMaxTxid = 0L;
    /**
     * 线程txid
     */
    private ThreadLocal<Long> threadTxid = new ThreadLocal<>();

    /**
     * 写日志
     * @param operation
     */
    public void logEdit(String operation) {
        synchronized(this) {
            // 如果有线程正在刷磁盘则等待
            waitFlush();
            // 组装日志
            txid++;
            long txid = this.txid;
            threadTxid.set(txid);
            EditLog log = new EditLog(txid, operation);
            // 写日志
            try {
                editLogBuffer.write(log);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 当前buffer是否需要刷磁盘
            if(!editLogBuffer.shouldFlush()){
                return;
            }
            // 需要刷磁盘
            shouldFlush = true;
        }
        // 刷磁盘
        logFlush();
    }

    /**
     * 等待刷磁盘操作结束
     */
    private void waitFlush() {
        try {
            while (shouldFlush){
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 刷磁盘
     */
    private void logFlush() {
        synchronized(this) {
            // 其他线程正在刷磁盘
            if(flushing) {
                // 当前线程日志已被其他线程处理,退出本次刷磁盘操作
                long txid = threadTxid.get();
                if(txid <= flushMaxTxid) {
                    return;
                }
                // 等待其他线程刷磁盘完毕
                while(flushing) {
                    try {
                        wait(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 交换两块缓冲区
            editLogBuffer.setReadyToFlush();
            shouldFlush = false;
            notifyAll();
            // 记录最大刷磁盘ID
            flushMaxTxid = editLogBuffer.getFlushMaxTxid();
            // 修改正在刷磁盘标识符
            flushing = true;
        }

        editLogBuffer.flush();

        synchronized(this) {
            flushing = false;
            notifyAll();
        }
    }

    public void flush() {
        this.editLogBuffer.setReadyToFlush();
        this.editLogBuffer.flush();
    }
}
