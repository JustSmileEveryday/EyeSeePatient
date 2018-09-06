package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.AddressInfo;

/**
 * 地址适配器
 *
 * @author luoyc
 */
public class AddressAdapter extends BaseQuickAdapter<AddressInfo, BaseViewHolder> {

    @Inject
    AddressAdapter() {
        super(R.layout.item_address_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressInfo item) {
        helper.setText(R.id.name, item.getReceName());
        helper.setText(R.id.phone, item.getMobilePhone());
        helper.setText(R.id.address, item.getAddrArea() + item.getStreet());
        helper.addOnClickListener(R.id.choose_img);
        helper.addOnClickListener(R.id.address_edit_btn);
        helper.addOnClickListener(R.id.address_delete_btn);
        if (item.isChoosed()) {
            helper.setImageDrawable(R.id.choose_img,mContext.getResources().getDrawable( R.drawable.address_choosed_img));
        } else {
            helper.setImageDrawable(R.id.choose_img,mContext.getResources().getDrawable( R.drawable.address_unchoose_img));
        }
    }
}
