package com.teapopo.life.util.Constans;

/**
 * Created by louiszgm on 2016/5/20.
 * 用于区分model请求数据的动作，通过RequestView通知viewmodel做出相应的动作
 */
public class ModelAction<T> {
    public Action action;
    public T t;
}
