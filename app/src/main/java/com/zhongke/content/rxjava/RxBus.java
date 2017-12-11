package com.zhongke.content.rxjava;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zhongke.content.entity.event.SelectWishEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static android.content.ContentValues.TAG;

/**
 * Created by hyx on 2017/12/4.
 *
 */

public class RxBus {
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();
    private static volatile RxBus instance;

    private RxBus() {
    }

    public static RxBus getInstance() {
        RxBus inst = instance;
        if (inst == null) {
            synchronized (RxBus.class) {
                inst = instance;
                if (inst == null) {
                    inst = new RxBus();
                    instance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 注册
     *
     * @param tag
     * @return
     */
    public <T> Observable<T> register(@NonNull Class<T> tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T, T> subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    /**
     * 解除注册
     *
     * @param tag
     */
    public <T> void unregister(@NonNull Class<T> tag, @NonNull Observable observable) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                subjectMapper.remove(tag);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param event
     */
    public <T> void post(@NonNull Object event) {
        List<Subject> subjectList = subjectMapper.get(event.getClass());
        if (subjectList != null && !subjectList.isEmpty()) {
            for (Subject subject : subjectList) {
                subject.onNext(event);
            }
        }
    }

    /**
     * 清除订阅
     */
    public void clear() {
        if (subjectMapper.isEmpty()) {
            return;
        }
        subjectMapper.clear();
    }

    private Observable<SelectWishEvent> observable;
    private void registerObservable() {
        observable = RxBus.getInstance().register(SelectWishEvent.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SelectWishEvent>() {
            @Override
            public void call(SelectWishEvent event) {
                Log.e(TAG, "event--2-->" +event.recordsBean.toString());
            }
        });
    }

    private void unRegisterObservable() {
        if (observable != null) {
            RxBus.getInstance().unregister(SelectWishEvent.class, observable);
        }
    }

}
