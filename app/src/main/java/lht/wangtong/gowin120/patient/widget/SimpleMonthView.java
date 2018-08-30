package lht.wangtong.gowin120.patient.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import lht.wangtong.gowin120.patient.R;

/**
 * @author huanghaibin
 * @date 2017/11/15
 */

public class SimpleMonthView extends MonthView {

    private int mRadius;
    private Bitmap mSignBitmap;
    private Context mContext;
    private Rect mSignSrcRect, mSignDesRect;
    private Paint mSignPaint;

    public SimpleMonthView(Context context) {
        super(context);
        mContext = context;
        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));

    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
        mSignBitmap = ((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.click_sign_btn_img)).getBitmap();
        mSignSrcRect = new Rect(0, 0, mSignBitmap.getWidth(), mSignBitmap.getHeight());
        mSignPaint = new Paint();
        mSignPaint.setStyle(Paint.Style.STROKE);
        mSignPaint.setColor(Color.parseColor("#C5C9CD"));
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
        if (calendar.isCurrentDay() && !hasScheme) {
            mSignDesRect = new Rect(x, y, mSignBitmap.getWidth() + x, mSignBitmap.getHeight() + y);
            canvas.drawBitmap(mSignBitmap, mSignSrcRect, mSignDesRect, mSignPaint);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSignBitmap != null && !mSignBitmap.isRecycled()) {
            mSignBitmap = null;
        }
        mContext = null;
    }
}
