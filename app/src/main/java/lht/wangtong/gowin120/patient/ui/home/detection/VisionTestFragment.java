package lht.wangtong.gowin120.patient.ui.home.detection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.VisionInfo;

/**
 * 视力测试
 *
 * @author luoyc
 * @date 2018/3/19
 */

public class VisionTestFragment extends BaseFragment<VisionTestPresenter> implements VisionTestContact.View {
    @BindView(R.id.vision_img)
    ImageView mVisionImg;
    @BindView(R.id.start_btn)
    TextView mStartBtn;
    @BindView(R.id.vision_type)
    TextView visionType;
    @BindView(R.id.vision_type_2)
    TextView visionType2;
    @BindView(R.id.start_test_layout)
    LinearLayout mStartView;
    @BindView(R.id.start_test_layout_1)
    LinearLayout mVisionView;
    @BindView(R.id.start_test_layout_2)
    LinearLayout mChooseView;
    private List<VisionInfo> visionInfos;
    /**
     * 从4.0开始
     **/
    private int rightVisionAngle = 0;
    /**
     * 从4.0开始
     **/
    private int leftVisionAngle = 0;
    /**
     * 右眼错误次数
     **/
    private int rightErrorTimes = 0;
    /**
     * 左眼错误次数
     **/
    private int leftErrorTimes = 0;
    /**
     * 右眼视力
     **/
    private String rightVision = "";
    /**
     * 左眼视力
     **/
    private String leftVision = "";
    /**
     * 测试眼睛 1右 2左 默认上来第一次为右眼
     **/
    private int mType = 1;
    private String mfamilyId = "";

    public void setMfamilyId(String mfamilyId) {
        this.mfamilyId = mfamilyId;
    }

    public static VisionTestFragment newInstance() {
        return new VisionTestFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vision_test;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        visionInfos = new ArrayList<>();
        mPresenter.initData();
    }

    @OnClick({R.id.start_btn, R.id.vision_img_layout, R.id.up_img, R.id.left_img, R.id.right_img, R.id.down_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                mStartView.setVisibility(View.GONE);
                mVisionView.setVisibility(View.VISIBLE);
                if (mType == 1) {
                    narrowVision(visionInfos.get(rightVisionAngle));
                } else {
                    narrowVision(visionInfos.get(leftVisionAngle));
                }
                break;
            case R.id.vision_img_layout:
                mVisionView.setVisibility(View.GONE);
                mChooseView.setVisibility(View.VISIBLE);
                break;
            case R.id.up_img:
                checkVision(0, mType);
                break;
            case R.id.left_img:
                checkVision(2, mType);
                break;
            case R.id.right_img:
                checkVision(3, mType);
                break;
            case R.id.down_img:
                checkVision(1, mType);
                break;
            default:
                break;
        }
    }

    @Override
    public void narrowVision(VisionInfo visionInfo) {
        mVisionView.setVisibility(View.VISIBLE);
        mVisionImg.setBackgroundResource(visionInfo.getDrawableId());
        Bitmap mBitmap = ImageUtils.drawable2Bitmap(mVisionImg.getBackground());
        Bitmap newBitmap = ImageUtils.scale(mBitmap, visionInfo.getScale(), visionInfo.getScale());
        mVisionImg.setBackground(ImageUtils.bitmap2Drawable(newBitmap));
    }

    @Override
    public void setVisionData(List<VisionInfo> visionData) {
        visionInfos.addAll(visionData);
    }

    @Override
    public void narrowVision2(VisionInfo visionInfo) {
        mVisionView.setVisibility(View.VISIBLE);
        mVisionImg.setBackgroundResource(visionInfo.getDrawableId2());
        Bitmap mBitmap = ImageUtils.drawable2Bitmap(mVisionImg.getBackground());
        Bitmap newBitmap = ImageUtils.scale(mBitmap, visionInfo.getScale(), visionInfo.getScale());
        mVisionImg.setBackground(ImageUtils.bitmap2Drawable(newBitmap));
    }

    /**
     * @param direction
     * @param type      1右 2左
     */
    @Override
    public void checkVision(int direction, int type) {
        mChooseView.setVisibility(View.GONE);
        switch (type) {
            case 1:
                //右
                if (rightErrorTimes == 0) {
                    //第一次
                    if (direction == visionInfos.get(rightVisionAngle).getDirection()) {
                        //正确 缩小尺寸
                        rightVisionAngle++;
                        if (rightVisionAngle == visionInfos.size()) {
                            // 5.0
                            rightVision = visionInfos.get(10).getName();
                            mType = 2;
                            startLeftDecetion();
                        } else {
                            narrowVision(visionInfos.get(rightVisionAngle));
                        }
                    } else {
                        //错误 展示第二张图
                        rightErrorTimes++;
                        narrowVision2(visionInfos.get(rightVisionAngle));
                    }
                } else {
                    //第二次
                    if (direction == visionInfos.get(rightVisionAngle).getDirection2()) {
                        //正确 重置错误次数 缩小尺寸
                        rightErrorTimes = 0;
                        rightVisionAngle++;
                        if (rightVisionAngle == visionInfos.size()) {
                            // 5.0
                            rightVision = visionInfos.get(10).getName();
                            mType = 2;
                            startLeftDecetion();
                        } else {
                            narrowVision(visionInfos.get(rightVisionAngle));
                        }
                    } else {
                        //错误
                        rightVision = visionInfos.get(rightVisionAngle).getName();
                        mType = 2;
                        startLeftDecetion();
                    }
                }
                break;
            case 2:
                //左
                if (leftErrorTimes == 0) {
                    //第一次
                    if (direction == visionInfos.get(leftVisionAngle).getDirection()) {
                        //正确 缩小尺寸
                        leftVisionAngle++;
                        if (leftVisionAngle == visionInfos.size()) {
                            // 5.0
                            leftVision = visionInfos.get(10).getName();
                            mPresenter.saveEyeHealthData(leftVision+","+rightVision,mfamilyId);
                        } else {
                            narrowVision(visionInfos.get(leftVisionAngle));
                        }
                    } else {
                        //错误 展示第二张图
                        leftErrorTimes++;
                        narrowVision2(visionInfos.get(leftVisionAngle));
                    }
                } else {
                    //第二次
                    if (direction == visionInfos.get(leftVisionAngle).getDirection2()) {
                        //正确 重置错误次数 缩小尺寸
                        leftErrorTimes = 0;
                        leftVisionAngle++;
                        if (leftVisionAngle == visionInfos.size()) {
                            // 5.0
                            leftVision = visionInfos.get(10).getName();
                            mPresenter.saveEyeHealthData(leftVision+","+rightVision,mfamilyId);
                        } else {
                            narrowVision(visionInfos.get(leftVisionAngle));
                        }
                    } else {
                        //错误
                        leftVision = visionInfos.get(leftVisionAngle).getName();
                        mPresenter.saveEyeHealthData(leftVision+","+rightVision,mfamilyId);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void startLeftDecetion() {
        visionType.setText("左眼");
        visionType2.setText(getResources().getString(R.string.sign_right_text));
        mStartBtn.setText("继续测试");
        mStartView.setVisibility(View.VISIBLE);
        mVisionView.setVisibility(View.GONE);
    }

    @Override
    public Activity getThisContext() {
        return getActivity();
    }


}
