package com.simon.dfs.backupdanode.server;

import java.io.IOException;
import java.util.List;

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
    private DoubleBuffer doubleBuffer = new DoubleBuffer();
    /**
     * 是否需要刷磁盘
     */
    private volatile Boolean needFlush = false;
    /**
     * 正在刷磁盘
     */
    private volatile Boolean flushing = false;
    /**
     * 同步到磁盘中最大txid
     */
    private volatile Long flushedMaxTxid = 0L;
    /**
     * 线程txid
     */
    private ThreadLocal<Long> threadTxid = new ThreadLocal<>();

    /**
     * 写日志
     * @param path
     */
    public void logEdit(String path,String op) {
        synchronized(this) {
            // 如果有线程需要刷磁盘则等待
            waitNeedFlush();
            // 记录日志
            this.txid++;
            long txid = this.txid;
            threadTxid.set(txid);
            Editlog log = new Editlog(txid, path,op);
            try {
                doubleBuffer.write(log);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 当前buffer是否需要刷磁盘
            if(!doubleBuffer.shouldFlush()){
                return;
            }
            // 需要刷磁盘
            needFlush = true;
        }
        // 刷磁盘
        logFlush();
    }

    /**
     * 等待刷磁盘操作结束
     */
    private void waitNeedFlush() {
        try {
            while (needFlush){
                wait(1000);
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
                Long txid = threadTxid.get();
                if(txid <= flushedMaxTxid) {
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

            // 修改最大刷磁盘ID
            flushedMaxTxid = doubleBuffer.getFlushMaxTxid();
            // 交换两块缓冲区
            doubleBuffer.setReadyToFlush();
            needFlush = false;
            notifyAll();
            // 修改正在刷磁盘标识符
            flushing = true;
        }

        this.flush();

        synchronized(this) {
            flushing = false;
            notifyAll();
        }
    }

    public void flush() {
        this.doubleBuffer.flush();
    }

    public List<Editlog> getEditlogs(Long fetchedMaxTxid) {
        synchronized (this){
            return doubleBuffer.getEditlogs(fetchedMaxTxid);
        }
    }
}
