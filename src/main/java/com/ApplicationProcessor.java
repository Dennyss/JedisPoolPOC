package com;

import com.redis.RedisDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Denys Kovalenko on 10/16/2015.
 */
@Component
public class ApplicationProcessor {

    @Autowired
    private RedisDAOImpl redisDAO;

    @Value("${number.of.keys}")
    private int numberOfKeys;

    @Value("${processing.interval}")
    private long processingInterval;

    public void process(String mode) {
        if ("insert".equals(mode)) {
            insertRecords();
        } else if ("retrieve".equals(mode)) {
            retrieveRecords();
        } else {
            throw new IllegalArgumentException("Invalid command line parameter: " + mode + ". Please use 'insert' or 'retrieve'");
        }
    }

    private void insertRecords() {
        for (int i = 0; i < numberOfKeys; i++) {
            redisDAO.insertRecord("key" + i, "value" + i);
            System.out.println("Record is inserted with key: key" + i);
            sleepInterval();
        }
    }

    private void retrieveRecords() {
        for (int i = 0; i < numberOfKeys; i++) {
            String value = redisDAO.retrieveRecord("key" + i);
            System.out.println("Record is retrieved with value: " + value);
            sleepInterval();
        }
    }

    private void sleepInterval() {
        try {
            Thread.currentThread().sleep(processingInterval);
        } catch (InterruptedException e) {
            return;
        }
    }

}
