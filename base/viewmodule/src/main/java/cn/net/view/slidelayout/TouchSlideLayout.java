package cn.net.view.slidelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;

public class TouchSlideLayout extends FrameLayout {
    private View centerView;
    private View leftView;
    private View rightView;
    private int mTouchSlop;
    private float mDownX;
    private float mDownY;
    private OverScroller mScroller;
    private View touchView=null;

    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            --t;
            return t * t * t * t * t + 1.0F;
        }
    };
    private GestureDetector detector;
    private float mLastMotionX;
    private int mTouchState = TOUCH_STATE_REST;
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;
    private int move;
    private int up_excess_move;
    private int down_excess_move;
    private VelocityTracker mVelocityTracker;
    private boolean isLeftOpen;
    private boolean isRightOpen;
    private float moveX;
    private float dx;

    public static int SCROLL_NONE=0;
    public static int SCROLL_MAIN_LEFT=1;
    public static int SCROLL_MAIN_RIGHT=2;
    public static int SCROLL_LEFT_RIGHT=3;
    public static int SCROLL_RIGHT_LEFT=4;
    private int scrollDirect=SCROLL_NONE;

    public TouchSlideLayout(Context context) {
        super(context);
        initView(null);
    }

    public TouchSlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public TouchSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat());
        ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);

        this.mScroller = new OverScroller(getContext(), sInterpolator);
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
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean interceptd = false;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }

        this.mVelocityTracker.addMovement(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                interceptd = false;
                float x = event.getX();
                float y = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动距离 判定是否滑动
                float dx = event.getX() - mDownX;
                float dy = event.getY() - mDownY;

                if (Math.abs(dx) > mTouchSlop) {
                    interceptd = true;
                } else {
                    interceptd = false;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return interceptd;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                scrollDirect=SCROLL_NONE;
                touchView=null;
                break;
            case MotionEvent.ACTION_MOVE:

                if (mDownX >= 0
//                        && mDownY >= mRootTopY
                        && mDownX <= getMeasuredWidth()
                    /*&& mDownY <= (mRootMeasuredHeight + mRootTopY)*/) {
                    moveX = event.getX();
                    dx = event.getX() - mDownX;
                    float dy = event.getY() - mDownY;

                    if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > mTouchSlop) {

                        float ownX = getX();
                        //获取手指按下的距离与控件本身Y轴的距离
                        float ownY = getY();
                        //理论中X轴拖动的距离
//                        float endX = ownX + dx;
                        float endX = dx;
                        //理论中Y轴拖动的距离
                        float endY = ownY + dy;
                        //X轴可以拖动的最大距离
                        float maxX = getMeasuredWidth();
                        //Y轴可以拖动的最大距离
                        float maxY = 0;
                        //X轴边界限制
                        endX = endX < 0 ? (endX>-maxX?endX:-maxX) : (endX > maxX ? maxX : endX);
                        View topChildUnder = findTopChildUnder((int) event.getX(), (int) event.getY());
                        if (topChildUnder != null) {
                            if (topChildUnder == centerView&&(touchView==null||touchView==centerView)) {
                                touchView=centerView;
                                if (scrollDirect==SCROLL_MAIN_RIGHT){
                                    setScrollDirect(SCROLL_MAIN_RIGHT);
                                    ViewCompat.offsetLeftAndRight(topChildUnder, (int) dx);
                                    Log.e("zz","move1111111111");
                                }else if (scrollDirect==SCROLL_MAIN_LEFT){
                                    setScrollDirect(SCROLL_MAIN_LEFT);
                                    ViewCompat.offsetLeftAndRight(rightView, (int) dx);
                                    Log.e("zz","move2222222222");
                                }else if (scrollDirect==SCROLL_NONE){
                                    if (dx > 0) {
                                        setScrollDirect(SCROLL_MAIN_RIGHT);
                                        ViewCompat.offsetLeftAndRight(topChildUnder, (int) dx);
                                        Log.e("zz","move3333333333");
                                    } else {
                                        setScrollDirect(SCROLL_MAIN_LEFT);
                                        ViewCompat.offsetLeftAndRight(rightView, (int) dx);
                                        Log.e("zz","move4444444444");
                                    }
                                }
                            } else if (topChildUnder == leftView&&(touchView==null||touchView==leftView)) {
                                touchView=leftView;
                                if (touchView==leftView&&leftView.getLeft()==0&&dx>0){
                                    dx=0;
                                }
                                if (scrollDirect==SCROLL_LEFT_RIGHT){
                                    setScrollDirect(SCROLL_LEFT_RIGHT);
                                    ViewCompat.offsetLeftAndRight(centerView, (int) dx);
                                    Log.e("zz","move55555555555");
                                }else if (scrollDirect==SCROLL_NONE){
                                    if (dx <= 0) {
                                        setScrollDirect(SCROLL_LEFT_RIGHT);
                                        ViewCompat.offsetLeftAndRight(centerView, (int) dx);
                                        Log.e("zz","move66666666");
                                    }
                                }
                            }else if (topChildUnder==rightView&&(touchView==null||touchView==rightView)){
                                touchView=rightView;
                                Log.e("zz",rightView.getLeft()+"rightview move");
                                if (rightView.getLeft()==0&&dx<=0){
                                    dx=0;
                                }else if (rightView.getLeft()+dx<0&&dx<0){
                                    dx=-getMeasuredWidth()+rightView.getLeft();
                                }
                                if (scrollDirect==SCROLL_RIGHT_LEFT){
                                    setScrollDirect(SCROLL_RIGHT_LEFT);
                                    ViewCompat.offsetLeftAndRight(rightView, (int)dx);
                                    Log.e("zz","move77777777");
                                }else if (scrollDirect==SCROLL_NONE){
                                    if (dx >= 0) {
                                        setScrollDirect(SCROLL_RIGHT_LEFT);
                                        ViewCompat.offsetLeftAndRight(rightView, (int) dx);
                                        Log.e("zz","move888888888");
                                    }
                                }
                            }else{
                                Log.e("zz","move99   scrolldirect"+scrollDirect);
                                if (scrollDirect==SCROLL_MAIN_RIGHT){
                                    touchView=centerView;
                                }else if (scrollDirect==SCROLL_RIGHT_LEFT||scrollDirect==SCROLL_MAIN_LEFT){
                                    touchView=rightView;
                                }else if (scrollDirect==SCROLL_LEFT_RIGHT){
                                    touchView=leftView;
                                }
                                if (touchView==leftView&&leftView.getLeft()==0&&dx>0){
                                    dx=0;
                                    Log.e("zz","move99999   dx"+dx+"...left"+touchView.getLeft());
                                }

                                if (touchView==rightView&&rightView.getLeft()==0&&dx<=0){
                                    dx=0;
                                    Log.e("zz","move99   dx"+dx+"...left"+touchView.getLeft());
                                }else if (touchView==rightView&&rightView.getLeft()+dx<0&&dx<0){
                                    Log.e("zz","move999   dx"+dx+"...left"+touchView.getLeft());
                                    dx=-getMeasuredWidth()+rightView.getLeft();
                                    Log.e("zz","move999   dx"+dx);
                                }/*else if (touchView==rightView&&rightView.getLeft()+dx>0&&dx<0){
                                    Log.e("zz","move99   dx"+dx+"...left"+touchView.getLeft());
//                                    dx=-rightView.getLeft();
                                }else{
                                    Log.e("zz","move99999   dx"+dx+"...left"+touchView.getLeft());
                                }*/
                                ViewCompat.offsetLeftAndRight(touchView, (int) dx);
                                Log.e("zz","move99999999"+(touchView==rightView));
                            }
                        }
                        Log.e("zz", dx + "endxleft");
                        mDownX = event.getX();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                View topChildUnder = findTopChildUnder((int) event.getX(), (int) event.getY());
                dx=0;
                if (topChildUnder == null) {
                    break;
                }
                float dx=event.getX() - mDownX;
                Log.e("zz","up dx"+dx);
                int left = topChildUnder.getLeft();
                Log.e("zz","left scrollview dircet"+scrollDirect);
                if (/*topChildUnder == centerView&&*/(scrollDirect==SCROLL_MAIN_RIGHT||scrollDirect==SCROLL_MAIN_LEFT)) {
                        //要把左侧的view滑出来
                    Log.e("zz","centerview");
                    if (scrollDirect==SCROLL_MAIN_LEFT){
                        left=rightView.getLeft();
                        Log.e("zz","left:"+left+"width:"+(getMeasuredWidth()-getMeasuredWidth() / 4));
                        if (left > (getMeasuredWidth()-getMeasuredWidth() / 4)) {
                            ViewCompat.offsetLeftAndRight(rightView, getMeasuredWidth() - left);
                            isRightOpen = false;
                            Log.e("zz", isLeftOpen + "left>left777777777");
                        }else{
                            ViewCompat.offsetLeftAndRight(rightView, 0 - left);
                            isRightOpen=true;
                            Log.e("zz", isLeftOpen + "left>left99999");
                        }
                    }else if (scrollDirect==SCROLL_MAIN_RIGHT){
                        if (left < getMeasuredWidth() / 4) {
                            ViewCompat.offsetLeftAndRight(topChildUnder, 0 - left);
                            isLeftOpen = false;
                            Log.e("zz", isLeftOpen + "left<3333");
                        } else if (left > getMeasuredWidth() / 4) {
                            ViewCompat.offsetLeftAndRight(topChildUnder, getMeasuredWidth() - left);
                            isLeftOpen = true;
                            Log.e("zz", isLeftOpen + "left>44444");
                        }
                    }
                } else if (/*topChildUnder == leftView&&*/scrollDirect==SCROLL_LEFT_RIGHT) {
                    left = centerView.getLeft();
                    Log.e("zz","left:"+left+"width:"+(topChildUnder.getMeasuredWidth()-topChildUnder.getMeasuredWidth() / 4));
                    if (left > (getMeasuredWidth()-getMeasuredWidth() / 4)) {
                        ViewCompat.offsetLeftAndRight(centerView, getMeasuredWidth() - left);
                        isLeftOpen = true;
                        Log.e("zz", isLeftOpen + "left>left555555555");
                    }else{
                        ViewCompat.offsetLeftAndRight(centerView, 0 - left);
                        isLeftOpen = false;
                        Log.e("zz", isLeftOpen + "left>left6666666");

                    }
                }else if (/*topChildUnder==rightView&&*/scrollDirect==SCROLL_RIGHT_LEFT){
                    left=rightView.getLeft();
                    Log.e("zz","left>left8"+left+"..."+getMeasuredWidth() / 4);
                    if (left > getMeasuredWidth() / 4) {
                        ViewCompat.offsetLeftAndRight(rightView, getMeasuredWidth() - left);
                        isRightOpen = true;
                        Log.e("zz", isLeftOpen + "left>left88888888");
                    }else{
                        ViewCompat.offsetLeftAndRight(rightView, 0 - left);
                        isRightOpen = false;
                        Log.e("zz", isLeftOpen + "left>left9999999");

                    }
                }
                Log.e("zz","move"+scrollDirect);
                break;
                case MotionEvent.ACTION_CANCEL:
                    Log.e("zz","movecancel");
                    break;
        }

        return true;
    }

    private void setScrollDirect(int scrollDirect) {
        this.scrollDirect=scrollDirect;
    }

    @Nullable
    public View findTopChildUnder(int x, int y) {
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight()
                    && y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

}
