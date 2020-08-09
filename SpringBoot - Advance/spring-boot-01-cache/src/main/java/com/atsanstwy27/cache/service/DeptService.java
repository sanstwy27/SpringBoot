package com.atkjs927.cache.service;

import com.atkjs927.cache.bean.Department;
import com.atkjs927.cache.mapper.DepartmentMapper;
import com.atkjs927.cache.bean.Department;
import com.atkjs927.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;


@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("deptCacheManager")
    @Autowired
    RedisCacheManager deptCacheManager;


    /**
     *  緩存的數據能存入redis；
     *  第二次從緩存中查詢就不能反序列化回來；
     *  存的是dept的json數據;CacheManager默認使用RedisTemplate<Object, Employee>操作Redis
     *
     *
     * @param id
     * @return
     */
//    @Cacheable(cacheNames = "dept",cacheManager = "deptCacheManager")
//    public Department getDeptById(Integer id){
//        System.out.println("查詢部門"+id);
//        Department department = departmentMapper.getDeptById(id);
//        return department;
//    }

    // 使用緩存管理器得到緩存，進行api調用
    public Department getDeptById(Integer id){
        System.out.println("查詢部門"+id);
        Department department = departmentMapper.getDeptById(id);

        //獲取某個緩存
        Cache dept = deptCacheManager.getCache("dept");
        dept.put("mykey:1",department);

        return department;
    }


}
