package com.simon.dfs.namenode.directory;

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
