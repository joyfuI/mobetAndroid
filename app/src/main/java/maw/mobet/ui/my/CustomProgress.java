package maw.mobet.ui.my;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import maw.mobet.R;

public class CustomProgress extends ProgressBar {
    private int mWidth = 13;    //프로그래스바 굵기
    private final RectF mRectF = new RectF();
    private final Paint mprogressPaint = new Paint();   //프로그래스바
    private final Paint mbackgroundPaint = new Paint(); //배경이 되는 프로그래스바

    public CustomProgress(Context context) {

        super(context);
        Log.e("Custom", "CustomProgress1");
        init(null, 0);
    }

    public CustomProgress(Context context, AttributeSet attrs) {

        super(context, attrs);
        Log.e("Custom", "CustomProgress2");
        init(attrs, 0);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        Log.e("Custom", "CustomProgress3");
        init(attrs, defStyleAttr);
    }

    public void init(AttributeSet attrs, int style){

        mprogressPaint.setAntiAlias(true);
        mprogressPaint.setStyle(Paint.Style.STROKE);

        mbackgroundPaint.setAntiAlias(true);
        mbackgroundPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        Matrix matrix = new Matrix();
        Shader gradient;

        Resources res = getResources();

        int progress = getProgress();

        float scale = getMax() > 0 ? (float)progress/getMax() *360: 0;
        float[] position = {0, 90};

        mbackgroundPaint.setColor(res.getColor(R.color.colorControlNormal));
        canvas.drawArc(mRectF, 0, 360, false, mbackgroundPaint);

        int[] color = {res.getColor(R.color.colorPrimary), res.getColor(R.color.colorPrimary)}; //처음색 그라데이션 들어가면서 변경될 색을 넣어준다

        gradient = new SweepGradient(getWidth()/2 ,getHeight()/2, color , position);
        matrix.postRotate(progress, getWidth()/2 ,getHeight()/2);
        gradient.setLocalMatrix(matrix);
        mprogressPaint.setShader(gradient);

        canvas.drawArc(mRectF, 270, scale, false, mprogressPaint);

        Log.e("Custom", "onDraw : " + scale);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min + 2 * mWidth, min + 2 * mWidth);

        mRectF.set(mWidth, mWidth, min + mWidth, min + mWidth);
        Log.e("Custom", "onMeasure");
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        Log.e("Custom", "setProgress");
        invalidate();
    }

    private class PixelUtil {
    }
}
