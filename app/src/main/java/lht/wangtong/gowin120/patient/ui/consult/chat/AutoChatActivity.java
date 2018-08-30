package lht.wangtong.gowin120.patient.ui.consult.chat;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.AutoMessageAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AutoMessage;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;
import lht.wangtong.gowin120.patient.view.OrdinaryTitleBar;

/**
 * 自动回复聊天界面
 *
 * @author luoyc
 */
@Route(path = "/consult/chat/AutoChatActivity")
public class AutoChatActivity extends BaseActivity<AutoChatPresenter> implements AutoChatContract.View {
    @BindView(R.id.chat_title)
    OrdinaryTitleBar titleBar;
    @BindView(R.id.user_head)
    CircleImageView mHeadView;
    @BindView(R.id.input_panel)
    AutoChatInput chatInput;
    @BindView(R.id.list)
    RecyclerView mMessageView;
    AutoMessageAdapter messageAdapter;
    private List<AutoMessage> messageList;
    private SimpleDateFormat dateFormat;
    private int mTopViewH = 0;
    private int mHeadViewH = 0;
    //询问次数
    private int times = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auto_chat;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mMessageView.setLayoutManager(new LinearLayoutManager(this));
        initHeadView();
        chatInput.setChatView(this);
        initData();
    }

    @Override
    public void initHeadView() {
        //获取当前headview的top距离
        // 向 ViewTreeObserver 注册方法，以获取控件尺寸
        ViewTreeObserver mTopViewViewTreeObserver = titleBar.getViewTreeObserver();
        mTopViewViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTopViewH = titleBar.getBottom();
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                titleBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ViewTreeObserver mHeadViewViewTreeObserver = mHeadView.getViewTreeObserver();
        mHeadViewViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeadViewH = mHeadView.getHeight();
                int marginTop = mTopViewH - mHeadViewH / 2;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mHeadView.getLayoutParams();
                layoutParams.topMargin = marginTop;
                mHeadView.setLayoutParams(layoutParams);
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                mHeadView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void initData() {
        dateFormat = new SimpleDateFormat("HH:mm");
        messageList = new ArrayList<>();
        messageAdapter = new AutoMessageAdapter(messageList);
        mMessageView.setAdapter(messageAdapter);
        //机器人招呼
        AutoMessage message = new AutoMessage();
        message.setAnswer("您好，我是小艾智能助手，请问有什么可以帮助您？");
        message.setTime(TimeUtils.getNowString(dateFormat));
        message.setDate(TimeUtils.getNowDate());
        message.setHasTime(true);
        messageList.add(message);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setMessage(AutoMessage message) {
        KeyboardUtils.hideSoftInput(this);
        if (!TextUtils.isEmpty(message.getAnswer())) {
            if (times < 4) {
                message.setTime(TimeUtils.getNowString(dateFormat));
                message.setDate(TimeUtils.getNowDate());
                long timeSpan = TimeUtils.getTimeSpan(message.getDate(), messageList.get(messageList.size() - 1).getDate(), TimeConstants.MIN);
                if (timeSpan > 5) {
                    message.setHasTime(true);
                }
                messageList.add(message);
                messageAdapter.notifyDataSetChanged();
                mMessageView.smoothScrollToPosition(messageAdapter.getData().size() - 1);
            } else {
                mPresenter.getConsultEmployeeInfo("1");
            }
        } else {
            mPresenter.getConsultEmployeeInfo("1");
        }
    }

    @Override
    public void skipChatActivity(ConsultEmployeeInfo employeeInfo) {
        ARouter.getInstance().build("/tencent/ui/ChatActivity")
                .withParcelable("mEmployeeInfo", employeeInfo)
                .withInt("mChatType", 101)
                .navigation();
        finish();
    }

    @Override
    public void sending() {

    }

    @Override
    public void sendText() {

        AutoMessage message = new AutoMessage();
        message.setSelf(true);
        message.setQuestion(chatInput.getText().toString());
        message.setTime(TimeUtils.getNowString(dateFormat));
        message.setDate(TimeUtils.getNowDate());
        long timeSpan = TimeUtils.getTimeSpan(message.getDate(), messageList.get(messageList.size() - 1).getDate(), TimeConstants.MIN);
        if (timeSpan > 5) {
            message.setHasTime(true);
        }
        messageList.add(message);
        times++;
        messageAdapter.notifyDataSetChanged();
        chatInput.setText("");
        mPresenter.getRobotAnswer(message.getQuestion());
    }


}
