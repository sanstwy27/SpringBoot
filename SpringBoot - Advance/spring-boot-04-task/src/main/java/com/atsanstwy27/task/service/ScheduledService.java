package com.atkjs927.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    /**
     * second(秒), minute（分）, hour（時）, day of month（日）, month（月）, day of week（周幾）.
     * 0 * * * * MON-FRI
     *  【0 0/5 14,18 * * ?】 每天14點整，和18點整，每隔5分鐘執行一次
     *  【0 15 10 ? * 1-6】 每個月的週一至週六10:15分執行一次
     *  【0 0 2 ? * 6L】每個月的最後一個週六凌晨2點執行一次
     *  【0 0 2 LW * ?】每個月的最後一個工作日凌晨2點執行一次
     *  【0 0 2-4 ? * 1#1】每個月的第一個週一凌晨2點到4點期間，每個整點都執行一次；
     */
    @Scheduled(cron = "0 * * * * MON-SAT")
    //@Scheduled(cron = "0,1,2,3,4 * * * * MON-SAT")
    //@Scheduled(cron = "0-4 * * * * MON-SAT")
    //@Scheduled(cron = "0/4 * * * * MON-SAT")  //每4秒執行一次
    public void hello() {
        System.out.println("hello....");
    }
}
