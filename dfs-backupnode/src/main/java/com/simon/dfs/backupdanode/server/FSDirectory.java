package com.simon.dfs.backupdanode.server;

import cn.hutool.core.util.StrUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author:
 * @Description: 文件目录树
 * @Date: 2020/12/29 7:41
 * @Version: 1.0
 */
public class FSDirectory {

    private NodeDirectory nodeDirectory;

    public FSDirectory() {
        nodeDirectory = new NodeDirectory(StrUtil.SLASH);
    }

    /**
     * 创建目录
     * @param path
     */
    public void mkdir(String path) {
        synchronized(nodeDirectory) {
            if(StrUtil.isBlankIfStr(path)) return;

            int firstIndex = path.indexOf(StrUtil.SLASH);
            String[] childPath = path.substring(firstIndex + 1).split(StrUtil.SLASH);

            NodeDirectory parent = nodeDirectory;

            for(String children : childPath) {
                NodeDirectory dir = findDirectory(parent, children);
                if(dir != null) {
                    parent = dir;
                    continue;
                }

                NodeDirectory child = new NodeDirectory(children);
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
    private NodeDirectory findDirectory(NodeDirectory dir, String path) {
        if(dir.getChildren().size() == 0) {
            return null;
        }
        for(Node child : dir.getChildren()) {
            if(child instanceof NodeDirectory) {
                NodeDirectory childDir = (NodeDirectory) child;
                if((childDir.getDirectory().equals(path))) {
                    return childDir;
                }
            }
        }
        return null;
    }

    /**
     * 目录
     */
    public static class NodeDirectory implements Node{

        private String directory;

        private List<Node> children;

        public NodeDirectory(String directory) {
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

    /**
     * 文件
     */
    public static class NodeFile implements Node{
        private String fileName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

    /**
     * 接口
     */
    private interface Node{}
}
