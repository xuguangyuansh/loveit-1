package cn.net.view.slidelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;

public class SlideLayout extends FrameLayout {

    private static final float MIN_FLING_VELOCITY = 400;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    private int mOverhangSize;
    private ViewDragHelper mDragHelper;
    private View leftView;
    private View centerView;
    private View rightView;
    private int mTouchSlop;

    public SlideLayout(Context context) {
        super(context);
        initView(null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        final float density = getContext().getResources().getDisplayMetrics().density;
        mOverhangSize = (int) (DEFAULT_OVERHANG_SIZE * density + 0.5f);

        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat());
        ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);


        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_RIGHT);
        mDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMeasureSpecMode != MeasureSpec.EXACTLY) {
            throw new IllegalArgumentException("width must be match or exactly");
        }
        if (heightMeasureSpecMode != MeasureSpec.EXACTLY) {
            throw new IllegalArgumentException("height must be match or exactly");
        }
        int layoutHeight = heightMeasureSpecSize - getPaddingTop() - getPaddingBottom();
        int layoutWidth = widthMeasureSpecSize - getPaddingLeft() - getPaddingRight();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(layoutWidth, widthMeasureSpecMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(layoutHeight, heightMeasureSpecMode);
        int childCount = getChildCount();
        if (childCount != 3) {
            throw new IllegalArgumentException("child must be three");
        }
        //从上到下, 最上面的是最底部的view，中间的是首页，右边的是上面的view
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.measure(widthMeasureSpec, heightMeasureSpec);
            if (i == 1) {
                this.centerView = childAt;
            } else if (i == 0) {
                this.leftView = childAt;
            } else {
                this.rightView = childAt;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (i == 2) {
                int childBottom = getPaddingTop() + childAt.getMeasuredHeight();
                childAt.layout(getMeasuredWidth(), getPaddingTop(), getMeasuredWidth() * 2, childBottom);
            } else {
                super.onLayout(changed, l, t, r, b);

            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    public void computeScroll() {
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        private int mLeft;
        private int mTop;
        private boolean isRightOpen;
        private boolean isLeftOpen;

        DragHelperCallback() {
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }


        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            Log.e("zzz", "onEdgeDragStarted");
            super.onEdgeDragStarted(edgeFlags, pointerId);
            if (mDragHelper!=null){
                if (mDragHelper.getViewDragState()==ViewDragHelper.STATE_SETTLING){
                    return;
                }
            }
            if (edgeFlags == ViewDragHelper.EDGE_LEFT) {
                if (isRightOpen) {
                    mDragHelper.captureChildView(rightView, pointerId);
                    Log.e("zz", "started 1111111");
                } else if (!isLeftOpen) {
                    mDragHelper.captureChildView(centerView, pointerId);
                    Log.e("zz", "started 2222222");

                }
            } else if (edgeFlags == ViewDragHelper.EDGE_RIGHT) {
                if (isLeftOpen) {
                    mDragHelper.captureChildView(centerView, pointerId);
                    Log.e("zz", "started 3333333333");

                } else if (!isRightOpen) {
                    mDragHelper.captureChildView(rightView, pointerId);
                    Log.e("zz", "started 4444444444");

                }
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            Log.e("zz","onViewDragStateChanged"+state);

            if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_SETTLING) {
//                if (mSlideOffset == 0) {
//                    updateObscuredViewsVisibility(mSlideableView);
//                    dispatchOnPanelClosed(mSlideableView);
//                    mPreservedOpenState = false;
//                } else {
//                    dispatchOnPanelOpened(mSlideableView);
//                    mPreservedOpenState = true;
//                }

            }
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            // Make all child views visible in preparation for sliding things around
            mLeft = capturedChild.getLeft();
            mTop = capturedChild.getTop();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.e("zzonViewPositionChanged", "onViewPositionChanged:left:" + left + "top:" + top + "dx:" + dx + "dy:" + dy);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.e("zzonViewReleased", "xvel:" + xvel + "..yvel:" + yvel+"。。。。"+releasedChild.getLeft());
            float movePrecent = releasedChild.getLeft() / (float) releasedChild.getWidth();
            Log.e("zz", movePrecent + "%");
            Log.e("zzcompare", (xvel >= 0 && movePrecent > 0.25f) + "");
            int finalLeft = (movePrecent > 0.25f) ? releasedChild.getWidth() : 0;

            Log.e("zzrelease1", finalLeft + "...");
            if (rightView == releasedChild) {
                if (isRightOpen) {
                    if ( movePrecent > 0.25f) {
                        Log.e("zz", "release rightView 111111" + movePrecent);
                        isRightOpen = false;
                        finalLeft = (movePrecent > 0.25f) ? getMeasuredWidth() : 0;
//                    isLeftOpen=false;
                    } else {
                        Log.e("zz", "release rightView 222222" + movePrecent);
                        finalLeft = (movePrecent < 0.25f) ? 0 : getMeasuredWidth();
                        isRightOpen = true;
                    }
                } else {
                    if (movePrecent < 0.75f) {
                        Log.e("zz", "release rightView 111111" + movePrecent);
                        isRightOpen = true;
                        finalLeft = (movePrecent < 0.75f) ? 0 : getMeasuredWidth();
//                    isLeftOpen=false;
                    } else if (movePrecent < 0.75f){
                        Log.e("zz", "release rightView 222222" + movePrecent);
                        finalLeft = ( movePrecent > 0.75f) ? getMeasuredWidth() : 0;
                        isRightOpen = false;
                    }
                }

            } else if (centerView == releasedChild) {
                if (!isLeftOpen&& movePrecent > 0.25f) {
                    Log.e("zz", "release centerView 11111" + movePrecent);
                    finalLeft = (movePrecent > 0.25f) ? releasedChild.getWidth() : 0;
                    isLeftOpen = true;
                } else if (isLeftOpen&&movePrecent > 0.75){
                    Log.e("zz", "release centerView 222222" + movePrecent);
                    finalLeft = (movePrecent > 0.75f) ? releasedChild.getWidth() : 0;
                    isLeftOpen = true;
                }else if (!isLeftOpen&&movePrecent < 0.25f){
                    finalLeft = (movePrecent < 0.25f) ? 0 : releasedChild.getWidth();
                    isLeftOpen = false;
                }else{
                    finalLeft = (movePrecent < 0.75f) ? 0 : releasedChild.getWidth();
                    isLeftOpen = false;
                }
            } else if (leftView == releasedChild) {
                Log.e("zz", "release leftView");
            }
            Log.e("zzisLeftOpen<", isLeftOpen + "");
            Log.e("zzisRightOpen<", isRightOpen + "");
            mDragHelper.settleCapturedViewAt(finalLeft, mTop);
            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == centerView) {
                if (dx>0) {
                    Log.e("zz","horizontal  11111111"+centerView.getLeft()+"...."+left);
                    int startBound = getPaddingLeft();
                    int endBound = startBound + getMeasuredWidth();
                    left = Math.min(Math.max(left, startBound), endBound);
                }else{
                    Log.e("zz","horizontal  11111fe111"+centerView.getLeft()+"...."+left);
                    int startBound = getPaddingLeft();
                    int endBound = startBound + getMeasuredWidth();
                    left = Math.min(Math.max(left, startBound), endBound);
                }
                Log.e("zz","horizontal final 11111111"+centerView.getLeft()+"...."+left);

            } else if (child == rightView) {
                if (isRightOpen) {
                    Log.e("zz","horizontal  22222222222"+centerView.getLeft()+"...."+left);
                    int startBound = getPaddingLeft();
                    int endBound = startBound + getMeasuredWidth();
                    left = Math.min(Math.max(left, startBound), endBound);
                    Log.e("zz","horizontal final 22222222222"+centerView.getLeft()+"...."+left);

                }
            }
            Log.e("zzz", "clampViewPositionHorizontal"+child.getLeft()+"....." + left + "...." + dx);
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // Make sure we never move views vertically.
            // This could happen if the child has less height than its parent.
            return child.getTop();
        }

    }


}
