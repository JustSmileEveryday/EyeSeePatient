package lht.wangtong.gowin120.patient.ui.consult;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.TIMConversation;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.HistoryConsultAdapter;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.ConsultInfo;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.tencent.model.MessageFactory;
import yue.wangtong.lht.tencent.im.presenter.ConversationPresenter;
import yue.wangtong.lht.tencent.im.viewfeatures.ConversationView;

/**
 * 历史咨询
 *
 * @author luoyc
 */
public class HistoryConsultFragment extends BaseFragment<HistoryConsultPresenter> implements HistoryConsultContact.View
        , SwipeRefreshLayout.OnRefreshListener, ConversationView, HistoryConsultAdapter.OnItemClickListener, HistoryConsultAdapter.OnItemChildClickListener {

    @BindView(R.id.consult_list)
    RecyclerView mConsultRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    HistoryConsultAdapter mAdapter;
    private ConversationPresenter presenter;
    private ArrayList<TIMConversation> conversationList = new ArrayList<>();
    private ArrayList<ConsultInfo> consultInfos;

    public static HistoryConsultFragment newInstance() {
        return new HistoryConsultFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_consult;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mConsultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mConsultRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        consultInfos = new ArrayList<>();
        presenter = new ConversationPresenter(this);
        presenter.getConversation();
        mPresenter.getMemberConsultList();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void setConsultList(List<ConsultInfo> consultList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (consultInfos.size() > 0) {
            consultInfos.clear();
        }
        consultInfos.addAll(consultList);
        fuseDatas();
    }

    /**
     * item对应的消息数据融入lists
     */
    @Override
    public synchronized void fuseDatas() {
        for (ConsultInfo info : consultInfos) {
            for (TIMConversation conversation : conversationList) {
                if (info.getVideoUserId().equals(conversation.getPeer())) {
                    //未读消息数目
                    info.setUnreadNum(conversation.getUnreadMessageNum());
                    List<TIMMessage> messages = conversation.getLastMsgs(1);
                    if (messages.size() > 0) {
                        //最后一条消息
                        TIMMessage message = messages.get(messages.size() - 1);
                        //消息时间
                        info.setLastMessageTime(message.timestamp());
                        //消息简介
                        if (message.getElement(0).getType().equals(TIMElemType.Custom)) {
                            if (messages.size() > 2) {
                                TIMMessage message1 = messages.get(messages.size() - 2);
                                //消息时间
                                info.setLastMessageTime(message1.timestamp());
                                info.setLastMessageSummary(MessageFactory.getMessage(message1).getSummary());
                            }
                        } else {
                            info.setLastMessageSummary(MessageFactory.getMessage(message).getSummary());
                        }
                    }
                }
            }
        }
        Collections.sort(consultInfos);
        mAdapter.setNewData(consultInfos);
    }

    /**
     * 初始化界面或刷新界面
     *
     * @param conversationList
     */
    @Override
    public void initView(List<TIMConversation> conversationList) {
        this.conversationList.clear();
        for (TIMConversation item : conversationList) {
            switch (item.getType()) {
                case C2C:
                    this.conversationList.add(item);
                    break;
                case Group:
                    break;
                default:
                    break;
            }
        }
        fuseDatas();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (consultInfos.size() > 0) {
            fuseDatas();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loginEventBus(UserLoginEvent event) {
        if (event.isLogin()) {
            mPresenter.getMemberConsultList();
        }
    }

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    @Override
    public void updateMessage(TIMMessage message) {
        if (message == null) {
            return;
        }
        TIMConversation conversation = new TIMConversation(message.getConversation().getPeer());
        if (message.getElement(0).getType() == TIMElemType.Custom) {
            conversation.setReadMessage(message);
        }
        Iterator<TIMConversation> iterator = conversationList.iterator();
        while (iterator.hasNext()) {
            TIMConversation c = iterator.next();
            if (conversation.equals(c)) {
                conversation = c;
                iterator.remove();
                break;
            }
        }
        conversationList.add(conversation);
        fuseDatas();
    }

    /**
     * 更新好友关系链消息
     */
    @Override
    public void updateFriendshipMessage() {

    }

    /**
     * 删除会话
     *
     * @param identify
     */
    @Override
    public void removeConversation(String identify) {

    }

    /**
     * 更新群信息
     *
     * @param info
     */
    @Override
    public void updateGroupInfo(TIMGroupCacheInfo info) {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ((ConsultInfo) adapter.getData().get(position)).setUnreadNum(0);
        adapter.notifyItemChanged(position);
        switch (((ConsultInfo) adapter.getData().get(position)).getConsultEmployeeType()) {
            case 1:
                //运营人员
                ARouter.getInstance().build("/tencent/ui/ChatActivity")
                        .withParcelable("mConsultInfo", (ConsultInfo) adapter.getData().get(position))
                        .withInt("mChatType", 105)
                        .navigation();
                break;
            case 2:
                //咨询医生
                ARouter.getInstance().build("/tencent/ui/ChatActivity")
                        .withParcelable("mConsultInfo", (ConsultInfo) adapter.getData().get(position))
                        .withInt("mChatType", 105)
                        .navigation();
                break;
            case 3:
                //普通医生
                ARouter.getInstance().build("/tencent/ui/ChatActivity")
                        .withParcelable("mConsultInfo", (ConsultInfo) adapter.getData().get(position))
                        .withInt("mChatType", 103)
                        .navigation();
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.head_img:
                ARouter.getInstance().build("/consult/doctor/DoctorActivity")
                        .withString("doctorId", ((ConsultInfo) adapter.getData().get(position)).getEmployeeId())
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
