package com.simon.dfs.common.utils;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * @Author:
 * @Description:
 * @Date: 2021/2/28 16:28
 * @Version: 1.0
 */
public class FileUtil {

    public static List<File> sortDataDir(File[] files, String suffix, boolean ascending)
    {
        if(files==null) return new ArrayList<File>(0);
        List<File> filelist = Arrays.asList(files);
        Collections.sort(filelist, new DataDirFileComparator(suffix, ascending));
        return filelist;
    }


    private static class DataDirFileComparator implements Comparator<File>, Serializable
    {
        private static final long serialVersionUID = -2648639884525140318L;

        private String suffix;
        private boolean ascending;
        public DataDirFileComparator(String suffix, boolean ascending) {
            this.suffix = suffix;
            this.ascending = ascending;
        }
        // 比较文件的zxid
        // 也就是排除前缀的部分
        public int compare(File o1, File o2) {
            long z1 = getTxidFromName(o1.getName(), suffix);
            long z2 = getTxidFromName(o2.getName(), suffix);
            int result = z1 < z2 ? -1 : (z1 > z2 ? 1 : 0);
            return ascending ? result : -result;
        }
    }

    public static long getTxidFromName(String name, String suffix) {
        long txid = 0;
        // 以.分割文件名
        String nameParts[] = name.split("\\.");
        // 判断文件名是否符合规范
        if (nameParts.length == 2 && nameParts[1].equals(suffix)) {
            try {
                // 解析文件名中的txid
                txid = Long.parseLong(nameParts[0]);
            } catch (NumberFormatException e) {
            }
        }
        return txid;
    }
}
