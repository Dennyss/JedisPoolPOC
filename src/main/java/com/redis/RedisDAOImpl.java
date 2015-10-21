package com.redis;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Denys Kovalenko on 10/16/2015.
 */
@Component
public class RedisDAOImpl implements InitializingBean, DisposableBean {

    @Value("${redis.sentinel.host}")
    private String redisHost;

    @Value("${redis.sentinel.port}")
    private int redisPort;

    @Value("${redis.master.name}")
    private String redisMasterName;

    private JedisSentinelPool jedisSentinelPool;

    private Set<String> sentinels = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        sentinels.add(redisHost + ":" + redisPort);

        jedisSentinelPool = new JedisSentinelPool(redisMasterName, sentinels);
    }

    @Override
    public void destroy() throws Exception {
        jedisSentinelPool.close();
        jedisSentinelPool.destroy();
    }

    public void insertRecord(String key, String value) {
        Jedis jedis = jedisSentinelPool.getResource();
        jedis.set(key, value);
    }

    public String retrieveRecord(String key) {
        Jedis jedis = jedisSentinelPool.getResource();
        return jedis.get(key);
    }
}
