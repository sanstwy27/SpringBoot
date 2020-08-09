package com.atkjs927.cache.service;

import com.atkjs927.cache.bean.Employee;
import com.atkjs927.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@EnableCaching
@CacheConfig(cacheNames="emp"/*,cacheManager = "employeeCacheManager"*/) //抽取緩存的公共配置
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 將方法的運行結果進行緩存；以後再要相同的數據，直接從緩存中獲取，不用調用方法；
     * CacheManager管理多個Cache組件的，對緩存的真正CRUD操作在Cache組件中，每一個緩存組件有自己唯一一個名字；
     *

     *
     * 原理：
     *   1、自動配置類；CacheAutoConfiguration
     *   2、緩存的配置類
     *   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默認】
     *   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *   3、哪個配置類默認生效：SimpleCacheConfiguration；
     *
     *   4、給容器中註冊了一個CacheManager：ConcurrentMapCacheManager
     *   5、可以獲取和創建ConcurrentMapCache類型的緩存組件；他的作用將數據保存在ConcurrentMap中；
     *
     *   運行流程：
     *   @Cacheable：
     *   1、方法運行之前，先去查詢Cache（緩存組件），按照cacheNames指定的名字獲取；
     *      （CacheManager先獲取相應的緩存），第一次獲取緩存如果沒有Cache組件會自動創建。
     *   2、去Cache中查找緩存的內容，使用一個key，默認就是方法的參數；
     *      key是按照某種策略生成的；默認是使用keyGenerator生成的，默認使用SimpleKeyGenerator生成key；
     *          SimpleKeyGenerator生成key的默認策略；
     *                  如果沒有參數；key=new SimpleKey()；
     *                  如果有一個參數：key=參數的值
     *                  如果有多個參數：key=new SimpleKey(params)；
     *   3、沒有查到緩存就調用目標方法；
     *   4、將目標方法返回的結果，放進緩存中
     *
     *   @Cacheable標注的方法執行之前先來檢查緩存中有沒有這個數據，默認按照參數的值作為key去查詢緩存，
     *   如果沒有就運行方法並將結果放入緩存；以後再來調用就可以直接使用緩存中的數據；
     *
     *   核心：
     *      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】組件
     *      2）、key使用keyGenerator生成的，默認是SimpleKeyGenerator
     *
     *
     *   幾個屬性：
     *      cacheNames/value：指定緩存組件的名字;將方法的返回結果放在哪個緩存中，是數組的方式，可以指定多個緩存；
     *
     *      key：緩存數據使用的key；可以用它來指定。默認是使用方法參數的值  1-方法的返回值
     *              編寫SpEL； #i d;參數id的值   #a0  #p0  #root.args[0]
     *              getEmp[2]
     *
     *      keyGenerator：key的生成器；可以自己指定key的生成器的組件id
     *              key/keyGenerator：二選一使用;
     *
     *
     *      cacheManager：指定緩存管理器；或者cacheResolver指定獲取解析器
     *
     *      condition：指定符合條件的情況下才緩存；
     *              ,condition = "#id>0"
     *          condition = "#a0>1"：第一個參數的值》1的時候才進行緩存
     *
     *      unless:否定緩存；當unless指定的條件為true，方法的返回值就不會被緩存；可以獲取到結果進行判斷
     *              unless = "#result == null"
     *              unless = "#a0==2":如果第一個參數的值是2，結果不緩存；
     *      sync：是否使用異步模式
     * @param id
     * @return
     *
     */
    @Cacheable(value = {"emp"}/*,keyGenerator = "myKeyGenerator",condition = "#a0>1",unless = "#a0==2"*/)
    public Employee getEmp(Integer id){
        System.out.println("查詢"+id+"號員工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut：既調用方法，又更新緩存數據；同步更新緩存
     * 修改了數據庫的某個數據，同時更新緩存；
     * 運行時機：
     *  1、先調用目標方法
     *  2、將目標方法的結果緩存起來
     *
     * 測試步驟：
     *  1、查詢1號員工；查到的結果會放在緩存中；
     *          key：1  value：lastName：張三
     *  2、以後查詢還是之前的結果
     *  3、更新1號員工；【lastName:zhangsan；gender:0】
     *          將方法的返回值也放進緩存了；
     *          key：傳入的employee對像  值：返回的employee對像；
     *  4、查詢1號員工？
     *      應該是更新後的員工；
     *          key = "#employee.id":使用傳入的參數的員工id；
     *          key = "#result.id"：使用返回後的id
     *             @Cacheable的key是不能用#result
     *      為什麼是沒更新前的？【1號員工沒有在緩存中更新】
     *
     */
    @CachePut(/*value = "emp",*/key = "#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
}

    /**
     * @CacheEvict：緩存清除
     *  key：指定要清除的數據
     *  allEntries = true：指定清除這個緩存中所有的數據
     *  beforeInvocation = false：緩存的清除是否在方法之前執行
     *      默認代表緩存清除操作是在方法執行之後執行;如果出現異常緩存就不會清除
     *
     *  beforeInvocation = true：
     *      代表清除緩存操作是在方法運行之前執行，無論方法是否出現異常，緩存都清除
     *
     *
     */
    @CacheEvict(value="emp",beforeInvocation = true/*key = "#id",*/)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
//        employeeMapper.deleteEmpById(id);
        int i = 10/0;
    }

    // @Caching 定義複雜的緩存規則
    @Caching(
         cacheable = {
             @Cacheable(/*value="emp",*/key = "#lastName")
         },
         put = {
             @CachePut(/*value="emp",*/key = "#result.id"),
             @CachePut(/*value="emp",*/key = "#result.email")
         }
    )

    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }




}
