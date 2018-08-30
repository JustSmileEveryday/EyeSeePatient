package lht.wangtong.gowin120.patient.ui.mine.info;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.util.RxPhotoTool;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * 用户选择 头像
 * Created by luoyc on 2018/3/9.
 */

public class UserHeadPresenter extends BasePresenter<UserHeadContact.View> implements UserHeadContact.Presenter {
    public static final int CAMERA = 1004;
    public static final int Album = 1005;


    @Inject
    public UserHeadPresenter() {

    }

    @Override
    public void initData() {
        //sd卡的路径
    }

    @Override
    public void openCamera() {
        if (checkSelfPermission(mView.getMyContext(), Manifest.permission.CAMERA) == 0) {
            RxPhotoTool.openCameraImage(mView.getMyContext());
        } else {
            //请求权限，此方法会弹出权限请求对话框，让用户授权，并回调 onRequestPermissionsResult 来告知授权结果
            requestPermissions(mView.getMyContext(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
        }
    }

    @Override
    public void openAlbum() {
        if (checkSelfPermission(mView.getMyContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == 0) {
            RxPhotoTool.openLocalImage(mView.getMyContext());
        } else {
            requestPermissions(mView.getMyContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Album);
        }
//        Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
//        intent_album.setType("image/*");
//        mView.getMyContext().startActivityForResult(intent_album, ICON_FROM_ALBUM);
    }

    @Override
    public void savePhoto() {

    }


    @SuppressLint("CheckResult")
    @Override
    public void netUpdateAvatar(File mFile) {
        //展示
        //上传头像 先压缩
        new Compressor(mView.getMyContext())
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.PNG)
                .compressToFileAsFlowable(mFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        mView.setImage(file.getAbsolutePath());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void initUCrop(Uri uri) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(mView.getMyContext().getCacheDir(), imageName + ".png"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(mView.getMyContext(), R.color.colorPrimaryDark));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(mView.getMyContext(), R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        options.setCircleDimmedLayer(true);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        options.setShowCropGrid(false);
        //设置横线的数量
//        options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(mView.getMyContext());
    }


}
