package lht.wangtong.gowin120.patient.ui.mine.address;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.AddressAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AddressInfo;

/**
 * 我的收货地址
 *
 * @author luoyc
 */
@Route(path = "/mine/address/MyAddressActivity")
public class MyAddressActivity extends BaseActivity<MyAddressPresenter> implements MyAddressContact.View, AddressAdapter.OnItemChildClickListener,
        AddressAdapter.OnItemClickListener {

    private final int ADD_PLAN = 101;
    @BindView(R.id.address_list)
    RecyclerView addressListView;
    @Inject
    AddressAdapter addressAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        addressListView.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        addressAdapter.setOnItemClickListener(this);
        addressAdapter.setOnItemChildClickListener(this);
        addressListView.setAdapter(addressAdapter);
        mPresenter.getAddressList();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        final AddressInfo addressInfo = (AddressInfo) adapter.getData().get(position);
        switch (view.getId()) {
            case R.id.choose_img:
                if (addressInfo.isChoosed()) {
                    addressInfo.setChoosed(false);
                } else {
                    addressInfo.setChoosed(true);
                    Intent data = new Intent();
                    data.putExtra("AddressInfo", addressInfo);
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
            case R.id.address_edit_btn:
                ARouter.getInstance().build("/mine/address/AddAddressActivity")
                        .withParcelable("mAddressInfo", addressInfo)
                        .navigation(this, ADD_PLAN);
                break;
            case R.id.address_delete_btn:
                new MaterialDialog.Builder(this)
                        .content("确定要删除此收货地址吗？")
                        .positiveText(R.string.sure)
                        .negativeText(R.string.cancel)
                        .positiveColor(Color.parseColor("#01C1B4"))
                        .negativeColor(Color.parseColor("#3C4045"))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mPresenter.deleteAddress(addressInfo.getAddrId());
                                dialog.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @OnClick(R.id.add_btn)
    public void onViewClicked() {
        //新增地址
        ARouter.getInstance().build("/mine/address/AddAddressActivity")
                .navigation(this, ADD_PLAN);
    }

    @Override
    public void setAddressList(List<AddressInfo> addressInfos) {
        addressAdapter.setNewData(addressInfos);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == ADD_PLAN) {
            boolean isUpdate = data.getBooleanExtra("isUpdate", false);
            if (isUpdate) {
                mPresenter.getAddressList();
            }
        }
    }
}
