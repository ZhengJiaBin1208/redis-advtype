package com.zjb.redis.advtype;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName HyperLogLogTest
 * @Description HyperLogLog测试UV与set的对比
 * @Author zhengjiabin
 * @Date 2024/6/12 11:26
 * @Version 1.0
 **/
public class HyperLogLogTest {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379,30000);

        // 这里使用HyperLogLog放入1万个元素,然后进行UV统计
        Jedis hyperJedis = null;
        try {
            hyperJedis = jedisPool.getResource();
            hyperJedis.del("hyper-count");
            for (int i = 0; i < 10000; i++) {
                hyperJedis.pfadd("hyper-count", "user" + i);
            }
            long pfcount = hyperJedis.pfcount("hyper-count");
            System.out.println("实际次数:" + 10000 + ",HyperLogLog统计次数:" + pfcount);
        }catch (Exception e){
                e.printStackTrace();
            }finally {
                hyperJedis.close();
            }

        // 这里使用set放入1万个元素,然后进行UV统计
        Jedis setJedis = null;
        try {
            setJedis = jedisPool.getResource();
            setJedis.del("set-count");
            for (int i = 0; i < 10000; i++) {
                setJedis.sadd("set-count", "user" + i);
            }
            long setCount = setJedis.scard("set-count");
            System.out.println("实际次数:" + 10000 + ",set统计次数:" + setCount);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            setJedis.close();
        }
        }
    }

