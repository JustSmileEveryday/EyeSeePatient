package lht.wangtong.gowin120.patient.tencent.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;
import java.util.Observer;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.tencent.model.CustomMessage;
import lht.wangtong.gowin120.patient.tencent.model.Message;
import lht.wangtong.gowin120.patient.tencent.model.MessageFactory;
import lht.wangtong.gowin120.patient.ui.MainActivity;
import lht.wangtong.gowin120.patient.ui.splash.SplashActivity;
import yue.wangtong.lht.tencent.event.MessageEvent;

/**
 * 在线消息通知展示
 *
 * @author luoyc
 */
public class PushUtil implements Observer {

    private static final String TAG = PushUtil.class.getSimpleName();

    private static int pushNum = 0;
    private static PushUtil instance = new PushUtil();
    private final int pushId = 1;

    private PushUtil() {
        MessageEvent.getInstance().addObserver(this);
    }

    public static PushUtil getInstance() {
        return instance;
    }

    public static void resetPushNum() {
        pushNum = 0;
    }

    private void PushNotify(TIMMessage msg) {
        //系统消息，自己发的消息，程序在前台的时候不通知
        if (msg == null ||
                (msg.getConversation().getType() != TIMConversationType.Group &&
                        msg.getConversation().getType() != TIMConversationType.C2C) ||
                msg.isSelf() ||
                msg.getRecvFlag() == TIMGroupReceiveMessageOpt.ReceiveNotNotify ||
                MessageFactory.getMessage(msg) instanceof CustomMessage) {
            return;
        }
        String senderStr, contentStr;
        Message message = MessageFactory.getMessage(msg);
        if (message == null) {
            return;
        }
        EventBus.getDefault().postSticky(new lht.wangtong.gowin120.patient.bean.event.MessageEvent(true, -1));
        if (ActivityUtils.getTopActivity() instanceof MainActivity) {
            if (((MainActivity) ActivityUtils.getTopActivity()).getmLastFgIndex() != 1) {
                EventBus.getDefault().postSticky(new lht.wangtong.gowin120.patient.bean.event.MessageEvent(true, -1));
            }else {
                EventBus.getDefault().postSticky(new lht.wangtong.gowin120.patient.bean.event.MessageEvent(true, 0));
            }
        }
        if (Foreground.get().isForeground()) {
            return;
        }
        senderStr = message.getSender();
        contentStr = message.getSummary();
        NotificationManager mNotificationManager = (NotificationManager) App.getAppContext().getSystemService(App.getAppContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(App.getAppContext());
        Intent notificationIntent = new Intent(App.getAppContext(), SplashActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(App.getAppContext(), 0,
                notificationIntent, 0);
        mBuilder.setContentTitle(senderStr)//设置通知栏标题
                .setContentText(contentStr)
                .setContentIntent(intent) //设置通知栏点击意图
//                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker(senderStr + ":" + contentStr) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(pushId, notify);
    }

    public void reset() {
        NotificationManager notificationManager = (NotificationManager) App.getAppContext().getSystemService(App.getAppContext().NOTIFICATION_SERVICE);
        notificationManager.cancel(pushId);
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            if (data instanceof TIMMessage) {
                TIMMessage msg = (TIMMessage) data;
                if (msg != null) {
                    PushNotify(msg);
                }
            }
        }
    }
}
