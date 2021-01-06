package com.simon.dfs.backupdanode.checkpoint;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.simon.dfs.backupdanode.BackupNode;
import com.simon.dfs.backupdanode.fetcher.EditlogFetcher;
import com.simon.dfs.backupdanode.server.FSDirectory;
import com.simon.dfs.backupdanode.server.FSNamesystem;
import com.simon.dfs.common.constants.BackupNodeConstant;
import com.simon.dfs.common.utils.IOClose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Author:
 * @Description: 定时checkpoint内存中的文件目录树
 * @Date: 2021/1/3 22:09
 * @Version: 1.0
 */
public class EditlogCheckpoint extends Thread{
    private final Logger logger = LoggerFactory.getLogger(EditlogCheckpoint.class);
    private BackupNode backupNode;
    private FSNamesystem fsNamesystem;
    private EditlogFetcher editlogFetcher;
    private String lastEditlogCheckpoint;

    public EditlogCheckpoint(BackupNode backupNode, FSNamesystem namesystem, EditlogFetcher editlogFetcher) {
        this.backupNode = backupNode;
        this.fsNamesystem = namesystem;
        this.editlogFetcher = editlogFetcher;
    }

    @Override
    public void run() {
        try {
            while (backupNode.isRunning()){
                Thread.sleep(BackupNodeConstant.EDITLOG_CHECKPOINT_INTERVAL);

                logger.info("开始 checkpoint fetchedMaxTxid {}",editlogFetcher.getFetchedMaxTxid());
                FSDirectory.Node nodeDirectory = fsNamesystem.getDirectory().getNodeDirectory();
                String tree = JSONUtil.toJsonStr(nodeDirectory);
                clearLastEditlogCheckpoint();
                doCheckPoint(tree);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearLastEditlogCheckpoint() {
        File lastEditlogCheckpointFile;
        File lastEditlogCheckpointPath;
        if(StrUtil.isNotEmpty(lastEditlogCheckpoint) && (lastEditlogCheckpointFile = new File(lastEditlogCheckpoint)).exists()){
            lastEditlogCheckpointFile.delete();
        }else if((lastEditlogCheckpointPath = new File(BackupNodeConstant.CHECKPOINT_FILE_PATH)).listFiles().length > 0){
            for (File file : lastEditlogCheckpointPath.listFiles()) {
                file.delete();
            }
        }
    }

    // todo tree内容无更新的话，会做无用操作
    private void doCheckPoint(String tree) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(tree.getBytes());
        lastEditlogCheckpoint = BackupNodeConstant.CHECKPOINT_FILE_PATH + editlogFetcher.getFetchedMaxTxid() + BackupNodeConstant.CHECKPOINT_FILE_SUFFIX;
        RandomAccessFile file = null;
        FileOutputStream out = null;
        FileChannel channel = null;
        FileLock fileLock = null;

        try {
            file = new RandomAccessFile(lastEditlogCheckpoint, "rw");
            out = new FileOutputStream(file.getFD());
            channel = out.getChannel();
            fileLock = channel.lock();
            // 写channel
            channel.write(buffer);
            // 强制把数据刷入磁盘上
            channel.force(false);
        } finally {
            if(fileLock != null){
                fileLock.release();
            }
            IOClose.close(out,file,channel);
        }
    }

}
