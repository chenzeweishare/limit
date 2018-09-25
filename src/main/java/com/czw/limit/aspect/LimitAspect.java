package com.czw.limit.aspect;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.czw.limit.exception.LimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author czw
 * @date 2018/9/25 23:19
 */
@Component
public class LimitAspect {

    private Semaphore semaphore = null;

    /**
     * 访问频率
     */
    @Value("${access.frequency}")
    private int frequency;


    /**
     * 毫秒
     */
    private int timout = 1000;


    @Pointcut("execution(public * com.czw.limit.controller.*.*(..))")
    public void limit(){

    }


    @Before("limit()")
    public void doBefore(JoinPoint point) throws Throwable {
        System.out.println("进来doBefore方法了============");
        if (null == semaphore) {
            semaphore = new Semaphore(1);
        }
        if (!semaphore.tryAcquire(timout, TimeUnit.MILLISECONDS)) {
            throw  new LimitException("挂了");
        }
    }


    @AfterThrowing(value = "limit()", throwing = "ex")
    public String afterError(JoinPoint joinPoint, Exception ex){
        return  ex.getMessage();
    }


    @After("limit()")
    public void after(){
        System.out.println("进来after方法了============");
        semaphore.release();
    }
}
