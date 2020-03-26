package cn.net.view.slidelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;

public class SlideLayout2 extends FrameLayout {

    private static final float MIN_FLING_VELOCITY = 400;
    private float startX;
    private float startY;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    private int mOverhangSize;
    private ViewDragHelper mLeftDragHelper;
    private ViewDragHelper mDragHelper;
    private ViewDragHelper mRightDragHelper;
    private View leftView;
    private View centerView;
    private View rightView;
    private int mTouchSlop;
    private boolean isRightScroll;
    private boolean isLeftScroll;

    public SlideLayout2(Context context) {
        super(context);
        initView(null);
    }

    public SlideLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public SlideLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        DragCallBack leftDragCallBack = new DragCallBack(Gravity.LEFT);
        mLeftDragHelper = ViewDragHelper.create(this, 1.0f, leftDragCallBack);
        mLeftDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_RIGHT);
        mLeftDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);
        leftDragCallBack.setDragger(mLeftDragHelper);

//        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
//        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_RIGHT);
//        mDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);
//
//        DragCallBack rightDragCallBack = new DragCallBack(Gravity.RIGHT);
//        mRightDragHelper = ViewDragHelper.create(this, 1.0f,rightDragCallBack );
//        mRightDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
//        mRightDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);
//        rightDragCallBack.setDragger(mRightDragHelper);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mLeftDragHelper!=null){
            mLeftDragHelper.abort();
            mLeftDragHelper.cancel();
        }
        if (mRightDragHelper!=null){
            mRightDragHelper.abort();
            mRightDragHelper.cancel();
        }
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
//        mDragHelper.shouldInterceptTouchEvent(ev);
//        return super.onInterceptTouchEvent(ev);
        Log.e("zz","onInterceptTouchEvent"+ev.getAction());

        return mLeftDragHelper.shouldInterceptTouchEvent(ev)/*|mRightDragHelper.shouldInterceptTouchEvent(ev)*/;
//        return  mDragHelper.shouldInterceptTouchEvent(ev);
//        int action = ev.getActionMasked();
//        boolean interceptForDrag = this.mLeftDragHelper.shouldInterceptTouchEvent(ev) | this.mRightDragHelper.shouldInterceptTouchEvent(ev);
//        boolean interceptForTap = false;
//        switch(action) {
//            case 0:
//                float x = ev.getX();
//                float y = ev.getY();
//                this.startX = x;
//                this.startY = y;
//                if (this.mScrimOpacity > 0.0F) {
//                    View child = this.mLeftDragger.findTopChildUnder((int)x, (int)y);
//                    if (child != null && this.isContentView(child)) {
//                        interceptForTap = true;
//                    }
//                }
//
//                this.mDisallowInterceptRequested = false;
//                this.mChildrenCanceledTouch = false;
//                break;
//            case 1:
//            case 3:
//                this.closeDrawers(true);
//                this.mDisallowInterceptRequested = false;
//                this.mChildrenCanceledTouch = false;
//                break;
//            case 2:
//                if (this.mLeftDragger.checkTouchSlop(3)) {
//                    this.mLeftCallback.removeCallbacks();
//                    this.mRightCallback.removeCallbacks();
//                }
//        }
//
//        return interceptForDrag || interceptForTap || this.hasPeekingDrawer() || this.mChildrenCanceledTouch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("zz","onTouchEvent");
//        mDragHelper.processTouchEvent(event);
//        mRightDragHelper.processTouchEvent(event);
        mLeftDragHelper.processTouchEvent(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = event.getX();
//                startY = event.getY();
//                Log.e("zz", "down");
//                isLeftScroll=false;
//                isRightScroll=false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("zz","move");
//                float moveX = event.getX();
//                float moveY = event.getY();
//                float dx = moveX - startX;
//                float dy = moveY - startY;
//
//                float absdx = Math.abs(dx);
//                float absdy = Math.abs(dy);
//                if (absdx > absdy&&absdx>mTouchSlop) {
//
//                    if (dx > 0) {
//                        Log.e("zz", "move" + dx + "..." + dy);
//                        isRightScroll=true;
//                    } else if (dx < 0) {
//                        Log.e("zz", "zuo hua");
//                        isLeftScroll=true;
//                    }
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return true;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                startX= (int) ev.getX();
//                startY= (int) ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
////                Log.e("zz","ss"+ev.getX()+ev.getY());
//                float moveX = ev.getX();
//                float moveY = ev.getY();
//                float dx=moveX-startX;
//                float dy=moveY-startY;
//                if (Math.abs(dx)>Math.abs(dy)&&dx>mTouchSlop){
//                    return true;
//                }
//                break;
//        }
//
//        return super.dispatchTouchEvent(ev);
//    }


    @Override
    public void computeScroll() {
        if (mLeftDragHelper != null && mLeftDragHelper.continueSettling(true)) {
            invalidate();
        }
        if (mRightDragHelper != null && mRightDragHelper.continueSettling(true)) {
            invalidate();
        }
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
//    private class RightDragCallBack extends ViewDragHelper.Callback{
//
//        private int right;
//
//        public RightDragCallBack(int right) {
//            this.right=right;
//        }
//
//        @Override
//        public boolean tryCaptureView(@NonNull View view, int i) {
//            return view==rightView||view==centerView;
//        }
//
//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return left;
//        }
//
//        @Override
//        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
//            return child.getTop();
//        }
//
//        @Override
//        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
//            if (capturedChild==centerView){
//                Log.e("zz","onViewCaptured");
//                mRightDragHelper.captureChildView(rightView,activePointerId);
//            }else if (capturedChild==rightView){
//                mRightDragHelper.captureChildView(rightView,activePointerId);
//            }
//        }
//
//
//    }

    public static int LEFT=0;
    public static int RIGHT=1;
    public static int CENTER=2;
    private class DragCallBack extends ViewDragHelper.Callback{

        private int gravity;
        private boolean isOpenLeft;
        private boolean isOpenRight;
        private ViewDragHelper mDragger;
        private String onViewCaptured="";
        private int startView=-1;
        private boolean isLeftScroll;

        public DragCallBack(int gravity) {
            this.gravity=gravity;
        }

        public void setDragger(ViewDragHelper dragger) {
            this.mDragger = dragger;
        }

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return view==leftView||view==centerView||view==rightView;
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
//            Log.e("zz","iscenter"+(capturedChild==centerView)+(this.gravity==Gravity.LEFT)+",,,"+ mLeftDragHelper.isEdgeTouched(ViewDragHelper.EDGE_RIGHT));
            Log.e("zz","onViewCaptured"+startView+isLeftScroll+"..");
            if (capturedChild==leftView){
                this.onViewCaptured="left";
                mLeftDragHelper.captureChildView(centerView,activePointerId);
            }else if (capturedChild==rightView){
//                mRightDragHelper.captureChildView(rightView,activePointerId);
            }
            else if (capturedChild==centerView){
//                if (!isOpenLeft)
//                    this.onViewCaptured="center";
                //leftview是否打开
                //左边还是右边
                if (isLeftScroll){
                    mLeftDragHelper.captureChildView(rightView,activePointerId);
                }
//                if (gravity==Gravity.LEFT){
////                    mLeftDragHelper.captureChildView(centerView,activePointerId);
//                }else{
//                    mLeftDragHelper.captureChildView(rightView,activePointerId);
//                    Log.e("zz","onViewCapturedRight");
//                }
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);

        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Log.e("zz","onEdgeTouched");
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            Log.e("zz","onEdgeDragStarted");

        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
//            Log.e("zz",left+"..."+dx);
//            if (changedView==centerView&&!isOpenLeft&&dx<0&&onViewCaptured.equals("center")){
//                mLeftDragHelper.captureChildView(rightView,0);
//            }
//            Log.e("zz",onViewCaptured);
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return child.getTop();
        }
//
//        @Override
//        public void onViewStart(View toCapture, int pointerId, boolean isLeftScroll) {
//            if (toCapture==leftView){
//                startView=0;
//            }else if (toCapture==centerView){
//                startView=2;
//            }else if (toCapture==rightView){
//                startView=1;
//            }
//            this.isLeftScroll=isLeftScroll;
//        }


        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            isLeftScroll=false;
            if (releasedChild==centerView){
                if (!isOpenLeft&&releasedChild.getLeft()>releasedChild.getMeasuredWidth()/4){
//                    mDragHelper.settleCapturedViewAt(releasedChild.getMeasuredWidth(), 0);
                    mLeftDragHelper.smoothSlideViewTo(centerView,releasedChild.getMeasuredWidth(),0);
                    isOpenLeft=true;
                    Log.e("zz","onViewReleased>1/2");
                }else if (releasedChild.getLeft()<releasedChild.getMeasuredWidth()-releasedChild.getMeasuredWidth()/4){
//                    mDragHelper.settleCapturedViewAt(0, 0);
//                    if (isOpenLeft) {
                        mLeftDragHelper.smoothSlideViewTo(centerView, 0, 0);
                        isOpenLeft = false;
//                    }
//                    else{
//                        mRightDragHelper.smoothSlideViewTo(rightView,rightView.getMeasuredWidth(),0);
//                        isOpenRight=false;
//                    }
                    Log.e("zz","onViewReleased<1/2");
                }
                invalidate();
            }else if (releasedChild==rightView){
                if (releasedChild.getLeft()<releasedChild.getMeasuredWidth()/2){
                    Log.e("zzright","onViewReleased<1/2");
                    mLeftDragHelper.smoothSlideViewTo(rightView,0,0);
                    isOpenRight=true;
                }else{
                    mLeftDragHelper.smoothSlideViewTo(rightView,rightView.getMeasuredWidth(),0);
                    isOpenRight=false;
                    Log.e("zzright","onViewReleased>1/2");
                }
            }
        }
    }

}
