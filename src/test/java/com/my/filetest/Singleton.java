package com.my.filetest;

/**
 * @author maoyu
 * @Description 单例模式  线程安全
 * @date 2020/5/11 17:37
 */
public class Singleton {
    private volatile static Singleton uniqueInstance;

    public Singleton() {
    }
    private static Singleton getUniqueInstance(){
        //先判断对象是否已经实例化过，没有实例化过才进入加锁代码
        if(uniqueInstance == null){
            synchronized (Singleton.class){
                //类对象加锁
                if (uniqueInstance == null){
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
