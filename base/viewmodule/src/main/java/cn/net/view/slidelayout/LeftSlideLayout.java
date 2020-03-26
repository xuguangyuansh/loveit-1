package cn.net.view.slidelayout;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;

import cn.net.view.viewpager.NoScrollViewPager;

public class LeftSlideLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;
    private static final float MIN_FLING_VELOCITY = 400;
    private float downX;
    private float downY;
    private int dealtX;
    private int dealtY;
    private int lastX;
    private int lastY;
    private boolean isLeftOpen;
    private SlideTouchListener listener;
    private int touchSlop;
    private static final int DEFAULT_GUTTER_SIZE = 16; // dips
    private int mDefaultGutterSize;
    private int mGutterSize;


    public LeftSlideLayout(Context context) {
        super(context);
        init();
    }

    public LeftSlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeftSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("zz", "onMeasure");
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
        if (childCount != 2) {
            throw new IllegalArgumentException("child must be two");
        }
        //从上到下, 最上面的是最底部的view，中间的是首页，右边的是上面的view
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.measure(widthMeasureSpec, heightMeasureSpec);
        }
        final int maxGutterSize = layoutWidth / 10;
        mGutterSize = Math.min(maxGutterSize, mDefaultGutterSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        final float density = getContext().getResources().getDisplayMetrics().density;

        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat());
        ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mDefaultGutterSize = (int) (DEFAULT_GUTTER_SIZE * density);

        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyFrameDragCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
        mDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (isLeftOpen){
        }else {
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    private boolean isGutterDrag(float x, float dx) {
        return (x < mGutterSize && dx > 0) || (x > getWidth() - mGutterSize && dx < 0);
    }

    private int dx;
    private int moveX;
    private int moveY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
//                requestParentDisallowInterceptTouchEvent(true);
                dx = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                dx += (moveX - downX);
                float detalX = ev.getX() - downX;
                float detalY = ev.getY() - downY;
                if (dx > 0 && Math.abs(detalX) > Math.abs(detalY)) {
                    intercept = true;
                    Log.e("zz", "dx=" + dx);
                    mDragHelper.tryCaptureViewForDrag(getChildAt(0), ev.getPointerId(0));
//                    requestParentDisallowInterceptTouchEvent(true);
                    if (dx != 0 &&
                            canLeftScroll(this, false, (int) dx, (int) moveX, (int) moveY)) {
                        // Nested view has scrollable area under this point. Let it be handled there.
                        intercept = false;
                        Log.e("zz", "canLeftScroll=");
                    }
                } else {
                    if (isLeftOpen) {
                        if (Math.abs(dx) < touchSlop) {
                            intercept = false;
                        } else {
                            intercept = true;
                        }
//                        requestParentDisallowInterceptTouchEvent(true);
                        if (dx != 0 &&
                                canScroll(this, false, (int) dx, (int) moveX, (int) moveY)) {
                            // Nested view has scrollable area under this point. Let it be handled there.
                            intercept = false;
                            Log.e("zz", "canScroll=" + dx + ".." + touchSlop);
                        }
                    } else {
                        intercept = false;
                    }
                }

                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        boolean interceptTouchEvent = mDragHelper.shouldInterceptTouchEvent(ev);
        boolean onInterceptTouchEvent = super.onInterceptTouchEvent(ev);
        return intercept;
    }


    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }


    public boolean canScrollHorizontally(int direction) {
        if (getChildCount() == 0) {
            return false;
        }

        final int width = getPaddedWidth();
        final int scrollX = getScrollX();
        if (direction < 0) {
            return (scrollX > 0);
        } else if (direction > 0) {
            return (scrollX < getMeasuredWidth());
        } else {
            return false;
        }
    }

    protected boolean canLeftScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                if (group instanceof LeftSlideLayout) {
                    if (i == 0) {
                        continue;
                    }
                }
                // TODO: Add support for transformed views.
                final View child = group.getChildAt(i);
//                Log.e("zzcanLeftScroll","scrollX="+scrollX+"..left="+child.getLeft()+"...right"+child.getRight()+"....."+canScroll(child, true, dx, x + scrollX - child.getLeft(),
//                        y + scrollY - child.getTop()));
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()
                        && canScroll(child, true, dx, x + scrollX - child.getLeft(),
                        y + scrollY - child.getTop())) {
//                    Log.e("zzcanLeftScroll","ViewGroup");
                    return true;
                }
            }
        }
//        Log.e("zzcanLeftScroll","checkV"+checkV+"...."+ v.canScrollHorizontally(-dx));
        return checkV && v.canScrollHorizontally(-dx);
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                // TODO: Add support for transformed views.
                if (group instanceof LeftSlideLayout) {
                    if (i == 1) {
                        continue;
                    }
                }
                final View child = group.getChildAt(i);
//                Log.e("zzcanScroll","scrollX="+scrollX+"..left="+child.getLeft()+"...right"+child.getRight()+"....."+canScroll(child, true, dx, x + scrollX - child.getLeft(),
//                        y + scrollY - child.getTop()));
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()
                        && canScroll(child, true, dx, x + scrollX - child.getLeft(),
                        y + scrollY - child.getTop())) {
//                    Log.e("zzcanScroll","ViewGroup");
                    return true;
                }
            }
        }
//        Log.e("zzcanScroll","checkV"+checkV+"...."+ v.canScrollHorizontally(-dx));
        return checkV && v.canScrollHorizontally(-dx);
    }

    private int getPaddedWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public void computeScroll() {
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    public class MyFrameDragCallBack extends ViewDragHelper.Callback {

        private int mTop;
        private int mLeft;
        private int dxTotal;

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (lastState == ViewDragHelper.STATE_SETTLING) {
                return false;
            }
            return true;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            mTop = capturedChild.getTop();
            mLeft = capturedChild.getLeft();
            if (capturedChild == getChildAt(0)) {
                mDragHelper.captureChildView(getChildAt(1), activePointerId);
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            dxTotal = 0;
            float movePrecent = releasedChild.getLeft() / (float) releasedChild.getWidth();
            int finalLeft = (movePrecent > 0.25f) ? releasedChild.getWidth() : 0;
            if (!isLeftOpen && movePrecent > 0.25f) {
                finalLeft = (movePrecent > 0.25f) ? releasedChild.getWidth() : 0;
                isLeftOpen = true;
                if (listener != null)
                    listener.onOpen();
//                FrameLayout.LayoutParams layoutParams = (LayoutParams) releasedChild.getLayoutParams();
//                layoutParams.leftMargin=getMeasuredHeight();
//                releasedChild.setLayoutParams(layoutParams);
            } else if (!isLeftOpen && movePrecent < 0.25f) {
                finalLeft = (movePrecent < 0.25f) ? 0 : releasedChild.getWidth();
                isLeftOpen = false;
            } else if (isLeftOpen && movePrecent > 0.75) {
                finalLeft = (movePrecent > 0.75f) ? releasedChild.getWidth() : 0;
                isLeftOpen = true;
            } else {
                finalLeft = (movePrecent < 0.75f) ? 0 : releasedChild.getWidth();
                isLeftOpen = false;
                if (listener != null)
                    listener.onClose();
//                FrameLayout.LayoutParams layoutParams = (LayoutParams) releasedChild.getLayoutParams();
//                layoutParams.leftMargin=0;
//                releasedChild.setLayoutParams(layoutParams);
            }
            mDragHelper.settleCapturedViewAt(finalLeft, mTop);
            invalidate();
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            dxTotal += dx;
            if (isLeftOpen) {
                if (dxTotal >= 0) {
                    dxTotal -= dx;
                    return getMeasuredWidth();
                }
            }
            if (!isLeftOpen) {
                if (dxTotal <= 0) {
                    dxTotal -= dx;
                    return 0;
                }
            }

            int startBound = getPaddingLeft();
            int endBound = startBound + getMeasuredWidth();
            left = Math.min(Math.max(left, startBound), endBound);
            return left;
        }

        private int lastState = 0;

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            lastState = state;
        }
    }

    public void openSlide() {
        mDragHelper.settleCapturedViewAt(getMeasuredWidth(), 0);
        invalidate();
    }

    public void closeSlide() {
        mDragHelper.settleCapturedViewAt(0, 0);
        invalidate();
    }

    public void setSlideTouchListener(SlideTouchListener listener) {
        this.listener = listener;
    }
}
