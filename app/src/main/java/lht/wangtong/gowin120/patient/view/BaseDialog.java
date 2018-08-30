package lht.wangtong.gowin120.patient.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;


/**
 *
 * @author sanmu
 * @date 2016/5/30 0030
 * 封装的dialog
 */
public class BaseDialog extends Dialog {
    public ICallbackTab callback;

    public BaseDialog(Context context) {
        super(context);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public BaseDialog(Context context, View view) {
        super(context);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public BaseDialog(Context context, View view, ICallbackTab callback) {
        this(context, view);
        this.callback = callback;
    }

    public BaseDialog(Context context, ICallbackTab callback) {
        super(context);
        this.callback = callback;
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    /**
     * @param gravity 上中下  三个展现位置
     */
    public void setGravity(int gravity) {
        this.getWindow().setGravity(gravity);
    }

    //按照百分比来  0为不设置
    public void setSize(int w, int h) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = w; //设置宽度
        this.getWindow().setAttributes(lp);
    }
}
