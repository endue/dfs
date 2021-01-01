package com.simon.dfs.namenode.directory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.common.utils.EditlogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
    private final Logger logger = LoggerFactory.getLogger(DoubleBuffer.class);
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
        return currentBuffer.getFlushMaxTxid();
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

    /**
     * 获取一段范围的editlog
     * @param fetchedMaxTxid
     * @return
     */
    public List<Editlog> getEditlogs(Long fetchedMaxTxid) {
        List<Editlog> list = new ArrayList<>();
        try {
            // 从内存记录的flush文件读
            for (Map.Entry<Long, Long> txidMapEntry : flushedTxidMap.entrySet()) {
                if(txidMapEntry.getValue() <= fetchedMaxTxid){
                    continue;
                }
                List<String> editlogsStr = Files.readAllLines(Paths.get(String.format(NameNodeConstant.EDITLOG_FILE_PATH, txidMapEntry.getKey().toString(), txidMapEntry.getValue().toString())));
                for (String editlogStr : editlogsStr) {
                    list.add(JSONUtil.toBean(editlogStr,Editlog.class));
                }
                break;
            }
            // 从磁盘文件读
            File editlogs = new File(NameNodeConstant.EDITLOG_PATH);
            if(list.isEmpty() && editlogs.isDirectory() && editlogs.listFiles().length > 0){
                List<File> fileList = Arrays.asList(editlogs.listFiles());
                Collections.sort(fileList, Comparator.comparing(file -> EditlogUtil.getEditlogMinTxid(file.getPath())));
                for (File file : fileList) {
                    if(EditlogUtil.getEditlogMaxTxid(file.getPath()) <= fetchedMaxTxid){
                        continue;
                    }
                    List<String> editlogsStr = Files.readAllLines(Paths.get(file.getPath()));
                    for (String editlogStr : editlogsStr) {
                        list.add(JSONUtil.toBean(editlogStr,Editlog.class));
                    }
                    break;
                }
            }
           // 从内存buffer读
            if(list.isEmpty() && currentBuffer.getBuffer().size() > 0){
                ByteArrayOutputStream arrayOutputStream = currentBuffer.getBuffer();
                if(arrayOutputStream.size() <= 0){
                    return Collections.emptyList();
                }
                String editlogsStr = new String(arrayOutputStream.toByteArray());
                for (String editlogStr : editlogsStr.split("\n")) {
                    list.add(JSONUtil.toBean(editlogStr,Editlog.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
