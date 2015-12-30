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
//			if(getScrollY()<0){						            //ֻ�����»������������ϻ���
//				dy=0;
//			}
//			if(getScrollY()>getHeight()-mScreenHeight){           //������»����ľ��볬����ʣ��Ŀɻ������룬������
//				dy=0;
//			}
			scrollBy(0, dy);
			mLasty=y;								//Ϊ��֮���ж��Ƿ�ҳ���ǻ������ڴ˴�����һ��
			break;
		case MotionEvent.ACTION_UP:
			mEnd=getScrollY();
			int dScrollY=mEnd-mStart;
			if(dScrollY>0){
				if(dScrollY<mScreenHeight/3){		//����������С����Ļ�߶�1/3����ع�
					mScroller.startScroll(				//���������Ѿ�������һ�����룬���Թ������ΪgetScrollY()
							0,
							getScrollY(), 
							0, 
							-dScrollY);					//�ع������֮ǰ
				}else{									//���������������Ļ�߶�1/3���������һ������
					mScroller.startScroll(				//���������Ѿ�������һ�����룬���Թ������ΪgetScrollY()
							0,
							getScrollY(), 
							0, 
							mScreenHeight-dScrollY);//��������һ������
				}	
			}else{
				if(dScrollY>-mScreenHeight/3){		//��Ϊ���Ϲ���������������С����Ļ�߶�1/3����ع�
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
