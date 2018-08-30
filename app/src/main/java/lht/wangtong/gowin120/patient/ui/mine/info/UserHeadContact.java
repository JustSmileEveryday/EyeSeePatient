package lht.wangtong.gowin120.patient.ui.mine.info;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * Created by luoyc on 2018/3/9.
 */

public interface UserHeadContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initChoosePic();

        Activity getMyContext();

        void setImage(String imageFile);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void openCamera();

        void openAlbum();

        void savePhoto();

        void netUpdateAvatar(File file);

        void initUCrop(Uri uri);
    }

}
