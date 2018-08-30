package lht.wangtong.gowin120.patient.tencent.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;
import lht.wangtong.gowin120.patient.bean.ConsultInfo;
import lht.wangtong.gowin120.patient.bean.RemoteConsultationId;
import lht.wangtong.gowin120.patient.bean.TencentUserInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.tencent.adapter.ChatAdapter;
import lht.wangtong.gowin120.patient.tencent.model.ImageMessage;
import lht.wangtong.gowin120.patient.tencent.model.Message;
import lht.wangtong.gowin120.patient.tencent.model.MessageFactory;
import lht.wangtong.gowin120.patient.tencent.model.TextMessage;
import lht.wangtong.gowin120.patient.tencent.model.VoiceMessage;
import lht.wangtong.gowin120.patient.tencent.utils.FileUtil;
import lht.wangtong.gowin120.patient.tencent.utils.MediaUtil;
import lht.wangtong.gowin120.patient.tencent.utils.RecorderUtil;
import lht.wangtong.gowin120.patient.tencent.view.ChatInput;
import lht.wangtong.gowin120.patient.tencent.view.VoiceSendingView;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import lht.wangtong.gowin120.patient.util.ProviderUtil;
import lht.wangtong.gowin120.patient.view.OrdinaryTitleBar;
import yue.wangtong.lht.tencent.presenter.ChatPresenter;
import yue.wangtong.lht.tencent.viewfeatures.ChatView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 聊天界面
 *
 * @author luoyc
 */

@Route(path = "/tencent/ui/ChatActivity")
public class ChatActivity extends FragmentActivity implements ChatView {

    private static final String TAG = "ChatActivity";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int IMAGE_STORE = 200;
    private static final int IMAGE_PREVIEW = 400;
    private static final int SEND_REPORT = 300;
    private static final int COMMENT = 600;
    OrdinaryTitleBar titleBar;
    @Autowired
    int mChatType;
    @Autowired
    ConsultEmployeeInfo mEmployeeInfo;
    @Autowired
    ConsultInfo mConsultInfo;
    @Autowired
    TencentUserInfo mTencentUserInfo;
    private CircleImageView mHeadView;
    private ArrayList<Message> messageList = new ArrayList<>();
    private ChatAdapter adapter;
    private ListView listView;
    private ChatPresenter presenter;
    private ChatInput input;
    private File cameraFile;
    private TextView rightText;
    private VoiceSendingView voiceSendingView;
    private RecorderUtil recorder = new RecorderUtil();
    private TIMConversationType type;
    private int mTopViewH = 0;
    private int mHeadViewH = 0;
    private String remoteConsultationId = "";

    public static void navToChat(Context context, String identify, TIMConversationType type) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("identify", identify);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ARouter.getInstance().inject(this);
        FileDownloader.setup(this);
        rightText = findViewById(R.id.right_text);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        titleBar = findViewById(R.id.chat_title);
        mHeadView = findViewById(R.id.user_head);
        input = findViewById(R.id.input_panel);
        voiceSendingView = findViewById(R.id.voice_sending);
        View chatHeaderView = LayoutInflater.from(this).inflate(R.layout.chat_notice_view, null);
        listView = findViewById(R.id.list);
        initHeadView();
        input.setChatView(this);
        type = TIMConversationType.C2C;
        if (mEmployeeInfo != null) {
            presenter = new ChatPresenter(this, mEmployeeInfo.getVideoUserId(), type);
            adapter = new ChatAdapter(this, R.layout.item_message, messageList, mEmployeeInfo.getEmployeeId(), mEmployeeInfo.getPicUrl());
            Glide.with(this)
                    .load(Api.HOST_IMG + mEmployeeInfo.getPicUrl())
                    .transition(withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            mHeadView.setImageDrawable(resource);
                        }
                    });
        }
        if (mConsultInfo != null) {
            presenter = new ChatPresenter(this, mConsultInfo.getVideoUserId(), type);
            adapter = new ChatAdapter(this, R.layout.item_message, messageList, mConsultInfo.getEmployeeId(), mConsultInfo.getPicUrl());
            Glide.with(this).load(Api.HOST_IMG + mConsultInfo.getPicUrl()).transition(withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            mHeadView.setImageDrawable(resource);
                        }
                    });
        }
        if (mTencentUserInfo != null) {
            presenter = new ChatPresenter(this, mTencentUserInfo.getVideoId(), type);
            adapter = new ChatAdapter(this, R.layout.item_message, messageList, mTencentUserInfo.getEmployeeId(), mTencentUserInfo.getPicUrl());
        }
        listView.setAdapter(adapter);
        listView.addHeaderView(chatHeaderView);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        input.setInputMode(ChatInput.InputMode.NONE);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int firstItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && firstItem == 0) {
                    //如果拉到顶端读取更多消息
                    presenter.getMessage(messageList.size() > 0 ? messageList.get(0).getMessage() : null, false);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                firstItem = firstVisibleItem;
            }
        });
        registerForContextMenu(listView);
        presenter.start();
        initData();
    }

    private void initData() {
        switch (mChatType) {
            case 101:
                //运营人员初次
                saveRemoteConsultation(mEmployeeInfo.getEmployeeId());
                break;
            case 102:
                //医生人员初次
                saveRemoteConsultation(mEmployeeInfo.getEmployeeId());
                break;
            case 103:
                //历史咨询
                getMemberNoCompleteConsult(mConsultInfo.getEmployeeId());
                break;
            case 104:
                //转交
                break;
            case 105:
                //历史咨询 医生
                getMemberNoCompleteConsult(mConsultInfo.getEmployeeId());
                break;
            default:
                break;
        }
    }

    /**
     * 患者新增咨询记录
     *
     * @param employeeId
     */
    private void saveRemoteConsultation(String employeeId) {
        HttpUtil.getObject(Api.saveRemoteConsultation.mapClear()
                .addMap("employeeId", employeeId)
                .addBody(), RemoteConsultationId.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    RemoteConsultationId mRemoteConsultationId = (RemoteConsultationId) obj;
                    remoteConsultationId = mRemoteConsultationId.getRemoteConsultationId();
                }
            }
        });
    }

    /**
     * 患者获取未完成的咨询记录
     *
     * @param employeeId
     */
    private void getMemberNoCompleteConsult(String employeeId) {
        HttpUtil.getObject(Api.getMemberNoCompleteConsult.mapClear()
                .addMap("employeeId", employeeId)
                .addBody(), RemoteConsultationId.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    RemoteConsultationId mRemoteConsultationId = (RemoteConsultationId) obj;
                    remoteConsultationId = mRemoteConsultationId.getRemoteConsultationId();
                    initView(mRemoteConsultationId);
                }
            }
        });
    }

    private void initView(final RemoteConsultationId remoteConsultationId) {
        if (remoteConsultationId.getRemoteConsultationId() == null || TextUtils.isEmpty(remoteConsultationId.getRemoteConsultationId())) {
            input.setVisibility(View.GONE);
            //完成咨询
            if (!TextUtils.isEmpty(remoteConsultationId.getCommentRemoteConsultationId())) {
                //可以评价
                if (mChatType == 105) {
                    //医生
                    rightText.setText("评价");
                    rightText.setVisibility(View.VISIBLE);
                    rightText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ARouter.getInstance().build("/consult/doctor/CommentActivity")
                                    .withString("commentRemoteConsultationId", remoteConsultationId.getCommentRemoteConsultationId())
                                    .navigation(ChatActivity.this, COMMENT);
                        }
                    });
                }
            }
        }
    }

    /**
     * 根据腾讯id获取用户信息
     *
     * @param videoUserId
     */
    private void getUserInfoByTencent(final String videoUserId, final String employeeId, final String operationRemoteConsultationId) {
        HttpUtil.getObject(Api.getUserInfoByTencent.mapClear()
                .addMap("videoUserId", videoUserId)
                .addBody(), TencentUserInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    TencentUserInfo userInfo = (TencentUserInfo) obj;
                    userInfo.setVideoId(videoUserId);
                    userInfo.setEmployeeId(employeeId);
                    userInfo.setOperationRemoteConsultationId(operationRemoteConsultationId);
                    ARouter.getInstance().build("/tencent/ui/ChatActivity")
                            .withParcelable("mTencentUserInfo", userInfo)
                            .withInt("mChatType", 104)
                            .navigation();
                    finish();
                }
            }
        });
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

    @Override
    protected void onPause() {
        super.onPause();
//        RefreshEvent.getInstance().onRefresh();
        presenter.readMessages();
        MediaUtil.getInstance().stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    /**
     * 显示消息
     *
     * @param message
     */
    @Override
    public void showMessage(TIMMessage message) {
        if (message == null) {
            adapter.notifyDataSetChanged();
        } else {
            if (message.getElement(0).getType() == TIMElemType.Custom) {
                String des = new String(((TIMCustomElem) message.getElement(0)).getData());
                String[] ss = des.split("-");
                if (ss[0].equals("EYESEE") && ss.length == 4) {
                    //转交跳转到医生
                    getUserInfoByTencent(ss[1], ss[2], ss[3]);
                } else if (ss[0].equals("VIDEO005") && ss.length == 2) {
                    //结束咨询
                    if (TextUtils.equals(ss[1], remoteConsultationId)) {
                        finish();
                    }
                }
            }
            Message mMessage = MessageFactory.getMessage(message);
            if (mMessage != null) {
                if (messageList.size() == 0) {
                    mMessage.setHasTime(null);
                } else {
                    mMessage.setHasTime(messageList.get(messageList.size() - 1).getMessage());
                }
                messageList.add(mMessage);
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }
        }
    }


    /**
     * 显示消息
     *
     * @param messages
     */
    @Override
    public void showMessage(List<TIMMessage> messages, boolean isFirst) {
        int newMsgNum = 0;
        for (int i = 0; i < messages.size(); ++i) {
            Message mMessage = MessageFactory.getMessage(messages.get(i));
            if (messages.get(i).getElement(0).getType() == TIMElemType.Custom) {

            }
            if (mMessage == null) {
                continue;
            }
            ++newMsgNum;
            if (i != messages.size() - 1) {
                mMessage.setHasTime(messages.get(i + 1));
                messageList.add(0, mMessage);
            } else {
                messageList.add(0, mMessage);
            }
        }
        if (isFirst) {
            switch (mChatType) {
                case 101:
                    //运营人员
                    TextMessage picMessage = new TextMessage("您好，我是小艾金牌眼管家，接下来将由我来解答您的问题！");
                    picMessage.setHasTime(true);
                    messageList.add(picMessage);
                    break;
                case 102:
                    //医生
                    TextMessage videoMessage = new TextMessage("您好，我是小艾在线专家医生" + mEmployeeInfo.getUserName() + "，接下来将由我来解答您的问题");
                    videoMessage.setHasTime(true);
                    messageList.add(videoMessage);
                    break;
                case 104:
                    TextMessage message = new TextMessage("您好，我是小艾在线专家医生" + mTencentUserInfo.getUserName() + "，接下来将由我来解答您的问题");
                    message.setHasTime(true);
                    messageList.add(message);
                    break;
                default:
                    break;
            }
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(newMsgNum);
    }


    /**
     * 清除所有消息，等待刷新
     */
    @Override
    public void clearAllMessage() {
        messageList.clear();
    }

    /**
     * 发送消息成功
     *
     * @param message 返回的消息
     */
    @Override
    public void onSendMessageSuccess(TIMMessage message) {
        showMessage(message);
    }

    /**
     * 发送消息失败
     *
     * @param code 返回码
     * @param desc 返回描述
     */
    @Override
    public void onSendMessageFail(int code, String desc, TIMMessage message) {
        long id = message.getMsgUniqueId();
        for (Message msg : messageList) {
            if (msg.getMessage().getMsgUniqueId() == id) {
                switch (code) {
                    case 80001:
                        //发送内容包含敏感词
                        msg.setDesc(getString(R.string.chat_content_bad));
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }

        adapter.notifyDataSetChanged();

    }

    /**
     * 发送图片消息
     */
    @Override
    public void sendImage() {
        Intent album = new Intent("android.intent.action.GET_CONTENT");
        album.setType("image/*");
        startActivityForResult(album, IMAGE_STORE);
    }

    /**
     * 发送照片消息
     */
    @Override
    public void sendPhoto() {
        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photo.resolveActivity(getPackageManager()) != null) {
            //创建临时图片
            cameraFile = FileUtil.getTempFile(FileUtil.FileType.IMG);
            Uri fileUri = null;
            if (cameraFile != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    fileUri = Uri.fromFile(cameraFile);
                } else {
                    /**
                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                     */
                    fileUri = FileProvider.getUriForFile(this, ProviderUtil.getFileProviderName(this), cameraFile);
                }
            }
            photo.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(photo, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    /**
     * 发送文本消息
     */
    @Override
    public void sendText() {
        Message message = new TextMessage(input.getText());
        presenter.sendMessage(message.getMessage());
        input.setText("");
    }


    /**
     * 开始发送语音消息
     */
    @Override
    public void startSendVoice() {
        voiceSendingView.setVisibility(View.VISIBLE);
        voiceSendingView.showRecording();
        recorder.startRecording();

    }

    /**
     * 结束发送语音消息
     */
    @Override
    public void endSendVoice() {
        voiceSendingView.release();
        voiceSendingView.setVisibility(View.GONE);
        recorder.stopRecording();
        if (recorder.getTimeInterval() < 1) {
            Toast.makeText(this, getResources().getString(R.string.chat_audio_too_short), Toast.LENGTH_SHORT).show();
        } else if (recorder.getTimeInterval() > 60) {
            Toast.makeText(this, getResources().getString(R.string.chat_audio_too_long), Toast.LENGTH_SHORT).show();
        } else {
            Message message = new VoiceMessage(recorder.getTimeInterval(), recorder.getFilePath());
            presenter.sendMessage(message.getMessage());
        }
    }


    /**
     * 结束发送语音消息
     */
    @Override
    public void cancelSendVoice() {

    }

    /**
     * 正在发送
     */
    @Override
    public void sending() {
//        if (type == TIMConversationType.C2C) {
//            Message message = new CustomMessage(CustomMessage.Type.TYPING);
//            presenter.sendOnlineMessage(message.getMessage());
//        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendReport() {
        ARouter.getInstance().build("/mine/report/ReportCenterActivity")
                .withString("mMemberName", LoginUtil.user.getMemberName())
                .withString("mHeadPic", LoginUtil.user.getPicUrl())
                .withInt("type", 2)
                .navigation(this, SEND_REPORT);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Message message = messageList.get(info.position);
        if (message.isSendFail()) {
            menu.add(0, 1, Menu.NONE, getString(R.string.chat_resend));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Message message = messageList.get(info.position);
        switch (item.getItemId()) {
            case 1:
                messageList.remove(message);
                presenter.sendMessage(message.getMessage());
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showImagePreview(cameraFile.getPath());
            }
        } else if (requestCode == IMAGE_STORE) {
            if (resultCode == RESULT_OK && data != null) {
                showImagePreview(FileUtil.getFilePath(this, data.getData()));
            }
        } else if (requestCode == IMAGE_PREVIEW) {
            if (resultCode == RESULT_OK) {
                boolean isOri = data.getBooleanExtra("isOri", false);
                String path = data.getStringExtra("path");
                File file = new File(path);
                if (file.exists()) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path, options);
                    if (file.length() == 0 && options.outWidth == 0) {
                        Toast.makeText(this, getString(R.string.chat_file_not_exist), Toast.LENGTH_SHORT).show();
                    } else {
                        if (file.length() > 1024 * 1024 * 10) {
                            Toast.makeText(this, getString(R.string.chat_file_too_large), Toast.LENGTH_SHORT).show();
                        } else {
                            Message message = new ImageMessage(path, isOri);
                            presenter.sendMessage(message.getMessage());
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.chat_file_not_exist), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == SEND_REPORT) {
            if (resultCode == RESULT_OK) {
                String path = data.getStringExtra("reprotImg");
                if (path.contains(",")) {
                    //多张
                    String[] paths = path.split(",");
                    downloadReport(paths);
                } else {
                    downloadReport(path);
                }
            }
        } else if (requestCode == COMMENT) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    /**
     * 下载报告  单任务下载
     *
     * @param path
     */
    private void downloadReport(String path) {
        FileDownloader.getImpl().create(Api.HOST_IMG + path)
                .setPath(FileUtil.getCacheFilePath(path))
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("luoyc", " pending ");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        Log.e("luoyc", " connected ");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {

                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (task.isReusedOldFile()) {
                            Message message = new ImageMessage(task.getPath(), false);
                            presenter.sendMessage(message.getMessage());
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e("luoyc", " error +  " + e.toString());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    /**
     * 下载报告 多任务下载
     *
     * @param paths
     */
    private void downloadReport(String[] paths) {
        final FileDownloadListener queueTarget = new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
            }

            @Override
            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if (task.isReusedOldFile()) {
                    Message message = new ImageMessage(task.getPath(), false);
                    presenter.sendMessage(message.getMessage());
                }
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
            }

            @Override
            protected void warn(BaseDownloadTask task) {
            }
        };
        // 第二种方式:

        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(queueTarget);

        final List<BaseDownloadTask> tasks = new ArrayList<>();
        for (String path : paths) {
            tasks.add(FileDownloader.getImpl().create(Api.HOST_IMG + path));
        }
        queueSet.disableCallbackProgressTimes(); // 由于是队列任务, 这里是我们假设了现在不需要每个任务都回调`FileDownloadListener#progress`, 我们只关系每个任务是否完成, 所以这里这样设置可以很有效的减少ipc.
        // 所有任务在下载失败的时候都自动重试一次
        queueSet.setAutoRetryTimes(1);
        //  串行执行该任务队列
        queueSet.downloadSequentially(tasks);
        // 最后你需要主动调用start方法来启动该Queue
        queueSet.start();
    }

    private void showImagePreview(String path) {
        if (path == null) {
            return;
        }
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra("path", path);
        startActivityForResult(intent, IMAGE_PREVIEW);
    }


}
