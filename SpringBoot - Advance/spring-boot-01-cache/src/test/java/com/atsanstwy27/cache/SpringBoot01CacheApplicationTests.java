package com.atkjs927.cache;

import com.atkjs927.cache.bean.Employee;
import com.atkjs927.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpringBoot01CacheApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate; // k-v string

    @Autowired
    RedisTemplate redisTemplate; // k-v Instance

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void test01() {
        //stringRedisTemplate.opsForValue().append("msg", "hello");

        //String msg = stringRedisTemplate.opsForValue().get("msg");
        //System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("mylist", "1");
        stringRedisTemplate.opsForList().leftPush("mylist", "2");
        stringRedisTemplate.opsForList().leftPush("mylist", "3");
    }

    @Test
    public void test02() {
        Employee employee = employeeMapper.getEmpById(1);
        empRedisTemplate.opsForValue().set("emp-01", employee);
    }

    @Test
    void contextLoads() {

        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee);

    }

}
