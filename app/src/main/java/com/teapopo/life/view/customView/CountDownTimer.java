package com.teapopo.life.view.customView;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by louiszgm on 2016/5/10.
 */
public abstract class CountDownTimer {
    /**
     * Millis since epoch when alarm should stop.
     执行的总时间
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     时间间隔
     */
    private final long mCountdownInterval;

    // 停止时间
    private long mStopTimeInFuture;
    //判断任务是否正在执行
    public boolean isRunning;
    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
    两参数构造函数，总时间，时间间隔
     */
    public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     取消到timer
     */
    public final void cancel() {
        isRunning = false;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     开始
     */
    public synchronized final CountDownTimer start() {
        isRunning = true;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        // 停止时间 = 系统启动时间 + 总计时间
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownTimer.this) {
                // 计算剩余总时间
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                // 小于等于 0 ，回调 onFinish
                if (millisLeft <= 0) {
                    isRunning = false;
                    onFinish();
                } else if (millisLeft < mCountdownInterval) { // 小于计时间隔 ，delayed 一个消息
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
