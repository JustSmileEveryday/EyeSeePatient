package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author luoyc
 */
public class HalfRelativeLayout extends RelativeLayout {

    public HalfRelativeLayout(Context context) {
        super(context);
    }

    public HalfRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int newHeight= MeasureSpec.makeMeasureSpec(height / 2 - 20, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeight);
    }
}
