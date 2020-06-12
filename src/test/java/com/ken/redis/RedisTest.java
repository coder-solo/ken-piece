package com.ken.redis;

import com.ken.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // redis订阅模式生产者
    @Test
    public void testConvertAndSend() {
        stringRedisTemplate.convertAndSend("testWx", "this is test content by wx2.");
    }

    @Test
    public void testQueue() {
        stringRedisTemplate.opsForList().leftPush("test-list", "Order-1");
        stringRedisTemplate.opsForList().leftPush("test-list", "Order-2");
        stringRedisTemplate.opsForList().leftPush("test-list", "Order-3");
    }

    @Test
    public void testQueuePop() {

        System.out.println(stringRedisTemplate.opsForList().leftPop("test-list"));
        System.out.println(stringRedisTemplate.opsForList().leftPop("test-list"));
        System.out.println(stringRedisTemplate.opsForList().leftPop("test-list"));
        System.out.println(stringRedisTemplate.opsForList().leftPop("test-list"));
    }

    private String KEY_TEST = "test:wx:";

    @Test
    public void testBatchInsert() {

        StopWatch sw = new StopWatch("Test");
        sw.start("10w条生成时间");
        Map<String, String> map = LongStream.rangeClosed(1L, 100000L).boxed().collect(Collectors.toMap(a -> KEY_TEST + a, a -> "v" + a));
        sw.stop();

        sw.start("10w条插入时间");
        stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    stringRedisTemplate.opsForValue().set(entry.getKey(), entry.getValue());
                }
                return null;
            }
        });
        sw.stop();
//		stringRedisTemplate.opsForValue().multiSet(map); 1000w条数据报错
        System.out.println(sw.prettyPrint());
//		00073  011%  10w条生成时间
//		00583  089%  10w条插入时间
    }

    @Test
    public void testBatchGet() {

        StopWatch sw = new StopWatch("Test");
        sw.start("10w条Key获取时间");
        List<String> keyList = stringRedisTemplate.keys(KEY_TEST + "*").stream().collect(Collectors.toList());
        sw.stop();

        sw.start("10w条Pipelined读取时间");
        List<Object> objects = stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                for (String key : keyList) {
                    stringRedisConnection.get(key);
                }
                return null;
            }
        });
        List<String> valList = objects.stream().map(Object::toString).collect(Collectors.toList());
        System.out.println("valList:" + valList.size());
        System.out.println("valList:" + valList.get(0));
        sw.stop();

        sw.start("10w条multiGet读取时间");
        List<String> val2List = stringRedisTemplate.opsForValue().multiGet(keyList);
        System.out.println("val2List:" + val2List.size());
        sw.stop();

/*		sw.start("10w条普通循环读取时间");
		List<String> val3List = keyList.stream().map(a -> stringRedisTemplate.opsForValue().get(a)).collect(Collectors.toList());
		System.out.println("val3List:" + val3List.size());
		sw.stop();*/

        System.out.println(sw.prettyPrint());
//		00305  000%  10w条Key获取时间
//		00444  000%  10w条Pipelined读取时间
//		00415  000%  10w条multiGet读取时间
//		309263  100%  10w条普通循环读取时间
    }

}
