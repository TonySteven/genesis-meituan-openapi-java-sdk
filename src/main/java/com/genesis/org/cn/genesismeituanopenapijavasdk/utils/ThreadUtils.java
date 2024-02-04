package com.genesis.org.cn.genesismeituanopenapijavasdk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 线程池工具类
 */
public class ThreadUtils {

    /**
     * 线程等待封装
     * @param futureList 线程列表
     * @return 结果
     * @param <R> 线程返回类型
     */
    public static <R> List<R> threadWaiting(List<Future<R>> futureList){

        try {
            List<R> successList = new ArrayList<>();

            //查询任务执行的结果
            for (Future<R> future : futureList) {
                //CPU高速轮询：每个future都并发轮循，判断完成状态然后获取结果，这一行，是本实现方案的精髓所在。
                // 即有10个future在高速轮询，完成一个future的获取结果，就关闭一个轮询
                while (true) {
                    //获取future成功完成状态，如果想要限制每个任务的超时时间，取消本行的状态判断+future.get(1000*1, TimeUnit.MILLISECONDS)+catch超时异常使用即可。
                    if (future.isDone() && !future.isCancelled()) {
                        // 获取结果
                        R result = future.get();
                        successList.add(result);
                        // 当前future获取结果完毕，跳出while
                        break;
                    } else {
                        // 每次轮询休息10毫秒（CPU纳秒级），避免CPU高速轮循耗空CPU---》新手别忘记这个
                        Thread.sleep(10);
                    }
                }
            }

            return successList;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
