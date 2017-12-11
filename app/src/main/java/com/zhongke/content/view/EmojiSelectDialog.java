package com.zhongke.content.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.zhongke.content.R;

/**
 * Created by ${xingen} on 2017/8/10.
 *
 *  表情的Dialog
 */

public class EmojiSelectDialog  extends BaseDialog implements IEmotionExtClickListener,IEmotionSelectedListener{
    private EmotionLayout emotionLayout;
    private EmotionKeyboard mEmotionKeyboard;
    public EmojiSelectDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, R.style.DialogTheme_no_black,onClickListener);
        this.setCanceledOnTouchOutside(true);
    }

    /**
     * 设置位置，通过x,y的
     * @param x
     * @param y
     */
    public void setLocation(int x,int y){
        WindowManager.LayoutParams layoutParams= this.getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM|Gravity.LEFT;
        layoutParams.x=x;
        layoutParams.y=y;
    }
    @Override
    protected View getRootView() {
        return View.inflate(context, R.layout.dialog_layout_emoji,null);
    }
    @Override
    protected void initView(View rootView) {
        this.emotionLayout=(EmotionLayout) rootView.findViewById(R.id.layout_elEmotion);

        //initEmotionKeyboard();
    }

    @Override
    public void onEmotionAddClick(View view) {
        Toast.makeText(context.getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmotionSettingClick(View view) {
        Toast.makeText(context.getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
    }
    public void attachEditText(EditText editText){
        this.emotionLayout.attachEditText(editText);
    }

    @Override
    public void onEmojiSelected(String key) {

    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
         this.dismiss();
    }

/*    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with((Activity)context);
        mEmotionKeyboard.bindToContent(mLlContent);
        mEmotionKeyboard.bindToEmotionButton(mIvEmo);
        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.setEmotionLayout(mElEmotion);
    }*/
}
