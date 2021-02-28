package com.simon.dfs.client;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:
 * @Description:
 * @Date: 2021/1/1 8:39
 * @Version: 1.0
 */
public class FileSystemTest {

    private FileSystem fileSystem;

    @Before
    public void init(){
        fileSystem = new FileSystemImpl();
    }
    @Test
    public void mkdir(){
		for(int j = 0; j < 200; j++) {
			try {
				fileSystem.mkdir("/usr/warehouse/hive" + j + "_" + Thread.currentThread().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*try {
			CountDownLatch countDownLatch = new CountDownLatch(5);
			for(int i = 0; i < 5;i++){
				new Thread(() -> {
					for(int j = 0; j < 200; j++) {
						try {
							fileSystem.mkdir("/usr/warehouse/hive" + j + "_" + Thread.currentThread().getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					countDownLatch.countDown();
				}).start();
			}

			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}

    @Test
    public void shutdown(){
		try {
			fileSystem.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
