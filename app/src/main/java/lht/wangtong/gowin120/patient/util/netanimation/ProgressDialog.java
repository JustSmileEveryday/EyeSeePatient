package lht.wangtong.gowin120.patient.util.netanimation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import lht.wangtong.gowin120.patient.R;


/**
 * @author luoyc
 */
public class ProgressDialog {

    private Dialog mDialog;

    public ProgressDialog(Context context) {
        mDialog = new Dialog(context, R.style.dialog_style);
        //设置是否允许Dialog可以被点击取消,也会阻止Back键
        mDialog.setCancelable(false);
        //获取Dialog窗体的根容器
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup root = (ViewGroup) mDialog.getWindow().getDecorView().findViewById(android.R.id.content);
        //设置窗口大小为屏幕大小
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);
        root.setLayoutParams(new LinearLayout.LayoutParams(screenSize.x, screenSize.y));
        //获取自定义布局,并设置给Dialog
        View view = inflater.inflate(R.layout.progress_dialog_layout, root, false);
        mDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void show() {
        try {
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShow() {
        return mDialog.isShowing();
    }
}
