package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * @author luoyc
 */
public class HalfLinearLayout extends LinearLayout {
    public HalfLinearLayout(Context context) {
        super(context);
    }

    public HalfLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int newWidth= MeasureSpec.makeMeasureSpec(width / 2 - 20, MeasureSpec.EXACTLY);
        super.onMeasure(newWidth, heightMeasureSpec);
    }
}
