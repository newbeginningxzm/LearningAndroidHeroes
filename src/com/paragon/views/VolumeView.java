package com.paragon.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class VolumeView extends View {
	private Context mContext;
	private int mWidth,mHeight,mRectnum,mRectWidth,mRectHeight,mDelay;
	private float mArea,mOffset;
	private Paint mRectPaint;
	private LinearGradient mLinearGradient;
	
	public VolumeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		init();
	}
	
	
	private void init(){
		mRectPaint=new Paint();
		mRectPaint.setColor(Color.BLUE);
		mRectPaint.setStyle(Style.FILL);
		mRectnum=12;
		mArea=0.8f;
		mOffset=0.2f;
		mDelay=300;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth=MeasureSpec.getSize(widthMeasureSpec);
		mHeight=MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(mWidth, mHeight);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth=getWidth();
		mHeight=getHeight();
		mRectWidth=(int)((mArea*mWidth)/mRectnum);
		mLinearGradient=new LinearGradient(
				0, 
				0, 
				mRectWidth, 
				mHeight, 
				Color.BLUE,
				Color.GREEN, 
				Shader.TileMode.MIRROR);
		mRectPaint.setShader(mLinearGradient);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		for(int i=0;i<mRectnum;i++){
			mRectHeight=(int) (mHeight*Math.random());
			canvas.drawRect(
					(1-mArea)*mWidth+mRectWidth*i+mOffset*mRectWidth, 
					mRectHeight, 
					(1-mArea)*mWidth+mRectWidth*(i+1), 
					mHeight, 
					mRectPaint);
		}
		postInvalidateDelayed(mDelay);
	}
}
