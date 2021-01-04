package com.simon.dfs.backupdanode.nio;

import com.simon.dfs.backupdanode.BackupNode;
import com.simon.dfs.backupdanode.checkpoint.EditlogCheckpoint;
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
    private EditlogCheckpoint editlogCheckpoint;

    private SocketChannel socketChannel;
    private Selector selector;

    public CheckpointUploaderClient(BackupNode backupNode, EditlogCheckpoint editlogCheckpoint) {
        try {
            this.backupNode = backupNode;
            this.editlogCheckpoint = editlogCheckpoint;

            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.bind(new InetSocketAddress(NameNodeConstant.CHECKPOINT_UPLOAD_IP,NameNodeConstant.CHECKPOINT_UPLOAD_PORT));

            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
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
                Thread.sleep(BackupNodeConstant.CHECKPOINT_UPLOAD_INTERVAL);
                logger.info("开始 checkpoint upload");

                boolean uploading = true;
                while(uploading){
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();;

                        if(key.isConnectable()){
                            socketChannel = (SocketChannel) key.channel();
                            if(socketChannel.isConnectionPending()){
                                socketChannel.finishConnect();
                            }
                            socketChannel.register(selector,SelectionKey.OP_WRITE);
                        }else if(key.isReadable()){
                            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                            socketChannel = (SocketChannel) key.channel();
                            int v = -1;
                            int count = 0;
                            while ((v = socketChannel.read(byteBuffer)) != -1){
                                count += v;
                            };
                            System.out.println(new String(byteBuffer.array(),0,count));
                            socketChannel.register(selector,SelectionKey.OP_WRITE);
                            uploading = false;
                        }else if(key.isWritable()){

                            RandomAccessFile file = null;
                            FileOutputStream out = null;
                            FileChannel channel = null;
                            FileLock fileLock = null;

                            try {
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
                                file = new RandomAccessFile(editlogCheckpoint.getLastEditlogCheckpoint(), "rw");
                                out = new FileOutputStream(file.getFD());
                                channel = out.getChannel();
                                fileLock = channel.lock();
                                channel.write(byteBuffer);

                                socketChannel.write(byteBuffer);
                                socketChannel.register(selector,SelectionKey.OP_READ);
                            } finally {
                                fileLock.release();
                                IOClose.close(out,file,channel);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
