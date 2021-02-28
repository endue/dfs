package com.simon.dfs.namenode.directory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.simon.dfs.common.constants.NameNodeConstant;
import com.simon.dfs.common.utils.IOClose;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author:
 * @Description: 文件目录树
 * @Date: 2020/12/29 7:41
 * @Version: 1.0
 */
public class FSDirectory {

    private Node nodeDirectory;

    public FSDirectory() {
        nodeDirectory = new Node(StrUtil.SLASH);
    }

    /**
     * 创建目录
     * @param path
     */
    public void mkdir(String path) {
        synchronized(this) {
            if(StrUtil.isBlankIfStr(path)) return;

            int firstIndex = path.indexOf(StrUtil.SLASH);
            String[] childPath = path.substring(firstIndex + 1).split(StrUtil.SLASH);

            Node parent = nodeDirectory;

            for(String children : childPath) {
                Node dir = findDirectory(parent, children);
                if(dir != null) {
                    parent = dir;
                    continue;
                }

                Node child = new Node(children);
                parent.getChildren().add(child);
                parent = child;
            }
        }
    }

    /**
     * 查找与当前目录名称相同的路径名称
     * @param dir
     * @param path
     * @return
     */
    private Node findDirectory(Node dir, String path) {
        if(dir.getChildren().size() == 0) {
            return null;
        }
        for(Node child : dir.getChildren()) {
            if((child.getDirectory().equals(path))) {
                return child;
            }
        }
        return null;
    }

    /**
     * 恢复磁盘上的editlog
     */
    public void recoverEditLogs() {
        File editlogsPath = null;
        RandomAccessFile randomAccessFile = null;
        try {
            editlogsPath  = new File(NameNodeConstant.EDITLOG_PATH);
            File[] files = editlogsPath.listFiles();
            if(ArrayUtil.isNotEmpty(files)){
                List<File> filelist = Arrays.asList(files);

                Collections.sort(filelist, (o1, o2) -> {
                    String[] split1 = o1.getName().split(StrUtil.DASHED);
                    Integer n1 = Integer.valueOf(split1[0]);
                    String[] split2 = o2.getName().split(StrUtil.DASHED);
                    Integer n2 = Integer.valueOf(split2[0]);
                    return n1 - n2;
                });
                for (File file : filelist) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    String line = null;
                    while (StrUtil.isNotEmpty(line = randomAccessFile.readLine())){
                        Editlog el = JSONUtil.toBean(line, Editlog.class);
                        this.mkdir(el.getPath());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOClose.close(randomAccessFile);
        }
    }

    public void resetNodeDirectory(Node node) {
        this.nodeDirectory = node;
    }

    /**
     * 目录或文件
     */
    public static class Node{

        private String directory;

        private List<Node> children;

        public Node() {
        }

        public Node(String directory) {
            this.directory = directory;
            this.children = new LinkedList<>();
        }

        public String getDirectory() {
            return directory;
        }

        public void setDirectory(String directory) {
            this.directory = directory;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }
    }
}
