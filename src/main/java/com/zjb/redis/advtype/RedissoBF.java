package com.zjb.redis.advtype;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @ClassName RedissoBF
 * @Description TODO
 * @Author zhengjiabin
 * @Date 2024/6/7 17:56
 * @Version 1.0
 **/
public class RedissoBF {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.150.35.6:6379");

        RedissonClient redisson = Redisson.create(config);

            RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");

            bloomFilter.tryInit(20000L,0.03);

        for (int i = 0; i<=10086; i++){
            bloomFilter.add(String.valueOf(i));
        }

        System.out.println("996:BF--" + bloomFilter.contains("996"));
        System.out.println("10086:BF--" + bloomFilter.contains("10086"));
        System.out.println("10088:BF--" + bloomFilter.contains("10088"));
        System.out.println("10096:BF--" + bloomFilter.contains("10096"));
        System.out.println("10340:BF--" + bloomFilter.contains("10340"));


    }
}
