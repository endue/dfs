package com.simon.dfs.backupdanode.server;

import cn.hutool.json.JSONUtil;
import com.simon.dfs.common.constants.BackupNodeConstant;
import com.simon.dfs.common.constants.NameNodeConstant;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author:
 * @Description: editlog内存缓存区
 * @Date: 2021/1/1 8:01
 * @Version: 1.0
 */
public class EditlogBuffer {

    private ByteArrayOutputStream buffer;
    private Long flushMaxTxid = -1L;
    private Long fulshMinTxid = -1L;


    public EditlogBuffer() {
        this.buffer = new ByteArrayOutputStream(NameNodeConstant.EDIT_LOG_BUFFER_LIMIT.intValue());
    }

    public void write(Editlog editLog) throws IOException {
        if(fulshMinTxid < 0 || fulshMinTxid > editLog.txid){
            this.fulshMinTxid = editLog.txid;
        }
        if(flushMaxTxid < editLog.txid){
            this.flushMaxTxid = editLog.txid;
        }
        this.buffer.write(JSONUtil.toJsonStr(editLog).getBytes());
        this.buffer.write("\n".getBytes());
    }

    /**
     * 缓存区数据刷入磁盘
     */
    public void flush(){
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer.toByteArray());
        RandomAccessFile randomAccessFile = null;
        FileOutputStream outputStream = null;
        FileChannel fileChannel = null;

        try {
            randomAccessFile = new RandomAccessFile(String.format(BackupNodeConstant.EDITLOG_FILE_PATH,fulshMinTxid,flushMaxTxid),"rw");
            outputStream = new FileOutputStream(randomAccessFile.getFD());
            fileChannel = outputStream.getChannel();

            fileChannel.write(byteBuffer);
            fileChannel.force(false);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            fulshMinTxid = flushMaxTxid = -1L;

            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileChannel != null){
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int size(){
        return this.buffer.size();
    }

    public void clear(){
        this.buffer.reset();
    }

    public Long getFlushMaxTxid() {
        return flushMaxTxid;
    }

    public Long getFulshMinTxid() {
        return fulshMinTxid;
    }

    public ByteArrayOutputStream getBuffer() {
        return buffer;
    }
}
