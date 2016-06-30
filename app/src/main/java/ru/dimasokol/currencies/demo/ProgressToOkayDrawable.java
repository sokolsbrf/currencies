package ru.dimasokol.currencies.demo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Animation;
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
    private static final int CIRCLE_DURATION = ANIMATION_DURATION / 2;

    private static final float REPEATS_PER_CYCLE = 3;
    private static final float MAX_SWEEP = 280f;

    private static final float CHECKMARK_END_ANGLE = 180f;

    private enum Mode {
        Rotate,
        RunningToOkay,
        RunningToFail,
        Okay,
        Fail
    }

    // M21,7 L9,19 L3.5,13.5 L4.91,12.09 L9,16.17 L19.59,5.59 L21,7 Z
    private static final PointF[] CHECKMARK_STOPS = new PointF[] {
            new PointF(0.125f, 0.55f),
            new PointF(0.37f, 0.79f),
            new PointF(0.87f, 0.29f)
    };

    private final Paint mLinePaint;
    private float mStrokeWidth;
    private View mParent;

    private Mode mMode = Mode.Rotate;

    private final Interpolator mLinearInterpolator = new LinearInterpolator();
    private final Interpolator mMaterialInterpolator = new FastOutSlowInInterpolator();
    private final Interpolator mMaterialCircleInterpolator = new LinearOutSlowInInterpolator();

    // Для анимации обычного прогресса
    private float mCurrentAngle = 0f;
    private float mStartAngle = 0f;
    private float mSweepAngle = 8f;

    // Для анимации заполняющегося кружочка
    private float mInitialSweepAngle = 0f;
    private float mInitialStartAngle = 0f;

    // Для анимации рисования галочки
    private float mCirclePart = 0f;
    private float mIconPart = 0f;

    private Animation mProgressAnimation = new Animation() {
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

    private Animation mFullCircleAnimation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mIconPart = 0f;
            mStartAngle = mInitialStartAngle + (MAX_SWEEP * interpolatedTime);
            mSweepAngle = mInitialSweepAngle + ((360f - mInitialSweepAngle) * interpolatedTime);
            invalidateSelf();
        }
    };

    private Animation.AnimationListener mSwitchToCheckListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mCheckAnimation.setDuration(CIRCLE_DURATION);
            mCheckAnimation.setRepeatCount(0);
            mCheckAnimation.setInterpolator(mMaterialInterpolator);
            mParent.startAnimation(mCheckAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Animation mCheckAnimation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mStartAngle = CHECKMARK_END_ANGLE;
            mSweepAngle = 360f * (1f - interpolatedTime);

            mIconPart = interpolatedTime;

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
        Path path = new Path();

//        for (int i = 0; i < CHECKMARK_STOPS.length; i++) {
//            PointF point = CHECKMARK_STOPS[i];
//            float startX = ((float) canvas.getWidth()) * point.x;
//            float startY = ((float) canvas.getHeight()) * point.y;
//
//            if (i == 0)
//                path.moveTo(startX, startY);
//            else
//                path.lineTo(startX, startY);
//        }

        if (mIconPart > 0f) {
            float startX = ((float) canvas.getWidth()) * CHECKMARK_STOPS[0].x;
            float startY = ((float) canvas.getHeight()) * CHECKMARK_STOPS[0].y;
            path.moveTo(startX, startY);

            float endX = ((float) canvas.getWidth()) * CHECKMARK_STOPS[1].x;
            float endY = ((float) canvas.getHeight()) * CHECKMARK_STOPS[1].y;

            if (mIconPart < 0.5f) {
                float part = mIconPart * 2f;

                float x = startX + ((endX - startX) * part);
                float y = startY + ((endY - startY) * part);

                path.lineTo(x, y);
            } else {
                path.lineTo(endX, endY);

                float part = (mIconPart - 0.5f) * 2f;
                endX = ((float) canvas.getWidth()) * CHECKMARK_STOPS[2].x;
                endY = ((float) canvas.getHeight()) * CHECKMARK_STOPS[2].y;

                float x = startX + ((endX - startX) * part);
                float y = startY + ((endY - startY) * part);

                path.lineTo(x, y);
            }
        }

        path.addArc(full, mStartAngle, mSweepAngle);
        canvas.drawPath(path, mLinePaint);
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
        mProgressAnimation.setRepeatCount(Animation.INFINITE);
        mProgressAnimation.setRepeatMode(Animation.RESTART);
        mProgressAnimation.setInterpolator(mLinearInterpolator);
        mProgressAnimation.setDuration(ANIMATION_DURATION * 3);
        mParent.startAnimation(mProgressAnimation);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    public void okay() {
        mMode = Mode.RunningToOkay;

        mProgressAnimation.cancel();
        mFullCircleAnimation.setRepeatCount(0);
        mFullCircleAnimation.setInterpolator(mMaterialCircleInterpolator);

        mInitialSweepAngle = mSweepAngle;
        mInitialStartAngle = mStartAngle;

        mFullCircleAnimation.setDuration(CIRCLE_DURATION);
        mFullCircleAnimation.setAnimationListener(mSwitchToCheckListener);
        mParent.startAnimation(mFullCircleAnimation);
    }

    public void fail() {

    }

    private float getMinSweep() {
        return 4f;
    }
}
