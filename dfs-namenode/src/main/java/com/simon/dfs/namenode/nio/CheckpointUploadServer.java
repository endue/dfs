package com.simon.dfs.namenode.nio;

import com.simon.dfs.common.constants.BackupNodeConstant;
import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.common.utils.IOClose;
import com.simon.dfs.namenode.directory.FSNamesystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author:
 * @Description: 负责接受checkpoint文件
 * @Date: 2021/1/4 23:04
 * @Version: 1.0
 */
public class CheckpointUploadServer extends Thread {
    private final Logger logger = LoggerFactory.getLogger(CheckpointUploadServer.class);
    private FSNamesystem namesystem;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public CheckpointUploadServer(FSNamesystem namesystem) {
        try {
            this.namesystem = namesystem;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(NameNodeConstant.CHECKPOINT_UPLOAD_IP,NameNodeConstant.CHECKPOINT_UPLOAD_PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("CheckpointUploadServer start success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        handles(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(selector != null){
                try {
                    selector.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void handles(SelectionKey key) {
        try {
            if(key.isAcceptable()){
                handleAcceptableRequest(key);
            } else if(key.isReadable()){
                handleReadableRequest(key);
            } else if(key.isWritable()) {
                handleWritableRequest(key);
            }
        } catch (IOException e) {
            logger.error("uploader server handle error {}",e.getMessage());
        }
    }

    /**
     * 处理连接请求
     * @param key
     * @throws IOException
     */
    private void handleAcceptableRequest(SelectionKey key) throws IOException {
        serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        logger.info("handle acceptable request, clientAddress {}",socketChannel.getRemoteAddress());
        socketChannel.register(selector,SelectionKey.OP_READ);
    }

    /**
     * 处理写事件
     * @param key
     * @throws IOException
     */
    private void handleWritableRequest(SelectionKey key) throws IOException {
        byte[] result = "SUCCESS".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(8 + result.length);
        buffer.putLong(result.length);
        buffer.put(result);
        buffer.flip();

        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.write(buffer);
        logger.info("handle writable request, clientAddress {}",socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 处理读事件
     * @param key
     * @throws IOException
     */
    private void handleReadableRequest(SelectionKey key) throws IOException {
        String lastEditlogCheckpoint = NameNodeConstant.CHECKPOINT_FILE_PATH + "checkpoint" + BackupNodeConstant.CHECKPOINT_FILE_SUFFIX;
        SocketChannel socketChannel;
        RandomAccessFile file = null;
        FileChannel channel = null;

        try {
            ByteBuffer checkpointBuffer = ByteBuffer.allocate(8);
            ByteBuffer lengthBuffer = ByteBuffer.allocate(8);
            socketChannel = (SocketChannel) key.channel();
            int read = socketChannel.read(checkpointBuffer);
            if(read == -1){
                logger.error("client closed");
                throw new IOException("客户端已关闭");
            }else{
                checkpointBuffer.flip();
                long checkpointTxid = checkpointBuffer.getLong();

                socketChannel.read(lengthBuffer);
                lengthBuffer.flip();
                Long bufferSize = lengthBuffer.getLong();

                ByteBuffer fileBuffer = ByteBuffer.allocate(bufferSize.intValue());
                socketChannel.read(fileBuffer);
                fileBuffer.flip();

                File checkpointFile = new File(lastEditlogCheckpoint);
                if(checkpointFile.exists()){
                    checkpointFile.delete();
                }

                file = new RandomAccessFile(lastEditlogCheckpoint, "rw");
                channel = file.getChannel();
                channel.write(fileBuffer);
                channel.force(false);
                logger.info("handle readable request, clientAddress {}",socketChannel.getRemoteAddress());

                namesystem.deleteEditlog(checkpointTxid);
            }

            socketChannel.register(selector, SelectionKey.OP_WRITE);
        } finally {
            IOClose.close(file,channel);
        }
    }

    private void deleteEditLog(long checkpointTxid) {
    }


}
