package lht.wangtong.gowin120.patient.ui.mine.report;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AddressInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;

/**
 * 申请报告
 *
 * @author luoyc
 */
@Route(path = "/mine/report/ApplyReportActivity")
public class ApplyReportActivity extends BaseActivity<ApplyReportPresenter> implements ApplyReportContact.View {

    private final int ADDRESS = 1001;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.agent_name)
    EditText agentName;
    @BindView(R.id.other_info)
    EditText otherInfo;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.report_close_btn)
    ImageView reportCloseBtn;
    @BindView(R.id.no_data_layout)
    ConstraintLayout noDataLayout;
    @BindView(R.id.data_layout)
    LinearLayout dataLayout;
    private boolean isShow = false;
    private OptionsPickerView pvOptions;
    private String sexCode = "";
    private List<CommonCdInfo> sexCdInfoList;
    private String memberAddrId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_report;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.queryReportApplyStatus();
    }

    @OnClick({R.id.report_close_btn, R.id.save_btn, R.id.address_layout, R.id.sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sex:
                //性别
                showSex();
                break;
            case R.id.report_close_btn:
                if (isShow) {
                    isShow = false;
                    addressLayout.setVisibility(View.GONE);
                    reportCloseBtn.setImageResource(R.drawable.apply_report_close_btn_img);
                } else {
                    isShow = true;
                    addressLayout.setVisibility(View.VISIBLE);
                    reportCloseBtn.setImageResource(R.drawable.apply_report_open_btn_img);
                }
                break;
            case R.id.address_layout:
                //地址
                ARouter.getInstance().build("/mine/address/MyAddressActivity")
                        .navigation(this, ADDRESS);
                break;
            case R.id.save_btn:
                mPresenter.saveRadiographScreenReportApply(name.getText().toString().trim(), sexCode, age.getText().toString().trim(), phone.getText().toString().trim()
                        , agentName.getText().toString().trim(), otherInfo.getText().toString().trim(), memberAddrId);
                break;
            default:
                break;
        }
    }

    private void showSex() {
        if (pvOptions == null) {
            pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    sexCode = sexCdInfoList.get(options1).getCode();
                    sex.setText(sexCdInfoList.get(options1).getNameZh());
                }
            }).setSubmitText("确定")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setSubCalSize(18)//确定和取消文字大小
                    .setContentTextSize(20)//滚轮文字大小
                    .isCenterLabel(true)
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.time_text_color))//取消按钮文字颜色
                    .build();
            sexCdInfoList = CommonInfoUtils.getCommonCdInfo("SexType");
            List<String> textInfos = new ArrayList<>();
            for (CommonCdInfo info : sexCdInfoList) {
                textInfos.add(info.getNameZh());
            }
            pvOptions.setPicker(textInfos);
            KeyboardUtils.hideSoftInput(this);
            pvOptions.show();
        } else {
            KeyboardUtils.hideSoftInput(this);
            pvOptions.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == ADDRESS) {
            AddressInfo addressInfo = data.getParcelableExtra("AddressInfo");
            memberAddrId = addressInfo.getAddrId();
            address.setText(addressInfo.getReceName() + "  " + addressInfo.getMobilePhone() + "  " + addressInfo.getAddrArea() + addressInfo.getStreet());
        }
    }

    @Override
    public void showSuccess(boolean isShow) {
        if (isShow) {
            dataLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            noDataLayout.setVisibility(View.GONE);
            dataLayout.setVisibility(View.VISIBLE);
        }
    }
}
