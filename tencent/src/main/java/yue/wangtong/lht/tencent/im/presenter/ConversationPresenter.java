package yue.wangtong.lht.tencent.im.presenter;

import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import yue.wangtong.lht.tencent.im.event.FriendshipEvent;
import yue.wangtong.lht.tencent.im.event.GroupEvent;
import yue.wangtong.lht.tencent.im.event.MessageEvent;
import yue.wangtong.lht.tencent.im.viewfeatures.ConversationView;

/**
 * 会话界面逻辑
 * @author luoyc
 */
public class ConversationPresenter implements Observer {

    private static final String TAG = "ConversationPresenter";
    private ConversationView view;

    public ConversationPresenter(ConversationView view) {
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
        //注册好友关系链监听
        FriendshipEvent.getInstance().addObserver(this);
        //注册群关系监听
        GroupEvent.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
            view.updateMessage(msg);
        } else if (observable instanceof FriendshipEvent) {
            FriendshipEvent.NotifyCmd cmd = (FriendshipEvent.NotifyCmd) data;
            switch (cmd.type) {
                case ADD_REQ:
                case READ_MSG:
                case ADD:
                    view.updateFriendshipMessage();
                    break;
                default:
                    break;
            }
        } else if (observable instanceof GroupEvent) {
            GroupEvent.NotifyCmd cmd = (GroupEvent.NotifyCmd) data;
            switch (cmd.type) {
                case UPDATE:
                    view.updateGroupInfo((TIMGroupCacheInfo) cmd.data);
                    break;
                case DEL:
                    view.removeConversation((String) cmd.data);
                    break;
                default:
                    break;
            }
        }
    }


    public void getConversation() {
        List<TIMConversation> list = new ArrayList<>();
        //获取会话个数
        long cnt = TIMManager.getInstance().getConversationCount();
        Log.d(TAG, "get " + cnt + " conversations");
        //遍历会话列表
        for (long i = 0; i < cnt; ++i) {
            //根据索引获取会话
            final TIMConversation conversation = TIMManager.getInstance().getConversationByIndex(i);
            if (conversation.getType() == TIMConversationType.System) {
                continue;
            }
            list.add(conversation);
            conversation.getMessage(1, null, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "get message error" + s);
                }

                @Override
                public void onSuccess(List<TIMMessage> timMessages) {
                    view.updateMessage(timMessages.get(0));
                }
            });
        }
        view.initView(list);
    }

    /**
     * 删除会话
     *
     * @param type 会话类型
     * @param id   会话对象id
     */
    public boolean delConversation(TIMConversationType type, String id) {
        return TIMManager.getInstance().deleteConversationAndLocalMsgs(type, id);
    }

}
