package com.zhongke.content.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.zhongke.content.R;


/**
 * Created by Administrator on 2016/8/8.
 * 选择拍照，图库中获取相片
 */
public abstract class CreatePitureDialog extends Dialog implements View.OnClickListener {
    private View rootView;
    private Context context;
    public CreatePitureDialog(Context context) {
        super(context,R.style.DialogTheme);
          this. context=context;
            initView();
    }

    protected void initView() {
        rootView=View.inflate(context, R.layout.dialog_createpicture,null);
        rootView.findViewById(R.id.dialog_createpicture_cancle).setOnClickListener(this);
        rootView.findViewById(R.id.dialog_createpicture_careme).setOnClickListener(this);
        rootView.findViewById(R.id.dialog_createpicture_photoalbum).setOnClickListener(this);
        this.setContentView(rootView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.dialog_createpicture_cancle:
                this.dismiss();
                break;
            case  R.id.dialog_createpicture_careme:
                camera();
                break;
            case  R.id.dialog_createpicture_photoalbum:
                photoAlbum();
                break;
        }
    }
    public abstract  void camera();
    public abstract  void photoAlbum();
}
