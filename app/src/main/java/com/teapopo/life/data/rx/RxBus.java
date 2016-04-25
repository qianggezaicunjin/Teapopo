package com.teapopo.life.data.rx;

/**
 * Created by louiszgm on 16/1/7.
 * 该类是RxBus的实现
 * 以下例子用两种方式来实现了该类的单例模式,分别是懒汉式和饿汗式
 * 两者的区别为:
 *    如果不需要外部通过构造函数传入参数的话，就用饿汉式，否则的话就用懒汉式。用懒汉式的时候要记得考虑线程安全的问题
 * 其中懒汉式通过双重检察机制来实现线程安全,其中要以volatile修饰的原因为以下:
 *    假设没有关键字volatile的情况下，两个线程A、B，都是第一次调用该单例方法，线程A先执行instance = new Instance()，
 *    该构造方法是一个非原子操作，编译后生成多条字节码指令，由于JAVA的指令重排序，可能会先执行instance的赋值操作，
 *    该操作实际只是在内存中开辟一片存储对象的区域后直接返回内存的引用，之后instance便不为空了，但是实际的初始化操作却还没有执行，
 *    如果就在此时线程B进入，就会看到一个不为空的但是不完整（没有完成初始化）的Instance对象，所以需要加入volatile关键字，禁止指令重排序优化，从而安全的实现单例。
 * 饿汉式天生就是线程安全,因为它是在类加载的时候就初始化了他额实例.
 */

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by louiszgm on 16/1/7.
 */
public class RxBus {
    private SerializedSubject<Object, Object> rxBus;
    private SerializedSubject<Object, Object> rxStickBus;

    @SuppressWarnings("unchecked")
    private RxBus() {
        rxBus = new SerializedSubject(PublishSubject.create());
        rxStickBus = new SerializedSubject(BehaviorSubject.create());
    }

    private static class SingletonHolder {
        /**
         * 懒汉式的单例模式,
         */
        // ------------------------------------------------------
        //    private static volatile RxBus mDefaultInstance;
        //    public static RxBus getDefault() {
        //        if (mDefaultInstance == null) {
        //            synchronized (RxBus.class) {
        //                if (mDefaultInstance == null) {
        //                    mDefaultInstance = new RxBus();
        //                }
        //            }
        //        }
        //        return mDefaultInstance;
        //    }
        // ------------------------------------------------------
        /**
         * 饿汉式单例类.在类初始化时，已经自行实例化
         */
        private static RxBus instance = new RxBus();
    }

    public static RxBus getInstance() {
        return SingletonHolder.instance;
    }

    public void postEvent(Object event) {
        if (this.hasObservers()) rxBus.onNext(event);
    }

    public void postStickEvent(Object event) {
        rxStickBus.onNext(event);
    }

    public <T> Observable<T> toObservableByType(Class<T> type) {
        return rxBus.asObservable().ofType(type).onBackpressureBuffer();
    }

    public Observable<Object> toObservable() {
        return rxBus;
    }
    public <T> Observable<T> toStickObservable(Class<T> type) {
        return rxStickBus.asObservable().ofType(type).onBackpressureBuffer();
    }

    private boolean hasObservers() {
        return rxBus.hasObservers();
    }

    @Deprecated
    public boolean hasStickObservers() {
        return rxStickBus.hasObservers();
    }

}