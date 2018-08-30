package lht.wangtong.gowin120.patient.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.AutoMessage;

/**
 * 自动聊天适配器
 *
 * @author Administrator
 */

public class AutoMessageAdapter extends BaseQuickAdapter<AutoMessage, BaseViewHolder> {

    public AutoMessageAdapter(List<AutoMessage> messages) {
        super(R.layout.item_auto_message_layout, messages);
    }

    @Override
    protected void convert(BaseViewHolder helper, AutoMessage item) {
        if (item.isHasTime()) {
            helper.setVisible(R.id.systemMessage, true);
            helper.setText(R.id.systemMessage, item.getTime());
        }
        if (item.isSelf()) {
            helper.setVisible(R.id.rightPanel, true);
            helper.setText(R.id.rightMessage, item.getQuestion());
        } else {
            helper.setVisible(R.id.leftPanel, true);
            helper.setText(R.id.leftMessage, item.getAnswer());
        }
    }
}
