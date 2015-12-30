package com.paragon.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class MyScrollView extends ViewGroup {
	private Context mContext;
	private Scroller mScroller;
	private int mScreenHeight;
	private int mLasty,mStart,mEnd;
	
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		init();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int y=(int)event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLasty=y;
			mStart=getScrollY();
			break;
		case MotionEvent.ACTION_MOVE:
			if(!mScroller.isFinished()){
				mScroller.abortAnimation();
			}
			int dy=mLasty-y;
//			if(getScrollY()<0){						            //只能向下滑动，不能向上滑动
//				dy=0;
//			}
//			if(getScrollY()>getHeight()-mScreenHeight){           //如果向下滑动的距离超过了剩余的可滑动距离，不滑动
//				dy=0;
//			}
			scrollBy(0, dy);
			mLasty=y;								//为了之后判断是翻页还是滑动，在此处跟新一下
			break;
		case MotionEvent.ACTION_UP:
			mEnd=getScrollY();
			int dScrollY=mEnd-mStart;
			if(dScrollY>0){
				if(dScrollY<mScreenHeight/3){		//若滚动距离小于屏幕高度1/3，则回滚
					mScroller.startScroll(				//由于现在已经滚动了一定距离，所以滚动起点为getScrollY()
							0,
							getScrollY(), 
							0, 
							-dScrollY);					//回滚到点击之前
				}else{									//若滚动距离大于屏幕高度1/3，则滚到下一个界面
					mScroller.startScroll(				//由于现在已经滚动了一定距离，所以滚动起点为getScrollY()
							0,
							getScrollY(), 
							0, 
							mScreenHeight-dScrollY);//滚到到下一个界面
				}	
			}else{
				if(dScrollY>-mScreenHeight/3){		//若为向上滚动，但滚动距离小于屏幕高度1/3，则回滚
					mScroller.startScroll(
							0, 
							getScrollY(), 
							0, 
							-dScrollY);
				}else{
					mScroller.startScroll(
							0, 
							getScrollY(), 
							0,
							-mScreenHeight-dScrollY);
				}
			}
			break;
		}
		postInvalidate();
		return true;
	}
	
	private void init(){
		WindowManager wm=(WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenHeight=dm.heightPixels;
		mScroller=new Scroller(mContext);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count=getChildCount();
		MarginLayoutParams mlp=(MarginLayoutParams) getLayoutParams();
		mlp.height=mScreenHeight*count;
		setLayoutParams(mlp);
		for(int i=0;i<count;i++){
			View child=getChildAt(i);
			if(child.getVisibility()!=View.GONE)
			child.layout(l, mScreenHeight*i, r, mScreenHeight*(i+1));
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int count=getChildCount();
		for(int i=0;i<count;i++){
			View child=getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
		}
	}
	@Override
	public void computeScroll() {
		super.computeScroll();
		if(mScroller.computeScrollOffset()){
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
	}
}
