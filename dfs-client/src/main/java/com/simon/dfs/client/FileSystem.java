package com.simon.dfs.client;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 8:31
 * @Version: 1.0
 */
public interface FileSystem {

    /**
     * 创建目录
     * @param path 目录对应的路径
     * @throws Exception
     */
    void mkdir(String path) throws Exception;

    /**
     * 优雅关闭
     * @throws Exception
     */
    void shutdown() throws Exception;
}
