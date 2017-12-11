package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgg1 on 2017/10/27.
 */

public class ActivityRewardBean {
    /**
     * 奖励类型，0代表虚拟物品，1代表实物
     */
    private int rewardType;
    /**
     * 奖励图片路径
     */
    private String photo;
    /**
     * 奖励物品名称
     */
    private String rewardName;
    /**
     * 奖励数量
     */
    private int number;

    public ActivityRewardBean() {
    }

    public ActivityRewardBean(int rewardType, String photo, String rewardName, int number) {
        this.rewardType = rewardType;
        this.photo = photo;
        this.rewardName = rewardName;
        this.number = number;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static List<ActivityRewardBean> getData() {
        List<ActivityRewardBean> list = new ArrayList<>();
        list.add(new ActivityRewardBean(0,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509107453789&di=2ec9075d52ad081ee66be84873bc7c6d&imgtype=0&src=http%3A%2F%2F58pic.ooopic.com%2F58pic%2F19%2F15%2F75%2F10458PICvSX.jpg","金币",100));
        list.add(new ActivityRewardBean(0,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509107531414&di=1d5108c9777d6950b750fe6b77893910&imgtype=0&src=http%3A%2F%2Fpic.35pic.com%2Fnormal%2F09%2F04%2F98%2F4499633_195643829000_2.jpg","徽章",1));
        list.add(new ActivityRewardBean(1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509107588799&di=b4ab2b1c1634698ad37070a5a8e97206&imgtype=0&src=http%3A%2F%2Fimage.kumanju.com%2Fother%2F1b3ab45e-6bcd-4331-8236-cb3de367ba86.jpg","公仔熊",2));
        list.add(new ActivityRewardBean(0,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509107649403&di=c6f8b01fdb489bd0460df65498350eb0&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F07%2F08%2F14T58PIC546.jpg","徽章",1));
        list.add(new ActivityRewardBean(1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509702426&di=d1806e4a7351760af5d3b2c6711c375e&imgtype=jpg&er=1&src=http%3A%2F%2Fpic3.16pic.com%2F00%2F07%2F64%2F16pic_764981_b.jpg","气球",15));
        list.add(new ActivityRewardBean(1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509702482&di=a656107ed3956916cf21e88c2905bf45&imgtype=jpg&er=1&src=http%3A%2F%2Fupload.newhua.com%2F2012%2F0508%2F1336459848885.jpg","游戏机",1));
        list.add(new ActivityRewardBean(1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509107821171&di=b76858c5f6b304adde722a39a84a4cf2&imgtype=0&src=http%3A%2F%2Fpic34.nipic.com%2F20131023%2F3101644_073933723000_2.png","马里奥",1));
        return list;
    }
}
