package lht.wangtong.gowin120.patient.ui.mine.info;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.util.RxPhotoTool;
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;
import lht.wangtong.gowin120.patient.view.AddPicDialog;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static lht.wangtong.gowin120.patient.view.AddPicDialog.SAVEPHOTO;
import static lht.wangtong.gowin120.patient.view.AddPicDialog.TAKEALBUM;
import static lht.wangtong.gowin120.patient.view.AddPicDialog.TAKEPHOTO;

/**
 * 选择头像
 * Created by luoyc on 2018/3/9.
 */
@Route(path = "/mine/info/UserHeadActivity")
public class UserHeadActivity extends BaseActivity<UserHeadPresenter> implements UserHeadContact.View, View.OnClickListener {
    @BindView(R.id.head_img)
    ImageView head_img;
    @Autowired
    String userPic;
    private AddPicDialog dialogPic; //选择头像
    private String mHead = "";
    private Uri resultUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_head;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        //展示用户当前的头像地址
        if (userPic != null && !TextUtils.isEmpty(userPic)) {
            //展示
            Glide.with(this)
                    .load(Api.HOST_IMG + userPic)
                    .apply(new RequestOptions().error(R.drawable.big_default_header_img))
                    .transition(withCrossFade())
                    .into(head_img);
        }
        mPresenter.initData();
    }


    @Override
    public void onBackPressed() {
        if (TextUtils.isEmpty(mHead)) {
            //当前替换过图片
            Intent data = new Intent();
            data.putExtra("headImg", mHead);
            setResult(RESULT_OK, data);
        }
        finish();
    }

    @Override
    public void initChoosePic() {
        if (dialogPic != null) {
            dialogPic.show();
            return;
        }
        dialogPic = new AddPicDialog(this, new ICallbackTab() {
            @Override
            public void callBackTab(int i) {
                switch (i) {
                    case TAKEPHOTO:
                        mPresenter.openCamera();
                        break;
                    case TAKEALBUM:
                        mPresenter.openAlbum();
                        break;
                    case SAVEPHOTO:
                        mPresenter.savePhoto();
                        break;
                    default:
                        break;
                }
            }
        });
        dialogPic.show();
    }

    @Override
    public Activity getMyContext() {
        return this;
    }

    @Override
    public void setImage(String imageFile) {
        mHead = imageFile;
    }


    @OnClick({R.id.back_btn, R.id.more_menu})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                if (mHead != null && !TextUtils.isEmpty(mHead)) {
                    //当前替换过图片
                    Intent data = new Intent();
                    data.putExtra("headImg", mHead);
                    setResult(RESULT_OK, data);
                }
                finish();
                break;
            case R.id.more_menu:
                initChoosePic();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RxPhotoTool.GET_IMAGE_FROM_PHONE:
                    //选择相册之后的处理
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    mPresenter.initUCrop(data.getData());
                    break;
                case RxPhotoTool.GET_IMAGE_BY_CAMERA:
                    //选择照相机之后的处理
                   /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    mPresenter.initUCrop(RxPhotoTool.imageUriFromCamera);
                    break;
                case UCrop.REQUEST_CROP:
                    dialogPic.dismiss();
                    //UCrop裁剪之后的处理
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, head_img);
                    break;
                case UCrop.RESULT_ERROR:
                    dialogPic.dismiss();
                    //UCrop裁剪错误之后的处理
                    final Throwable cropError = UCrop.getError(data);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 从Uri中加载图片 并将其转化成File文件返回
     */
    private void roadImageView(Uri uri, ImageView imageView) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.big_default_header_img)
                .priority(Priority.NORMAL)
                .error(R.drawable.big_default_header_img)
                .fallback(R.drawable.big_default_header_img))
                .thumbnail(0.5f)
                .into(imageView);
        mPresenter.netUpdateAvatar(new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case UserHeadPresenter.CAMERA:
                if (grantResults.length > 0) {
                    //grantResults 数组中存放的是授权结果
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //同意授权
                        //授权后做一些你想做的事情，即原来不需要动态授权时做的操作
                        mPresenter.openCamera();
                    } else {//用户拒绝授权
                        //可以简单提示用户
                        ToastUtils.showShort("没有授权继续操作");
                    }
                }
                break;
            case UserHeadPresenter.Album:
                if (grantResults.length > 0) {
                    //grantResults 数组中存放的是授权结果
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //同意授权
                        //授权后做一些你想做的事情，即原来不需要动态授权时做的操作
                        mPresenter.openAlbum();
                    } else {//用户拒绝授权
                        //可以简单提示用户
                        ToastUtils.showShort("没有授权继续操作");
                    }
                }
                break;
            default:
                break;
        }
    }
}
