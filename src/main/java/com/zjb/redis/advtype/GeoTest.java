package com.zjb.redis.advtype;

import jdk.nashorn.internal.objects.NativeUint8Array;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.List;

/**
 * @ClassName GeoTest
 * @Description TODO
 * @Author zhengjiabin
 * @Date 2024/6/12 15:53
 * @Version 1.0
 **/
public class GeoTest {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 30000);
        Jedis geoJedis = null;
        try {
            geoJedis = jedisPool.getResource();
            geoJedis.del("geo-cities");
            geoJedis.geoadd("geo-cities", 116.28, 39.55, "北京");
            geoJedis.geoadd("geo-cities", 117.12, 39.08, "天津");
            geoJedis.geoadd("geo-cities", 114.29, 38.02, "石家庄");
            geoJedis.geoadd("geo-cities", 118.01, 39.38, "唐山");
            geoJedis.geoadd("geo-cities", 115.29, 38.51, "保定");

            GeoRadiusParam geoRadiusParam = new GeoRadiusParam();
            geoRadiusParam.withDist();
            //降序
            geoRadiusParam.sortDescending();
            List<GeoRadiusResponse> geoRadiusResponses = geoJedis.georadiusByMember("geo-cities", "北京", 150, GeoUnit.KM, geoRadiusParam);
            for (GeoRadiusResponse geoRadiusRespons : geoRadiusResponses) {
                System.out.println(geoRadiusRespons.getMemberByString() + ":" + geoRadiusRespons.getDistance() + "KM");
//                System.out.println(geoRadiusRespons.getCoordinate());
                System.out.println("-------------------------------------");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            geoJedis.close();
        }
    }
}
