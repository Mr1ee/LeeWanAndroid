package com.lee.leewanandroid.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.lee.leewanandroid.R;

/**
 * ViewFlipper 仿淘宝、京东滚动播放控件
 *
 * @author lee
 * @date 18/7/27
 */

public class ViewFlipper extends FrameLayout {

    private static final int DEFAULT_FLIP_DURATION = 500;

    private static final int DEFAULT_FLIP_INTERVAL = 2500;

    /**
     * 切换的时间间隔
     */
    private int mFlipInterval = DEFAULT_FLIP_INTERVAL;

    /**
     * 动画偏移量，涉及到View动画滚动距离，当onSizeChanged时候获取
     * （为什么不从onMeasure？是因为每次设置childView VISIBLE的时候都会触发重绘，每次都要执行onMeasure，感觉太频繁了）
     */
    private int mTranslationY;

    /**
     * view in animator
     */
    private ObjectAnimator mInAnimator;

    /**
     * view out animator
     */
    private ObjectAnimator mOutAnimator;

    /**
     * is the view visible or not.
     * default is false, changed when view visibility change. or
     * {@link ViewFlipper#onAttachedToWindow()}
     * {@link ViewFlipper#onDetachedFromWindow()}
     */
    private boolean mVisible = false;

    /**
     * is view begin start flipping or not
     * when call {@link ViewFlipper#startFlipping()} set mStart true.
     * when call {@link ViewFlipper#stopFlipping()} ()} set mStart false.
     */
    private boolean mStarted = false;

    /**
     * is flipper is running or not
     * determined by mVisible && mStarted
     */
    private boolean mRunning = false;

    /**
     * 当前view的位置
     */
    private int mWhichChild = 0;

    /**
     * 当前要显示的数据的位置
     */
    private int mPosition = 0;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    /**
     * 隐藏的View
     */
    private RecyclerView.ViewHolder shadowedVH;

    /**
     * 正在显示的View
     */
    private RecyclerView.ViewHolder showingVH;

    public ViewFlipper(Context context) {
        this(context, null);
    }

    public ViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    ///////////////////// public methods ///////////////////
    /////////////////////     below     ////////////////////

    /**
     * Start a timer to cycle through child views
     * without show animation.
     */
    public void startFlipping() {
        mStarted = true;
        updateRunning(false);
    }

    /**
     * stop flip
     */
    public void stopFlipping() {
        mStarted = false;
        updateRunning();
    }

    /**
     * How long to wait before flipping to the next view
     *
     * @param milliseconds times in milliseconds
     */
    public void setFlipInterval(int milliseconds) {
        this.mFlipInterval = milliseconds;
    }

    /**
     * set how long flipping in/out animation cost
     *
     * @param milliseconds times in milliseconds
     */
    public void setFlipDuration(int milliseconds) {
        mInAnimator.setDuration(milliseconds);
        mOutAnimator.setDuration(milliseconds);
    }

    @SuppressWarnings("unchecked cast, unused")
    public <VH extends RecyclerView.ViewHolder, T extends RecyclerView.Adapter<VH>> void setAdapter(T adapter) {
        this.mAdapter = (RecyclerView.Adapter<RecyclerView.ViewHolder>) adapter;
        showingVH = mAdapter.createViewHolder(this, 0);
        shadowedVH = mAdapter.createViewHolder(this, 0);

        //add child view to parent
        addView(showingVH.itemView);
        addView(shadowedVH.itemView);
        mAdapter.bindViewHolder(showingVH, 0);
        setDisplayedChild(0, false);
    }

    /**
     * init flipper animation and setting
     */
    private void init(Context context, AttributeSet attrs) {
        initAnimation();

        if (null != attrs) {
            /* get config from xml files */
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewFlipper);
            setFlipDuration(a.getInteger(R.styleable.ViewFlipper_flipDuration, DEFAULT_FLIP_DURATION));
            setFlipInterval(a.getInteger(R.styleable.ViewFlipper_flipInterval, DEFAULT_FLIP_INTERVAL));
            a.recycle();
        }
    }

    private void initAnimation() {
        mInAnimator = defaultInAnimator();
        mOutAnimator = defaultOutAnimator();
    }

    private ObjectAnimator defaultInAnimator() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(null, "translationY", mTranslationY, 0);
        anim.setDuration(DEFAULT_FLIP_DURATION);
        return anim;
    }

    private ObjectAnimator defaultOutAnimator() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(null, "translationY", 0, -mTranslationY);
        anim.setDuration(DEFAULT_FLIP_DURATION);
        return anim;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mTranslationY = getMeasuredHeight();
        mInAnimator.setFloatValues(mTranslationY, 0);
        mOutAnimator.setFloatValues(0, -mTranslationY);
    }

    /**
     * 更新view显示
     */
    private void updateRunning() {
        updateRunning(true);
    }

    /**
     * 在布局中只显示当前id的子view，其他view不显示
     *
     * @param childIndex 子view的index
     * @param animate    是否要显示动画
     */
    void showOnly(int childIndex, boolean animate) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final View preChild = getChildAt((i == 0) ? count - 1 : i - 1);
            if (i == childIndex) {
                if (animate && mInAnimator != null) {
                    mOutAnimator.setTarget(preChild);
                    mInAnimator.setTarget(child);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(mOutAnimator).with(mInAnimator);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            child.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            preChild.setVisibility(View.INVISIBLE);
                        }
                    });
                    animatorSet.start();
                } else {
                    //没有设置动画或者 animate为false的时候不展示动画
                    child.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * begin running
     *
     * @param flipNow animator or not
     */
    private void updateRunning(boolean flipNow) {
        boolean running = mVisible && mStarted;
        if (running != mRunning) {
            if (running) {
                showOnly(mWhichChild, flipNow);
                postDelayed(mFlipRunnable, mFlipInterval);
            } else {
                removeCallbacks(mFlipRunnable);
            }
            mRunning = running;
        }
    }

    /**
     * show next view
     */
    public void showNext() {
        // if the flipper is currently flipping automatically, and showNext() is called
        // we should we should make sure to reset the timer
        if (mRunning) {
            removeCallbacks(mFlipRunnable);
            postDelayed(mFlipRunnable, mFlipInterval);
        }
        //exchange shadowed view and showing view.
        RecyclerView.ViewHolder tmp = showingVH;
        showingVH = shadowedVH;
        shadowedVH = tmp;
        setDisplayedChild(mWhichChild + 1);
    }

    /**
     * set display view by id
     *
     * @param whichChild the display view id
     */
    private void setDisplayedChild(int whichChild) {
        setDisplayedChild(whichChild, true);
    }

    private void setDisplayedChild(int whichChild, boolean animate) {
        //cycle show
        mWhichChild = ((whichChild == getChildCount()) ? 0 : whichChild);
        mPosition = mPosition == mAdapter.getItemCount() - 1 ? 0 : mPosition + 1;
        mAdapter.bindViewHolder(showingVH, mPosition);

        boolean hasFocus = getFocusedChild() != null;
        // This will clear old focus if we had it
        showOnly(mWhichChild, animate);
        if (hasFocus) {
            // Try to retake focus if we had it
            requestFocus(FOCUS_FORWARD);
        }
    }

    private final Runnable mFlipRunnable = () -> {
        if (mRunning) {
            showNext();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mVisible = true;
        startFlipping();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;
        stopFlipping();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = (visibility == VISIBLE);
        //must call in this method.
        updateRunning(false);
    }
}