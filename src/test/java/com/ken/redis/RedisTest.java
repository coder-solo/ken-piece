package com.ken.redis;

import com.ken.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;

    @Autowired
    @SuppressWarnings("rawtypes")
    private HashOperations hashOperateions;

    @Autowired
    @SuppressWarnings("rawtypes")
    private ValueOperations valueOperations;

    @Autowired
    @SuppressWarnings("rawtypes")
    private ListOperations listOperations;

    @Autowired
    @SuppressWarnings("rawtypes")
    private SetOperations setOperations;

    @Test
    @SuppressWarnings("unchecked")
    public void testRedisTemplate() throws Exception {

        // stringRedisTemplate
        stringRedisTemplate.opsForValue().set("strRt", "testString");
        Assert.assertEquals("testString", stringRedisTemplate.opsForValue().get("strRt"));

        // redisTemplate
        User user = User.builder().code("code").name("Ken").build();
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("userExist", user);
        operations.set("userExistTmp", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        Assert.assertTrue(redisTemplate.hasKey("userExist"));
        Assert.assertFalse(redisTemplate.hasKey("userExistTmp"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAllType() throws Exception {

        // Object
        valueOperations.set("tStr", "testStr");
        String resultStr = (String) valueOperations.get("tStr");
        Assert.assertEquals("testStr", resultStr);

        // Hash
        hashOperateions.put("tHash", "name", "key");
        String resultHash = (String) hashOperateions.get("tHash", "name");
        Assert.assertEquals("key", resultHash);

        // List
        listOperations.leftPush("tList", "testList");
        String resultList = (String) listOperations.leftPop("tList");
        Assert.assertEquals("testList", resultList);

        // Set
        setOperations.add("tSet", "testSet");
        String resultSet = (String) setOperations.pop("tSet");
        Assert.assertEquals("testSet", resultSet);
    }

    @Test
    public void testObj() throws Exception {

    }
}
