package com.simon.dfs.namenode.nio;

import com.simon.dfs.common.constants.BackupNodeConstant;
import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.common.utils.IOClose;

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
public class CheckpointUploaderServer extends Thread {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public CheckpointUploaderServer() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(NameNodeConstant.CHECKPOINT_UPLOAD_IP,NameNodeConstant.CHECKPOINT_UPLOAD_PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
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
            e.printStackTrace();
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
        socketChannel.register(selector,SelectionKey.OP_READ);
    }

    /**
     * 处理写事件
     * @param key
     * @throws IOException
     */
    private void handleWritableRequest(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("SUCCESS".getBytes());
        buffer.flip();

        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.write(buffer);
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
        FileOutputStream out = null;
        FileChannel channel = null;

        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel = (SocketChannel) key.channel();
            while (socketChannel.read(buffer) > 0)
            buffer.flip();
            File checkpointFile = new File(lastEditlogCheckpoint);
            if(checkpointFile.exists()){
                checkpointFile.delete();
            }
            file = new RandomAccessFile(lastEditlogCheckpoint, "rw");
            out = new FileOutputStream(file.getFD());
            channel = out.getChannel();
            channel.write(buffer);
            channel.force(false);

            socketChannel.register(selector, SelectionKey.OP_READ);
        } finally {
            IOClose.close(out,file,channel);
        }
    }


}
