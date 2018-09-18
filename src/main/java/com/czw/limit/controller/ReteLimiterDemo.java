package com.czw.limit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.RateLimiter;



/**
 * 1.令牌桶算法, 是有一个存放固定容量令牌的桶, 按照固定的速率往桶里添加令牌
 * 2.平滑限流某个接口的请求数
 * 有很多个任务，但希望每秒不超过X个，可用此类 
 * https://www.cnblogs.com/yeyinfu/p/7316972.html
 */ 
public class ReteLimiterDemo {
	
	 public static void main(String[] args) {  
	        //create(1)表示桶容量为1, 切每秒新增1个, 即1秒钟新增一个令牌
	        RateLimiter rateLimiter = RateLimiter.create(1);
	        List<Runnable> tasks = new ArrayList<Runnable>();  
	        for (int i = 0; i < 5; i++) {  
	            tasks.add(new UserRequest(i));  
	        }  
	        ExecutorService threadPool = Executors.newCachedThreadPool();  
	        for (Runnable runnable : tasks) {
	            //acquire表示消费1个
                //rateLimiter.acquire()该方法会阻塞线程，直到令牌桶中能取到令牌为止才继续向下执行，并返回等待的时间。
	            System.out.println("等待时间：" + rateLimiter.acquire());  
	            threadPool.execute(runnable);  
	        }  
	        threadPool.shutdown();
	    }  
	  
	    private static class UserRequest implements Runnable {  
	        private int id;  
	  
	        public UserRequest(int id) {  
	            this.id = id;  
	        }  
	  
	        public void run() {  
	            System.out.println(id);  
	        }  
	    }


    //tryAcquire(long timeout, TimeUnit unit)
    //从RateLimiter 获取许可如果该许可可以在不超过timeout的时间内获取得到的话，
    //或者如果无法在timeout 过期之前获取得到许可的话，那么立即返回false（无需等待）

}
