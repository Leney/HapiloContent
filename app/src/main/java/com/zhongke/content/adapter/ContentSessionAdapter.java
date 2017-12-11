package com.zhongke.content.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lqr.emoji.LQREmotionKit;
import com.lqr.emoji.MoonUtils;
import com.zhongke.account.model.ZkLocalMessage;
import com.zhongke.content.Constance;
import com.zhongke.content.R;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.utils.BitmapUtils;
import com.zhongke.content.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.zhongke.content.Constance.type_other;


/**
 * Created by ${xingen} on 2017/8/7.
 */

public class ContentSessionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public static final String tag = ContentSessionAdapter.class.getSimpleName();
    private List<SessionContent_Entity> list;



    //文本或者表情的显示布局
    private final int text_other = R.layout.session_content_item_text_other;
    private final int text_own = R.layout.session_content_item_text_own;
    //图片的显示布局
    private final int emoj_other = R.layout.session_content_item_emoj_other;
    private final int emoj_own = R.layout.session_content_item_emoj_own;
    //语音的显示布局

    private final int voice_other = R.layout.session_content_item_voice_other;
    private final int voice_own = R.layout.session_content_item_voice_own;
    //视频的显示布局
    private final int video_other = R.layout.session_content_item_video_other;
    private final int video_own = R.layout.session_content_item_emoj_own;
    //sticker的显示布局
    private final int sticker_other = R.layout.session_content_item_sticker_other;
    private final int sticker_own = R.layout.session_content_item_sticker_own;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      //  Log.i(tag," onCreateViewHolder  " );
        switch (viewType) {
            case text_other:
                //不建议使用View.inflate():容易造成item布局不一致。
                return new ViewHolder1(LayoutInflater.from(context).inflate(viewType, parent, false));
            case text_own:
                //使宽度填充屏幕,不绑定
                ViewHolder2 viewHolder = new ViewHolder2(LayoutInflater.from(context).inflate(viewType, parent, false));
                viewHolder.setAgainClick(sessionCallBack);
                return viewHolder;
            case emoj_other:
                //不建议使用View.inflate():容易造成item布局不一致。
              ViewHolder3 viewHolder3=  new ViewHolder3(LayoutInflater.from(context).inflate(viewType, parent, false));
                viewHolder3.setLookClick(sessionCallBack);
                return viewHolder3;
            case emoj_own:
                //不建议使用View.inflate():容易造成item布局不一致。
                ViewHolder4 viewHolder4=new ViewHolder4(LayoutInflater.from(context).inflate(viewType, parent, false));
                viewHolder4.setLookClick(sessionCallBack);
                viewHolder4.setAgainClick(sessionCallBack);
                return viewHolder4;
            case voice_other:
                // 接收到语音的视图
                //不建议使用View.inflate():容易造成item布局不一致。
                ViewHolder5 viewHolder5 = new ViewHolder5(LayoutInflater.from(context).inflate(viewType, parent, false));
                viewHolder5.setPlayClick(sessionCallBack);
                return viewHolder5;
            case voice_own:
                // 自己发送语音的视图
                //不建议使用View.inflate():容易造成item布局不一致。
                ViewHolder6 viewHolder6=new ViewHolder6(LayoutInflater.from(context).inflate(viewType, parent, false));
                viewHolder6.setAgainClick(sessionCallBack);
                viewHolder6.setPlayClick(sessionCallBack);
                return viewHolder6;
            case sticker_other:
                //不建议使用View.inflate():容易造成item布局不一致。
                return   new ViewHolder7(LayoutInflater.from(context).inflate(viewType, parent, false));
            case sticker_own:
                //不建议使用View.inflate():容易造成item布局不一致。
                ViewHolder8 viewHolder8=new ViewHolder8(LayoutInflater.from(context).inflate(viewType, parent, false));
                viewHolder8.setAgainClick(sessionCallBack);
                return viewHolder8;
            case video_other:
                ViewHolder9 viewHolder9=new ViewHolder9(LayoutInflater.from(context).inflate(viewType,parent,false));
                viewHolder9.setPlayClick(sessionCallBack);
                return viewHolder9;

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        SessionContent_Entity entity = list.get(position);
        switch (entity.getChatType()) {
            case Constance.TEXT:
                return entity .getMark() == type_other ? text_other : text_own;
            case  Constance.IMAGE:
                return entity .getMark() == type_other ? emoj_other : emoj_own;
            case  Constance.VIDEO:
                return entity .getMark() == type_other ? video_other : video_own;
            case  Constance.VOICE:
                return entity .getMark() == type_other ? voice_other : voice_own;
            case  Constance.STICKER:
                return entity .getMark() == type_other ? sticker_other :sticker_own;
            default:
                return 0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SessionContent_Entity item = list.get(position);
    //    Log.i(tag, " adapter正在刷新  位置" + position + " 状态 " + item.getSendStatus() + " msgId " + item.getMsgId()+" 时间 "+ item.getSendTime());
        String icon_url=item.getMemberInfo()!=null?item.getMemberInfo().getIcon():null;
        if (holder instanceof ViewHolder1) {//接受到别人的信息
            ImageView imageView;
            TextView textView;
            imageView = ((ViewHolder1) holder).imageView;
            textView = ((ViewHolder1) holder).textView;
            textView.setText(item.getContent());
            MoonUtils.identifyFaceExpression(context,textView,item.getContent(),0);
            GlideLoader.loadNetWorkResource(context,icon_url, imageView, R.mipmap.session_list_item_default_icon, true);
        } else if (holder instanceof ViewHolder2) {//自己发送的信息
            ImageView imageView;
            TextView textView;
            int progressbar_visiblity, agin_iv_visiblity;
            switch (item.getSendStatus()) {
                case ZkLocalMessage.SEND_MSG_FAILED:
                    agin_iv_visiblity = View.VISIBLE;
                    progressbar_visiblity = View.GONE;
                    break;
                case ZkLocalMessage.SEND_MSG_SECCUSS:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.GONE;
                    break;
                default:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.VISIBLE;
                    break;
            }

            ((ViewHolder2) holder).again_iv.setVisibility(agin_iv_visiblity);
            ((ViewHolder2) holder).progressBar.setVisibility(progressbar_visiblity);
            imageView = ((ViewHolder2) holder).icon_iv;
            textView = ((ViewHolder2) holder).textView;
            MoonUtils.identifyFaceExpression(context,textView,item.getContent(),0);
            GlideLoader.loadNetWorkResource(context, icon_url, imageView, R.mipmap.session_list_item_default_icon, true);
            Log.i(tag," 自己发送的信息 进度框 "+progressbar_visiblity+"  重新发送 "+agin_iv_visiblity+" 消息状态 "+item.getContent()+" 控件的宽度 "+textView.getWidth());
        }else if (holder instanceof ViewHolder3){
           GlideLoader.loadNetWorkResource(context, Constance.DOWND_PHOTO +item.getContent(),((ViewHolder3) holder).imageView_emoj, R.mipmap.session_list_item_default_icon,false);
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder3) holder).imageView, R.mipmap.session_list_item_default_icon, true);
        }else if (holder instanceof ViewHolder4){
           // Log.i(tag," 显示自己发送图片  "+ item.getSendStatus() );
            int progressbar_visiblity, agin_iv_visiblity;
            switch (item.getSendStatus()) {
                case ZkLocalMessage.SEND_MSG_FAILED:
                    agin_iv_visiblity = View.VISIBLE;
                    progressbar_visiblity = View.GONE;
                    break;
                case ZkLocalMessage.SEND_MSG_SECCUSS:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.GONE;
                    break;
                default:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.VISIBLE;
                    break;
            }
            ((ViewHolder4) holder).again_iv.setVisibility(agin_iv_visiblity);
            ((ViewHolder4) holder).progressBar.setVisibility(progressbar_visiblity);
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder4) holder).icon_iv, R.mipmap.session_list_item_default_icon, true);
            GlideLoader.loadNetWorkResource(context, item.getLocalUrl(),((ViewHolder4) holder).image_emoj, R.mipmap.session_list_item_default_icon, false);
        }else if (holder instanceof ViewHolder5){
            // 接收到语音的视图
            int increment = ((int)(UIUtils.getDisplayWidth() * 0.6f)) / 2 / Constance.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND * item.getDuraction();
            RelativeLayout chatLay = ((ViewHolder5)holder).voiceLay;
            ViewGroup.LayoutParams params = chatLay.getLayoutParams();
//            params.width = UIUtils.dip2Px(65) + UIUtils.dip2Px(increment);
            params.width = UIUtils.dip2Px(43) + increment;
            // 设置语音控件宽度
            chatLay.setLayoutParams(params);
            chatLay.setTag(((ViewHolder5) holder).voiceImg);
            // 设置时间
            ((ViewHolder5)holder).duration.setText(item.getDuraction() +"''");
            // 设置显示用户头像
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder5) holder).userIcon, R.mipmap.session_list_item_default_icon, true);
        }else if(holder instanceof ViewHolder6){
            // 自己发送语音的视图
//            int increment = UIUtils.getDisplayWidth() / 2 / Constance.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND * item.getDuraction();
            int increment = ((int)(UIUtils.getDisplayWidth() * 0.6f)) / 2 / Constance.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND * item.getDuraction();
            RelativeLayout chatLay = ((ViewHolder6)holder).voiceLay;
            ViewGroup.LayoutParams params = chatLay.getLayoutParams();
//            int d2p = UIUtils.dip2Px(increment);
//            LogUtil.i("llj","d2p-------->>>"+d2p);
            params.width = UIUtils.dip2Px(43) + increment;
            // 设置语音控件宽度
            chatLay.setLayoutParams(params);
            chatLay.setTag(((ViewHolder6) holder).voiceImg);
            // 设置时间
            ((ViewHolder6)holder).duration.setText(item.getDuraction() +"''");
            // 设置显示用户头像
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder6) holder).userIcon, R.mipmap.session_list_item_default_icon, true);

            // 设置消息状态
            int progressbar_visiblity, agin_iv_visiblity;
            switch (item.getSendStatus()) {
                case ZkLocalMessage.SEND_MSG_FAILED:
                    agin_iv_visiblity = View.VISIBLE;
                    progressbar_visiblity = View.GONE;
                    break;
                case ZkLocalMessage.SEND_MSG_SECCUSS:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.GONE;
                    break;
                default:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.VISIBLE;
                    break;
            }
            ((ViewHolder6) holder).again_iv.setVisibility(agin_iv_visiblity);
            ((ViewHolder6) holder).progressBar.setVisibility(progressbar_visiblity);
        } else  if (holder instanceof ViewHolder7){
            String url = item.getContent();
            String suffix = url.substring(url.lastIndexOf(".") + 1);
            url= LQREmotionKit.getStickerPath()+url;
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder7) holder).imageView, R.mipmap.session_list_item_default_icon, true);
            if (suffix.equals("png")) {
                    GlideLoader.loadNetWorkResource(context,url,((ViewHolder7) holder).imageView_emoj,false);
            } else if (suffix.equals("gif")) {
                GlideLoader.loadGifResource(context,url,((ViewHolder7) holder).imageView_emoj);
            }
        }else if (holder instanceof ViewHolder8){//自己发的贴图
            String url = item.getContent();
            String suffix = url.substring(url.lastIndexOf(".") + 1);
            url= LQREmotionKit.getStickerPath()+url;
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder8) holder).icon_iv, R.mipmap.session_list_item_default_icon, true);
            if (suffix.equals("png")) {
                GlideLoader.loadNetWorkResource(context,url,((ViewHolder8) holder).image_emoj,false);
            } else if (suffix.equals("gif")) {
                GlideLoader.loadGifResource(context,url,((ViewHolder8) holder).image_emoj);
            }
            int progressbar_visiblity, agin_iv_visiblity;
            switch (item.getSendStatus()) {
                case ZkLocalMessage.SEND_MSG_FAILED:
                    agin_iv_visiblity = View.VISIBLE;
                    progressbar_visiblity = View.GONE;
                    break;
                case ZkLocalMessage.SEND_MSG_SECCUSS:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.GONE;
                    break;
                default:
                    agin_iv_visiblity = View.GONE;
                    progressbar_visiblity = View.VISIBLE;
                    break;
            }
            ((ViewHolder8) holder).again_iv.setVisibility(agin_iv_visiblity);
            ((ViewHolder8) holder).progressBar.setVisibility(progressbar_visiblity);
        }else if (holder instanceof  ViewHolder9){
            String url = item.getContent();
            GlideLoader.loadNetWorkResource(context, icon_url,((ViewHolder9) holder).imageView, R.mipmap.session_list_item_default_icon, true);
            Bitmap bitmap= BitmapUtils.createVideoThumbnail(Constance.DOWND_PHOTO +url, MediaStore.Video.Thumbnails.MINI_KIND);
            ((ViewHolder9) holder).imageView_emoj.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public List<SessionContent_Entity> getList() {
        return list;
    }

    /**
     * 当列表中某个数据发生改变的时候，调用
     */
    public void notifyData() {
       // Log.i(tag, " 本次操作是 在Adapter中更新聊天内容 notifyData() ");
        this.notifyDataSetChanged();
    }

    /**
     * 切换数据源列表的时候，调用
     */
    public void setData(List<SessionContent_Entity> list) {
     //   Log.i(tag, " 本次操作是 在Adapter中切换新 聊天内容 setData()  新数据的个数 "+list.size());
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }


    /**
     * 排序
     */
    public void reverseOder() {
        //正序排列，比较
        Collections.sort(list, data_comparator);
        //倒叙排列
        // Collections.reverse(list);
    }

    /**
     * 向列表添加一个数据，调用新增自己发送或者接受到的
     *
     * @param entity
     */
    public void addNewDateData(SessionContent_Entity entity) {
        Log.i(tag," 本次操作是 在Adapter中 添加了一个item  消息id " +entity.getMsgId()+" 消息类型 "+entity.getChatType()+" 属于谁发的 "+ entity.getMark());
        this.list.add(entity);
       this.notifyItemInserted(this.list.size()-1);
    }

    /**
     * 向列表添加一个数据源,旧数据
     *
     * @param entity_list
     */
    public void addOldDateData(List<SessionContent_Entity> entity_list) {
        int changeSize=entity_list.size();
        for (int i=0;i<changeSize;++i){
            this.list.add(i,entity_list.get((changeSize-1)-i));
            this.notifyItemInserted(i);
        }

       // this.list.addAll(0,entity_list);
       // this.notifyItemRangeChanged(0,changeSize);
    }
    public ContentSessionAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder1(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.session_other_iv);
            this.textView = (TextView) itemView.findViewById(R.id.session_other_content);
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView icon_iv;
        private TextView textView;
        private ProgressBar progressBar;
        private ImageView again_iv;
        public ViewHolder2(View itemView) {
            super(itemView);
            this.icon_iv = (ImageView) itemView.findViewById(R.id.session_own_iv);
            this.textView = (TextView) itemView.findViewById(R.id.session_own_content);
            this.progressBar = itemView.findViewById(R.id.session_own_progressbar);
            this.again_iv = itemView.findViewById(R.id.session_own_again_iv);
        }
        public void setAgainClick(ContentSessionCallBack sessionCallBack) {
            this.again_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        again_iv.setVisibility(View.GONE);
                        sessionCallBack.click(view,true, getLayoutPosition());
                    }
                }
            });
        }
    }
    public static class ViewHolder3 extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView imageView_emoj;
        public ViewHolder3(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.session_emoj_other_icon_iv);
            this.imageView_emoj= (ImageView) itemView.findViewById(R.id.session_emoj_other_emoj_iv);
        }
        public void setLookClick(ContentSessionCallBack sessionCallBack) {
            this.imageView_emoj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        sessionCallBack.click(view,false, getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 自己发送图片
     */
    public static class ViewHolder4 extends RecyclerView.ViewHolder {
        private ImageView icon_iv;
        private ImageView image_emoj;
        private ProgressBar progressBar;
        private ImageView again_iv;

        public ViewHolder4(View itemView) {
            super(itemView);
            this.icon_iv = (ImageView) itemView.findViewById(R.id.session_image_own_iv);
            this.image_emoj = (ImageView) itemView.findViewById(R.id.session_image_own_content);
            this.progressBar = itemView.findViewById(R.id.session_own_progressbar);
            this.again_iv = itemView.findViewById(R.id.session_own_again_iv);
        }

        public void setAgainClick(ContentSessionCallBack sessionCallBack) {
            this.again_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        again_iv.setVisibility(View.GONE);
                        sessionCallBack.click(view,true, getLayoutPosition());
                    }
                }
            });
        }
        public void setLookClick(ContentSessionCallBack sessionCallBack) {
            this.image_emoj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        sessionCallBack.click(view,false, getLayoutPosition());
                    }
                }
            });
        }
    }


    /**
     * 接收到语音的视图HolderView
     */
    public static class ViewHolder5 extends RecyclerView.ViewHolder {
        private ImageView userIcon;
       public ImageView voiceImg;
        /** 时间*/
        private TextView duration;
        /** 语音部分*/
        private RelativeLayout voiceLay;

        public ViewHolder5(View itemView) {
            super(itemView);
            this.userIcon = (ImageView) itemView.findViewById(R.id.session_voice_other_user_icon);
            this.voiceImg = (ImageView) itemView.findViewById(R.id.session_voice_other_img);
            this.duration = itemView.findViewById(R.id.session_voice_other_duration);
            this.voiceLay = itemView.findViewById(R.id.sesstion_voice_other_voice_lay);
        }

        /**
         * 语音点击播放事件
         * @param sessionCallBack
         */
        public void setPlayClick(ContentSessionCallBack sessionCallBack){
            this.voiceLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        sessionCallBack.click(view,false,getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 发送语音的视图HolderView
     */
    public static class ViewHolder6 extends RecyclerView.ViewHolder {
        public  ImageView userIcon;
        public  ImageView voiceImg;
        /** 时间*/
        private TextView duration;
        /** 发送中进度条*/
        private ProgressBar progressBar;
        /** 发送失败图标*/
        private ImageView again_iv;
        /** 语音部分*/
        private RelativeLayout voiceLay;

        public ViewHolder6(View itemView) {
            super(itemView);
            this.userIcon = (ImageView) itemView.findViewById(R.id.session_voice_own_user_icon);
            this.voiceImg = (ImageView) itemView.findViewById(R.id.session_voice_own_voice_img);
            this.duration = itemView.findViewById(R.id.session_voice_own_duration);
            this.progressBar = itemView.findViewById(R.id.session_voice_own_progressbar);
            this.again_iv = itemView.findViewById(R.id.session_voice_own_again_iv);
            this.voiceLay = itemView.findViewById(R.id.session_voice_own_chat_lay);
        }

        public void setAgainClick(ContentSessionCallBack sessionCallBack) {
            this.again_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        again_iv.setVisibility(View.GONE);
                        sessionCallBack.click(view,true,getLayoutPosition());
                    }
                }
            });
        }

        /**
         * 语音点击播放事件
         * @param sessionCallBack
         */
        public void setPlayClick(ContentSessionCallBack sessionCallBack){
            this.voiceLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        sessionCallBack.click(view,false,getLayoutPosition());
                    }
                }
            });
        }
    }
    public static class ViewHolder7 extends RecyclerView.ViewHolder {
        public  ImageView imageView;
        public  ImageView imageView_emoj;
        public ViewHolder7(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.session_emoj_other_icon_iv);
            this.imageView_emoj= (ImageView) itemView.findViewById(R.id.session_emoj_other_emoj_iv);
        }
    }

    /**
     * 自己发送图片
     */
    public static class ViewHolder8 extends RecyclerView.ViewHolder {
        private ImageView icon_iv;
        private ImageView image_emoj;
        private ProgressBar progressBar;
        private ImageView again_iv;
        public ViewHolder8(View itemView) {
            super(itemView);
            this.icon_iv = (ImageView) itemView.findViewById(R.id.session_image_own_iv);
            this.image_emoj = (ImageView) itemView.findViewById(R.id.session_image_own_content);
            this.progressBar = itemView.findViewById(R.id.session_own_progressbar);
            this.again_iv = itemView.findViewById(R.id.session_own_again_iv);
        }
        public void setAgainClick(ContentSessionCallBack sessionCallBack) {
            this.again_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        again_iv.setVisibility(View.GONE);
                        sessionCallBack.click(view,true, getLayoutPosition());
                    }
                }
            });
        }
    }

    public static class ViewHolder9 extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView imageView_emoj;
        private RelativeLayout relativeLayout;
        public ViewHolder9(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.session_emoj_other_icon_iv);
            this.imageView_emoj= (ImageView) itemView.findViewById(R.id.session_emoj_other_emoj_iv);
            this.relativeLayout=itemView.findViewById(R.id.session_video_other_show_layout);
        }
        /**
         * 点击播放事件
         * @param sessionCallBack
         */
        public void setPlayClick(ContentSessionCallBack sessionCallBack){
            this.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionCallBack != null) {
                        sessionCallBack.click(view,false,getLayoutPosition());
                    }
                }
            });
        }

    }
    private ContentSessionCallBack sessionCallBack;

//    /** 播放的回调对象(语音、视频、图片等)*/
//    private ContentSessionCallBack sesstionPlayCallBack;

    public void setSessionCallBack(ContentSessionCallBack sessionCallBack) {
        this.sessionCallBack = sessionCallBack;
    }

//    public void setSesstionPlayCallBack(ContentSessionCallBack sesstionPlayCallBack){
//        this.sesstionPlayCallBack = sesstionPlayCallBack;
//    }

    public interface ContentSessionCallBack {
        /**
         * 点击回调
         * @param view
         * @param isRetry 是否是重试(重新发送消息) true = 重新发送，false=点击消息本身播放(图片、视频、语音等)
         * @param position
         */
        void click(View view, boolean isRetry, int position);
    }

    /**
     * 定义一个比较器,时间排序
     */
    private static final Comparator<SessionContent_Entity> data_comparator = new Comparator<SessionContent_Entity>() {
        //假如A的值大于B，你返回1。这样调用Collections.sort()方法就是升序
        // 假如A的值大于B，你返回-1。这样调用Collections.sort()方法就是降序
        @Override
        public int compare(SessionContent_Entity entity, SessionContent_Entity t1) {
            Date date1 = new Date(entity.getSendTime());
            Date date2 = new Date(t1.getSendTime());
          //  Log.i(tag," Comparator 日期排序 "+" 转换前的时间 "+entity.getSendTime()+" "+t1.getSendTime()+" 转化后的date "+date1+"  "+date2);
            return date1.compareTo(date2);
        }
    };
}
