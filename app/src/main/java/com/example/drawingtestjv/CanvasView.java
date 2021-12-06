package com.example.drawingtestjv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SizeF;
import android.view.MotionEvent;
import android.view.View;


public class CanvasView extends View {

    private Bitmap dwbmp;       // 描画保持用 For drawing retention
    private Canvas dwcanvas;    // キャンバス canvas
    private Paint dwpaint;      // ペイント paint

    private Path tcpath;                    // タッチパス Touch Path
    private PointF stpoint, edpoint;        // 開始-終了のポイント Start-End point


    public CanvasView(Context context) {
        super(context);
        initSet();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSet();
    }

    private void initSet() {
        // 描画ペン初期化 Drawing pen initialization
        dwpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dwpaint.setColor(0xFF008800);
        dwpaint.setStyle(Paint.Style.STROKE);
        dwpaint.setStrokeJoin(Paint.Join.ROUND);
        dwpaint.setStrokeCap(Paint.Cap.ROUND);
        dwpaint.setStrokeWidth(8);

        tcpath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 描画表示初期化 Initialize drawing display
        dwbmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        dwcanvas = new Canvas(dwbmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(rect, paint);
//        if (stpoint == null || edpoint == null) return;
        canvas.drawBitmap(dwbmp, 0, 0, dwpaint);
        canvas.drawPath(tcpath, dwpaint);
//        canvas.drawLine(stpoint.x, stpoint.y, edpoint.x, edpoint.y, dwpaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int act = event.getAction();

        switch(act) {
            case MotionEvent.ACTION_DOWN:
                tcpath.moveTo(event.getX(), event.getY());
//                stpoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                dwcanvas.drawPath(tcpath, dwpaint);
                tcpath = new Path();
                stpoint = null;
                edpoint = null;
                break;
            case MotionEvent.ACTION_MOVE:
                tcpath.lineTo(event.getX(), event.getY());
//                if (edpoint != null) stpoint = edpoint;
//                edpoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                // something to do
                return false;
        }

        invalidate();
        return true;
    }

}
