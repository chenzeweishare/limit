package com.czw.limit.controller;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: czw
 * time:  2018/9/15 11:15
 */

@RestController
public class LimitController {

    private AtomicInteger atomicInteger = new AtomicInteger();
    private Semaphore semaphore = new Semaphore(1); //允许10个线程获取许可.最大的并发数量

    /**
     * 限制某个接口的总并发数, 总请求数, 使用AtomicInteger设置为10, 该方法比较粗暴
     * @param request
     * @return
     */
    @RequestMapping("/test/atomic")
    public String  index(HttpServletRequest request){
        try {
            if (atomicInteger.incrementAndGet() > 3) { //允许2个线程获取许可.最大的并发数量
                return "oh, 你被限流了";
            }
            System.out.println("执行业务.....");
            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {
            atomicInteger.decrementAndGet();
        }
        return  "你没有限流";
    }


    /**
     * 该方式可以重构, Hystrix也有用到, ReteLimiter也和Semaphore有相识之处
     * @param request
     * @return
     */
    @RequestMapping("/test/semaphore")
    public String  toSemaphore(HttpServletRequest request){
        //tryAcquire(long timeout, TimeUnit unit)
        //如果在给定的等待时间内，此信号量有可用的许可并且当前线程未被中断，则从此信号量获取一个许可。
        try {
            if (semaphore.tryAcquire()) {
                System.out.println("执行业务.....");
                try {
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                return "oh, 你被限流了";
            }
            return "你没有被限流";

        } finally {
            semaphore.release();
        }

    }

    //TODO 分布式限流
    //TODO 分布式锁
    //TODO Semaphore 高级版本
    //TODO Nginx限流, ip 接口

}
