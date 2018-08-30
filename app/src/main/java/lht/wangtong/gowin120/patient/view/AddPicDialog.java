package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;


/**
 * 选择图片dialog
 * Created by luoyc on 2016/7/6 0006.
 */
public class AddPicDialog extends BaseDialog implements View.OnClickListener {
    //相机
    public static final int TAKEPHOTO = 1;
    //相册
    public static final int TAKEALBUM = 2;
    //保存图片
    public static final int SAVEPHOTO = 3;

    View view;
    TextView takePhoto, takeAlbum, save_photo, cancel;

    public AddPicDialog(Context context, ICallbackTab callback) {
        super(context, callback);
        view = LayoutInflater.from(context).inflate(R.layout.view_addpic, null);
        takePhoto = (TextView) view.findViewById(R.id.takephoto);
        takePhoto.setOnClickListener(this);
        takeAlbum = (TextView) view.findViewById(R.id.takealbum);
        takeAlbum.setOnClickListener(this);
        cancel = (TextView) view.findViewById(R.id.pic_cancel);
        cancel.setOnClickListener(this);
        save_photo = (TextView) view.findViewById(R.id.save_photo);
        save_photo.setOnClickListener(this);
        this.setContentView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takephoto:
                callback.callBackTab(TAKEPHOTO);
                break;
            case R.id.takealbum:
                callback.callBackTab(TAKEALBUM);
                break;
            case R.id.save_photo:
                callback.callBackTab(SAVEPHOTO);
                break;
            case R.id.pic_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        setGravity(Gravity.BOTTOM);
        setSize(ScreenUtils.getScreenWidth(), 0);
    }
}
