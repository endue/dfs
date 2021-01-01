package com.simon.dfs.datanode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:
 * @Description: 负责与一组namenode进行通信
 * @Date: 2020/12/28 22:43
 * @Version: 1.0
 */
public class NameNodeOfferService {

    private final Logger logger = LoggerFactory.getLogger(NameNodeOfferService.class);
    private NameNodeServiceActor leaderServiceActor;

    private CopyOnWriteArrayList<NameNodeServiceActor> serviceActors;

    public NameNodeOfferService() {
        logger.info("init NameNodeOfferService");
        this.leaderServiceActor = new NameNodeServiceActor();
    }

    public void start() {
        register();
        hearbeat();
    }

    private void register(){
        leaderServiceActor.register();
    }

    private void hearbeat(){
        leaderServiceActor.hearbeat();
    }

    public void shutdown(NameNodeServiceActor serviceActor){
        serviceActors.remove(serviceActor);
    }
}
