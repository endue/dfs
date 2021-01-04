package com.simon.dfs.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/4 23:20
 * @Version: 1.0
 */
public class IOClose {

    public static void close(Closeable ... closeable){
        for (Closeable c : closeable) {
            try {
                if(c != null){
                    c.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
