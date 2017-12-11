package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tanlei} on 2017/9/25.
 * 活动实体类
 */

public class ExerciseBean {
    private int id;
    private String location;
    private String photoUrl;
    private String time;
    private String text;
    private String startTime;
    private List<String> photos;

    public ExerciseBean() {
    }

    public ExerciseBean(int id, String location, String photoUrl, String time, String text, String startTime, List<String> photos) {
        this.id = id;
        this.location = location;
        this.photoUrl = photoUrl;
        this.time = time;
        this.text = text;
        this.startTime = startTime;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public static List<ExerciseBean> getData() {
        List<ExerciseBean> list = new ArrayList<>();
        List<String> listPhoto = new ArrayList<>();
        listPhoto.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344900982&di=2b9a657c09b256101d82e292c7a732c8&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1208%2F15%2Fc2%2F12920755_12920755_1313370994656.jpg");
        listPhoto.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344901301&di=d44f856db7535b0b675835f020f77cd8&imgtype=0&src=http%3A%2F%2Fs1.sinaimg.cn%2Fbmiddle%2F459b565agx6ChHO0wcE90%26690");
        listPhoto.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344901301&di=2e2202c93b33eb011ce802db6375a972&imgtype=0&src=http%3A%2F%2Fs14.sinaimg.cn%2Fmw690%2F002Updj4gy6IYhAuMX33d%26690");
        listPhoto.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344901299&di=23e6d2d230bda09cc0768c118c03da2d&imgtype=0&src=http%3A%2F%2Fmvimg10.meitudata.com%2F57a8047c92ecf5831.jpg");
        listPhoto.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344901297&di=3997059e4c118cf815c65eca83712db4&imgtype=0&src=http%3A%2F%2Fi0.bbs.fd.zol-img.com.cn%2Ft_s1200x5000%2Fg4%2FM08%2F0A%2F02%2FCg-4WlKW3BWITxbVACWNPyPffnYAANnXgErUL8AJY1X716.jpg");
        list.add(new ExerciseBean(1, "Hapilo活动覆盖区域", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344705118&di=4936dd5c6e8fc2f95bc780de34b2ae67&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F9e%2F6b%2Fd7%2Fc2%2Fb6%2Ff3%2Fac%2F30%2F65%2F5a%2F44%2F63%2F2c%2F34%2F14%2F1b.jpg", "2017年10月1日 19：00", "撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划", "距离开始还有3小时", listPhoto));
        list.add(new ExerciseBean(2, "莲花山野营", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344705118&di=4936dd5c6e8fc2f95bc780de34b2ae67&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F9e%2F6b%2Fd7%2Fc2%2Fb6%2Ff3%2Fac%2F30%2F65%2F5a%2F44%2F63%2F2c%2F34%2F14%2F1b.jpg", "2017年10月1日 19：00", "撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划", "距离开始还有3小时", listPhoto));
        list.add(new ExerciseBean(3, "凤凰山拜佛", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344705118&di=4936dd5c6e8fc2f95bc780de34b2ae67&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F9e%2F6b%2Fd7%2Fc2%2Fb6%2Ff3%2Fac%2F30%2F65%2F5a%2F44%2F63%2F2c%2F34%2F14%2F1b.jpg", "2017年10月1日 19：00", "撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划", "距离开始还有3小时", listPhoto));
        list.add(new ExerciseBean(4, "大梅沙游玩", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344705118&di=4936dd5c6e8fc2f95bc780de34b2ae67&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F9e%2F6b%2Fd7%2Fc2%2Fb6%2Ff3%2Fac%2F30%2F65%2F5a%2F44%2F63%2F2c%2F34%2F14%2F1b.jpg", "2017年10月1日 19：00", "撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划", "距离开始还有3小时", listPhoto));
        list.add(new ExerciseBean(5, "海洋馆观鱼", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506344705118&di=4936dd5c6e8fc2f95bc780de34b2ae67&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F9e%2F6b%2Fd7%2Fc2%2Fb6%2Ff3%2Fac%2F30%2F65%2F5a%2F44%2F63%2F2c%2F34%2F14%2F1b.jpg", "2017年10月1日 19：00", "撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划撒旦哈斯计划", "距离开始还有3小时", listPhoto));
        return list;
    }
}
