package com.simon.dfs.backupdanode.nio;

import com.simon.dfs.backupdanode.BackupNode;
import com.simon.dfs.common.constants.BackupNodeConstant;
import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.common.utils.IOClose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author:
 * @Description: 负责将checkpoint文件定时上传到namenode
 * @Date: 2021/1/4 22:25
 * @Version: 1.0
 */
public class CheckpointUploaderClient extends Thread {

    private final Logger logger = LoggerFactory.getLogger(CheckpointUploaderClient.class);
    private BackupNode backupNode;

    private SocketChannel socketChannel;
    private Selector selector;

    public CheckpointUploaderClient(BackupNode backupNode) {
        try {
            this.backupNode = backupNode;
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(NameNodeConstant.CHECKPOINT_UPLOAD_IP,NameNodeConstant.CHECKPOINT_UPLOAD_PORT));

            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            logger.info("CheckpointUploaderClient start success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时读取checkpoint文件,执行上传操作
     */
    @Override
    public void run() {
        try {
            while (backupNode.isRunning()){
                selector.select();
                logger.info("checkpoint upload client");
                Thread.sleep(BackupNodeConstant.CHECKPOINT_UPLOAD_INTERVAL);

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    try {
                        SelectionKey key = iterator.next();
                        iterator.remove();;
                        handles(key);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(selector != null){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handles(SelectionKey key) {
        try {
            if(key.isConnectable()){
                handleConnectableRequest(key);
            }else if(key.isReadable()){
                handleReadableRequest(key);
            }else if(key.isWritable()){
                handleWritableRequest(key);
            }
        }catch (Exception e){
            logger.error("uploader client handle error {}",e.getMessage());
        }
    }

    /**
     * 处理写事件
     * @param key
     * @throws IOException
     */
    private void handleWritableRequest(SelectionKey key) throws IOException {
        RandomAccessFile file = null;
        FileChannel channel = null;

        try {
            socketChannel = (SocketChannel) key.channel();

            File checkpointPath = new File(BackupNodeConstant.CHECKPOINT_FILE_PATH);
            File[] files = checkpointPath.listFiles();
            for (File checkpointFile : files) {
                String checkpointTxid = checkpointFile.getName().replace(BackupNodeConstant.CHECKPOINT_FILE_SUFFIX, "");

                file = new RandomAccessFile(checkpointFile, "rw");
                channel = file.getChannel();
                Long dataSize = checkpointFile.length();
                ByteBuffer fileBuffer = ByteBuffer.allocate(8 + 8 + dataSize.intValue());
                // 读取文件内容
                fileBuffer.putLong(Long.valueOf(checkpointTxid));
                fileBuffer.putLong(dataSize);
                channel.read(fileBuffer);
                fileBuffer.flip();
                socketChannel.write(fileBuffer);
            }

            socketChannel.register(selector,SelectionKey.OP_READ);
            logger.info("client checkpoint upload finish");
        } finally {
            IOClose.close(file,channel);
        }
    }

    /**
     * 处理读事件
     * @param key
     * @throws IOException
     */
    private void handleReadableRequest(SelectionKey key) throws IOException {

        socketChannel = (SocketChannel) key.channel();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(8);
        socketChannel.read(lengthBuffer);
        lengthBuffer.flip();
        Long length = lengthBuffer.getLong();

        ByteBuffer dataBuffer = ByteBuffer.allocate(length.intValue());
        socketChannel.read(dataBuffer);
        dataBuffer.flip();
        System.out.println(new String(dataBuffer.array()));

        socketChannel.register(selector,SelectionKey.OP_WRITE);
        logger.info("client checkpoint upload success");
    }

    /**
     * 处理连接事件
     * @param key
     * @throws IOException
     */
    private void handleConnectableRequest(SelectionKey key) throws IOException {
        socketChannel = (SocketChannel) key.channel();
        if(socketChannel.isConnectionPending()){
            socketChannel.finishConnect();
        }
        socketChannel.register(selector,SelectionKey.OP_WRITE);
    }
}
