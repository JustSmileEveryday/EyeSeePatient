package lht.wangtong.gowin120.patient.ui.home.detection;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.EyeHealthData;

/**
 * 测试结果页面
 *
 * @author luoyc
 * @date 2018/3/20
 */
@Route(path = "/home/detection/DetectionCompleteActivity")
public class DetectionCompleteActivity extends BaseActivity<DetectionCompletePresenter> implements DetectionCompleteContact.View {
    @BindView(R.id.achromatopsia_layout)
    LinearLayout mAchromatopsiaLayout;
    @BindView(R.id.vision_layout)
    LinearLayout mVisionLayout;
    @BindView(R.id.achromatopsia_num)
    TextView mAchromatopsiaNum;
    @BindView(R.id.right_vision)
    TextView mRightVision;
    @BindView(R.id.left_vision)
    TextView mLeftVision;
    @BindView(R.id.continue_vision_detection)
    TextView mVisionDetection;
    @BindView(R.id.continue_achromatopsia_detection)
    TextView mAchromatopsiaDetection;
    @BindView(R.id.continue_astigmatism_detection)
    TextView mAstigmatismDetection;
    @BindView(R.id.continue_macular_detection)
    TextView mMacularDetection;
    @BindView(R.id.achromatopsia_probability_text)
    TextView mAchromatopsiaText;
    @Autowired
    EyeHealthData mEyeHealthData;
    private DecimalFormat df;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detection_complete;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        df = new DecimalFormat("0%");
        initData();
        if (mEyeHealthData != null) {
            mPresenter.saveEyeHealthData(mEyeHealthData);
        }
    }

    @Override
    public void initData() {
        if (mEyeHealthData != null) {
            switch (mEyeHealthData.getDataType()) {
                case 1:
                    //视力测试
                    mVisionLayout.setVisibility(View.VISIBLE);
                    mAchromatopsiaLayout.setVisibility(View.GONE);
                    String[] vision = mEyeHealthData.getDataValue().split(",");
                    mLeftVision.setText(vision[0]);
                    mRightVision.setText(vision[1]);
                    mAchromatopsiaDetection.setText("继续色盲检测");
                    mAchromatopsiaDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAstigmatismDetection.setText("继续散光检测");
                    mAstigmatismDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mMacularDetection.setText("继续黄斑检测");
                    mMacularDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mVisionDetection.setText("视力检测已完成");
                    mVisionDetection.setClickable(false);
                    mVisionDetection.setBackgroundResource(R.drawable.gray_btn_bg);
                    break;
                case 3:
                    //色盲检测
                    mAchromatopsiaLayout.setVisibility(View.VISIBLE);
                    mVisionLayout.setVisibility(View.GONE);
                    mAchromatopsiaNum.setText(df.format(Float.valueOf(mEyeHealthData.getDataValue())));
                    if (TextUtils.equals("0%", df.format(Float.valueOf(mEyeHealthData.getDataValue())))) {
                        mAchromatopsiaText.setText("您有很小几率是色盲");
                    } else {
                        mAchromatopsiaText.setText("您有很大几率是色盲");
                    }
                    mVisionDetection.setText("继续视力检测");
                    mVisionDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAstigmatismDetection.setText("继续散光检测");
                    mAstigmatismDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mMacularDetection.setText("继续黄斑检测");
                    mMacularDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAchromatopsiaDetection.setText("色盲检测已完成");
                    mAchromatopsiaDetection.setClickable(false);
                    mAchromatopsiaDetection.setBackgroundResource(R.drawable.gray_btn_bg);
                    break;
                case 4:
                    //散光检测
                    mAchromatopsiaLayout.setVisibility(View.VISIBLE);
                    mVisionLayout.setVisibility(View.GONE);
                    mAchromatopsiaNum.setText(df.format(Float.valueOf(mEyeHealthData.getDataValue())));
                    if (TextUtils.equals("0%", df.format(Float.valueOf(mEyeHealthData.getDataValue())))) {
                        mAchromatopsiaText.setText("您有很小几率是散光");
                    } else {
                        mAchromatopsiaText.setText("您有很大几率是散光");
                    }
                    mVisionDetection.setText("继续视力检测");
                    mVisionDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAchromatopsiaDetection.setText("继续色盲检测");
                    mAchromatopsiaDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mMacularDetection.setText("继续黄斑检测");
                    mMacularDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAstigmatismDetection.setText("散光检测已完成");
                    mAstigmatismDetection.setClickable(false);
                    mAstigmatismDetection.setBackgroundResource(R.drawable.gray_btn_bg);
                    break;
                case 5:
                    //黄斑检测
                    mAchromatopsiaLayout.setVisibility(View.VISIBLE);
                    mVisionLayout.setVisibility(View.GONE);
                    mAchromatopsiaNum.setText(df.format(Float.valueOf(mEyeHealthData.getDataValue())));
                    if (TextUtils.equals("0%", df.format(Float.valueOf(mEyeHealthData.getDataValue())))) {
                        mAchromatopsiaText.setText("您有很小几率是黄斑");
                    } else {
                        mAchromatopsiaText.setText("您有很大几率是黄斑");
                    }
                    mVisionDetection.setText("继续视力检测");
                    mVisionDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAchromatopsiaDetection.setText("继续色盲检测");
                    mAchromatopsiaDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mAstigmatismDetection.setText("继续散光检测");
                    mAstigmatismDetection.setBackgroundResource(R.drawable.green_btn_bg_5);
                    mMacularDetection.setText("黄斑检测已完成");
                    mMacularDetection.setClickable(false);
                    mMacularDetection.setBackgroundResource(R.drawable.gray_btn_bg);
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.continue_vision_detection, R.id.continue_achromatopsia_detection,R.id.continue_astigmatism_detection,R.id.continue_macular_detection, R.id.back_home_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_vision_detection:
                ARouter.getInstance().build("/home/detection/EyeDetectionActivity")
                        .withInt("mCurrentItem", 0)
                        .withString("mfamilyId", mEyeHealthData.getFamilyId())
                        .navigation();
                finish();
                break;
            case R.id.continue_achromatopsia_detection:
                ARouter.getInstance().build("/home/detection/EyeDetectionActivity")
                        .withInt("mCurrentItem", 1)
                        .withString("mfamilyId", mEyeHealthData.getFamilyId())
                        .navigation();
                finish();
                break;
            case R.id.continue_astigmatism_detection:
                ARouter.getInstance().build("/home/detection/EyeDetectionActivity")
                        .withInt("mCurrentItem", 2)
                        .withString("mfamilyId", mEyeHealthData.getFamilyId())
                        .navigation();
                finish();
                break;
            case R.id.continue_macular_detection:
                ARouter.getInstance().build("/home/detection/EyeDetectionActivity")
                        .withInt("mCurrentItem", 3)
                        .withString("mfamilyId", mEyeHealthData.getFamilyId())
                        .navigation();
                finish();
                break;
            case R.id.back_home_btn:
                finish();
                break;
            default:
                break;
        }
    }
}
