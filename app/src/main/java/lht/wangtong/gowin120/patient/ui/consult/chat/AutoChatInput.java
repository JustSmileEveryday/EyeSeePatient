package lht.wangtong.gowin120.patient.ui.consult.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lht.wangtong.gowin120.patient.R;

;

/**
 * 自动聊天界面输入控件
 *
 * @author luoyc
 */
public class AutoChatInput extends RelativeLayout implements TextWatcher, View.OnClickListener {

    private static final String TAG = "AutoChatInput";
    private EditText editText;
    private ImageButton btnSend, btnAdd;
    private boolean isSendVisible;
    private InputMode inputMode = InputMode.NONE;
    private AutoChatContract.View chatView;
    private LinearLayout morePanel;


    public AutoChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.auto_chat_input, this);
        initView();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        morePanel = findViewById(R.id.morePanel);
        btnSend = findViewById(R.id.btn_send);
        btnAdd = findViewById(R.id.btn_add);
        btnSend.setOnClickListener(this);
        setSendBtn();
        editText = findViewById(R.id.input);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    updateView(InputMode.TEXT);
                }
            }
        });
        isSendVisible = editText.getText().length() != 0;
    }

    private void updateView(InputMode mode) {
        if (mode == inputMode) {
            return;
        }
        leavingCurrentState();
        switch (inputMode = mode) {
            case MORE:
                morePanel.setVisibility(VISIBLE);
                break;
            case TEXT:
                if (editText.requestFocus()) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
                break;
            default:
                break;
        }
    }

    private void leavingCurrentState() {
        switch (inputMode) {
            case TEXT:
                View view = ((Activity) getContext()).getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                editText.clearFocus();
                break;
            case MORE:
                morePanel.setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 关联聊天界面逻辑
     */
    public void setChatView(AutoChatContract.View chatView) {
        this.chatView = chatView;
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isSendVisible = s != null && s.length() > 0;
        setSendBtn();
        if (isSendVisible) {
            chatView.sending();
        }
    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setSendBtn() {
        if (isSendVisible) {
            btnAdd.setVisibility(GONE);
            btnSend.setVisibility(VISIBLE);
        } else {
            btnAdd.setVisibility(VISIBLE);
            btnSend.setVisibility(GONE);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_send) {
            chatView.sendText();
        }
        if (id == R.id.btn_add) {
            updateView(inputMode == InputMode.MORE ? InputMode.TEXT : InputMode.MORE);
        }
    }


    /**
     * 获取输入框文字
     */
    public Editable getText() {
        return editText.getText();
    }

    /**
     * 设置输入框文字
     */
    public void setText(String text) {
        editText.setText(text);
    }


    /**
     * 设置输入模式
     */
    public void setInputMode(InputMode mode) {
        updateView(mode);
    }

    public enum InputMode {
        TEXT,
        VOICE,
        EMOTICON,
        MORE,
        VIDEO,
        NONE,
    }

}
