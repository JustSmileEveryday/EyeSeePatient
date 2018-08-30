package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.MessageInfo;

/**
 * 消息适配器
 *
 * @author luoyc
 * @date 2018/3/27
 */

public class MessageAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {

    @Inject
    public MessageAdapter() {
        super(R.layout.item_message_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        helper.setText(R.id.message_type, item.getBusiTypeName());
        helper.setText(R.id.message_date, item.getMessageDate());
        helper.setText(R.id.message_content, item.getMessageContent());
        switch (item.getBusiType()) {
            case 6:
                //筛查报告提醒
                helper.setVisible(R.id.view_btn, true);
                helper.addOnClickListener(R.id.view_btn);
                break;
            case 7:
                //预约服务提醒
                helper.setVisible(R.id.view_btn, true);
                helper.addOnClickListener(R.id.view_btn);
                break;
            case 8:
                //咨询服务通知
                helper.setVisible(R.id.view_btn, true);
                helper.addOnClickListener(R.id.view_btn);
                break;
            case 9:
                //服务消息提醒
                helper.setVisible(R.id.view_btn, true);
                helper.addOnClickListener(R.id.view_btn);
                break;
            default:
                helper.setVisible(R.id.view_btn, false);
                break;
        }
    }
}
