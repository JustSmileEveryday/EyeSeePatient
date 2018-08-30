package lht.wangtong.gowin120.patient.ui.mine.address;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AddressBean;
import lht.wangtong.gowin120.patient.bean.AddressInfo;

/**
 * 新增地址
 *
 * @author luoyc
 */
@Route(path = "/mine/address/AddAddressActivity")
public class AddAddressActivity extends BaseActivity<AddAddressPresenter> implements AddAddressContact.View {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address_area)
    TextView addressArea;
    @BindView(R.id.address_detail)
    EditText addressDetail;
    @Autowired
    AddressInfo mAddressInfo;
    private OptionsPickerView areaPtions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.initData();
        setAddress(mAddressInfo);
    }

    @OnClick({R.id.next_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                KeyboardUtils.hideSoftInput(this);
                areaPtions.show();
                break;
            case R.id.save_btn:
                if (mAddressInfo == null) {
                    mPresenter.addAddress("", name.getText().toString().trim(), phone.getText().toString().trim()
                            , addressDetail.getText().toString().trim(), addressArea.getText().toString());
                } else {
                    mPresenter.addAddress(mAddressInfo.getAddrId(), name.getText().toString().trim(), phone.getText().toString().trim()
                            , addressDetail.getText().toString().trim(), addressArea.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setAreaList(final ArrayList<AddressBean> areaList, final ArrayList<ArrayList<String>> options2Items, final ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        if (areaPtions == null) {
            areaPtions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String areaText = areaList.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                    addressArea.setText(areaText);
                }
            })
                    .setSubmitText("确定")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setSubCalSize(18)//确定和取消文字大小
                    .setContentTextSize(20)//滚轮文字大小
                    .isCenterLabel(true)
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.time_text_color))//取消按钮文字颜色;
                    .build();
            areaPtions.setPicker(areaList, options2Items, options3Items);//三级选择器
        }
    }

    @Override
    public Context getThisContext() {
        return this;
    }

    @Override
    public void setAddress(AddressInfo addressInfo) {
        if (addressInfo == null) {
            return;
        }
        name.setText(addressInfo.getReceName());
        phone.setText(addressInfo.getMobilePhone());
        addressArea.setText(addressInfo.getAddrArea());
        addressDetail.setText(addressInfo.getStreet());
    }

    @Override
    public void addSuccess() {
        Intent data = new Intent();
        data.putExtra("isUpdate", true);
        setResult(RESULT_OK, data);
        finish();
    }
}
