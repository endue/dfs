package com.simon.dfs.datanode;

import com.simon.dfs.common.constants.TimesConstant;
import com.simon.dfs.datanode.server.NameNodeOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:
 * @Description: datanode服务类
 * @Date: 2020/12/28 22:39
 * @Version: 1.0
 */
public class DataNode {

    private final Logger logger = LoggerFactory.getLogger(DataNode.class);
    private volatile boolean running = false;

    private NameNodeOfferService offerService;

    private void initalize(){
        this.running = true;
        this.offerService = new NameNodeOfferService();
        this.offerService.start();
    }

    private void run(){
        logger.info("start dataNode");
        while (true){
            try {
                Thread.sleep(TimesConstant.SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DataNode dataNode = new DataNode();
        dataNode.initalize();
        dataNode.run();
    }
}
