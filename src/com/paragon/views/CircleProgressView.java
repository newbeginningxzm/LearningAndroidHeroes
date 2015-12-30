package com.paragon.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CircleProgressView extends View {
	private Context mContext;
	private int mWidth,mHeight;
	private Paint mCirclePaint,mArcPaint,mTextPaint;
	private float mCircleO,mCircleR,mLength;
	private float mStartAngle,mSweepAngle,mSweepValue=50;
	private String mText;
	private float mTextSize;
	private RectF mArcRect;
	public CircleProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
        // 绘制圆
        canvas.drawCircle(mCircleO, mCircleO, mCircleR, mCirclePaint);
        // 绘制弧线
        canvas.drawArc(mArcRect, 270, mSweepAngle, false, mArcPaint);
        // 绘制文字
        canvas.drawText(mText, 0, mText.length(),
                mCircleO, mCircleO + (mTextSize / 4), mTextPaint);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
//		mWidth=getSize(widthMeasureSpec, 200);
//		mHeight=getSize(heightMeasureSpec, 200);
		mWidth=MeasureSpec.getSize(widthMeasureSpec);
		mHeight=MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(mWidth,mHeight);
		initView();
	}
	@SuppressWarnings("deprecation")
	private void initView(){
		mLength=Math.min(mWidth, mHeight);
		mCircleO=mLength/2;
		mCircleR=mLength/4;
		mCirclePaint=new Paint();
		mCirclePaint.setColor(getResources().getColor(android.R.color.holo_green_light));
		mCirclePaint.setAntiAlias(true);
		mArcRect=new RectF((float)0.1*mLength, (float)0.1*mLength, (float)0.9*mLength, (float)0.9*mLength);
		mSweepAngle = (mSweepValue / 100f) * 360f;
		mArcPaint=new Paint();
		mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
		mArcPaint.setStyle(Style.STROKE);
		mArcPaint.setStrokeWidth((float) (0.1*mLength));
		mText = setShowText();
        mTextSize = setShowTextSize();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
	}
	private int getSize(int measureSpec,int defVal){
		int result=0;
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);
		switch (specMode) {
		case MeasureSpec.AT_MOST:
			result=Math.min(specSize, defVal);
			break;
		case MeasureSpec.EXACTLY:
			result=specSize;
			break;
		default:
			break;
		}
		return result;
	}
	private float setShowTextSize() {
        this.invalidate();
        return 50;
    }

    private String setShowText() {
        this.invalidate();
        return "Android Skill";
    }

    public void forceInvalidate() {
        this.invalidate();
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
        	mSweepValue = sweepValue;
        } else {
        	mSweepValue = 25;
        }
        this.invalidate();
    }

}
