package ru.dimasokol.currencies.demo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * <p></p>
 *
 * @author sokol @ 27.06.16
 */
public class ProgressToOkayDrawable extends Drawable implements Animatable {

    private static final int ANIMATION_DURATION = 1332;
    private static final float REPEATS_PER_CYCLE = 3;
    private static final float MAX_SWEEP = 280f;

    // M21,7 L9,19 L3.5,13.5 L4.91,12.09 L9,16.17 L19.59,5.59 L21,7 Z
    private static final PointF[] CHECKMARK_STOPS = new PointF[] {
            new PointF(0.125f, 0.55f),
            new PointF(0.37f, 0.79f),
            new PointF(0.87f, 0.29f)
    };

    private final Paint mLinePaint;
    private float mStrokeWidth;
    private View mParent;

    private final Interpolator mLinearInterpolator = new LinearInterpolator();
    private final Interpolator mMaterialInterpolator = new FastOutSlowInInterpolator();

    private float mCurrentAngle = 0f;
    private float mStartAngle = 0f;
    private float mSweepAngle = 8f;

    private Animation mAnimation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mCurrentAngle = (360f * interpolatedTime);

            long rawCurrentTime = (long) (interpolatedTime * ANIMATION_DURATION);
            long rawStepDuration = (long) (ANIMATION_DURATION / (REPEATS_PER_CYCLE * 2));
            long rawStepTime = rawCurrentTime % rawStepDuration;

            float subTime = (float) rawStepTime / (float) rawStepDuration;
            subTime = mMaterialInterpolator.getInterpolation(subTime);

            boolean expanding = ((rawCurrentTime / rawStepDuration) % 2) == 0;

            // На такую величину всего должна дополнительно сместиться дуга
            float offset = 360f - MAX_SWEEP;

            mStartAngle = expanding?
                    mCurrentAngle + ((offset / 2) * subTime)
                    :
                    mCurrentAngle + ((offset / 2) * subTime) + (MAX_SWEEP * subTime) + (offset / 2);

            mSweepAngle = expanding? MAX_SWEEP * subTime : MAX_SWEEP * (1f - subTime);
            mSweepAngle = Math.max(mSweepAngle, getMinSweep());

            invalidateSelf();
        }
    };

    public ProgressToOkayDrawable(View parent, float strokeWidth) {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(strokeWidth);
        mLinePaint.setStrokeJoin(Paint.Join.MITER);
        mLinePaint.setStrokeCap(Paint.Cap.SQUARE);

        mStrokeWidth = strokeWidth;
        mParent = parent;
    }

    @Override
    public void draw(Canvas canvas) {
        RectF full = new RectF(mStrokeWidth, mStrokeWidth, canvas.getWidth() - mStrokeWidth, canvas.getHeight() - mStrokeWidth);
        Path check = new Path();

//        for (int i = 0; i < CHECKMARK_STOPS.length; i++) {
//            PointF point = CHECKMARK_STOPS[i];
//            float startX = ((float) canvas.getWidth()) * point.x;
//            float startY = ((float) canvas.getHeight()) * point.y;
//
//            if (i == 0)
//                check.moveTo(startX, startY);
//            else
//                check.lineTo(startX, startY);
//        }

        check.addArc(full, mStartAngle, mSweepAngle);
        canvas.drawPath(check, mLinePaint);


    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(mLinearInterpolator);
        mAnimation.setDuration(ANIMATION_DURATION * 3);
        mParent.startAnimation(mAnimation);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    private float getMinSweep() {
        return 4f;
    }
}
