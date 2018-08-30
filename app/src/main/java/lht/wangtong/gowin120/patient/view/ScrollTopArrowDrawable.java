package lht.wangtong.gowin120.patient.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import static android.graphics.Paint.Cap.ROUND;

/**
 * Created by tomlezen.
 * Data: 2018/3/13.
 * Time: 18:14.
 */

public class ScrollTopArrowDrawable extends Drawable {

    private Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RecyclerView rv;

    /**
     * 箭头高度.
     */
    private Float arrowHeight = 20f;
    /**
     * 箭头圆角度.
     */
    private Float arrowRound = 4f;
    /**
     * 箭头边长.
     */
    private Float arrowLen = 0f;

    private PointF arrowCenter = new PointF();
    private Path arrowPath = new Path();

    private int selectPosition = 0;

    private ValueAnimator valueAnimator = null;

    public ScrollTopArrowDrawable(RecyclerView rv, int backgroundColor) {
        this.rv = rv;
        this.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (calculateArrowX()) {
                    stopAnimator();
                    invalidateSelf();
                }
            }
        });

        contentPaint.setColor(backgroundColor);
        contentPaint.setStyle(Paint.Style.FILL);
        contentPaint.setStrokeCap(ROUND);
        contentPaint.setPathEffect(new CornerPathEffect(arrowRound));

        //求取边长
        arrowLen = Float.valueOf(String.valueOf(arrowHeight / Math.cos(Math.toRadians(60))));
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        arrowCenter.y = bounds.bottom;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (rv != null && rv.getLayoutManager() != null) {
            arrowPath.reset();
            final Rect bounds = getBounds();
            arrowPath.moveTo(bounds.left, bounds.top);
            arrowPath.lineTo(bounds.right, bounds.top);
            arrowPath.lineTo(bounds.right, bounds.bottom - arrowHeight);
            arrowPath.lineTo(arrowCenter.x + arrowLen / 2, bounds.bottom - arrowHeight);
            arrowPath.lineTo(arrowCenter.x, bounds.bottom - 2 * arrowHeight);
            arrowPath.lineTo(arrowCenter.x - arrowLen / 2, bounds.bottom - arrowHeight);
            arrowPath.lineTo(bounds.left, bounds.bottom - arrowHeight);
            arrowPath.lineTo(bounds.left, bounds.top);
            arrowPath.close();
            canvas.drawPath(arrowPath, contentPaint);
            canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.bottom - 2 * arrowHeight, contentPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        this.contentPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        contentPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public void setSelectPosition(int position) {
        if (this.selectPosition != position) {
            this.selectPosition = position;
            smoothScrollToPosition();
        }
    }

    private void smoothScrollToPosition() {
        if (rv != null && rv.getLayoutManager() != null) {
            final float oldX = arrowCenter.x;
            if (calculateArrowX()) {
                stopAnimator();
                valueAnimator = ObjectAnimator.ofFloat(oldX, arrowCenter.x);
                valueAnimator.setDuration(500L);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        arrowCenter.x = (float) animation.getAnimatedValue();
                        invalidateSelf();
                    }
                });
                valueAnimator.start();
            }
        }
    }

    private Boolean calculateArrowX() {
        final LinearLayoutManager manager = (LinearLayoutManager) rv.getLayoutManager();
        final int firstVisiblePosition = manager.findFirstVisibleItemPosition();
        final int lastVisiblePosition = manager.findLastVisibleItemPosition();
        if (selectPosition >= firstVisiblePosition && selectPosition <= lastVisiblePosition) {
            //获取当前选中的view
            final View child = rv.getChildAt(selectPosition - firstVisiblePosition);
            arrowCenter.x = (child.getRight() + child.getLeft()) / 2f;
            return true;
        }
        return false;
    }

    private void stopAnimator() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.end();
        }
    }

}
