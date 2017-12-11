package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动趣味Bean
 * Created by llj on 2017/7/5.
 */

public class FunBean {
    /** 文本内容*/
    private String content;

    /** */
    private List<String> imgUrls = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
