package lht.wangtong.gowin120.patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.AgentInfo;


/**
 * 选择筛查点
 * Created by luoyc on 2017/8/22.
 */

public class ServiceAgentAdapter extends BaseQuickAdapter<AgentInfo, BaseViewHolder> {

    @Inject
    public ServiceAgentAdapter() {
        super(R.layout.choose_agent_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, AgentInfo item) {
        helper.setText(R.id.agent_name, item.getAgentName());
        if (item.isCheck()) {
            helper.setBackgroundRes(R.id.choose_img,R.drawable.agent_choosed_img);
        } else {
            helper.setBackgroundRes(R.id.choose_img,R.drawable.agent_un_choosed_img);
        }
    }
}
