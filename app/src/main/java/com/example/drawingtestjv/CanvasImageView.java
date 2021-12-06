package com.example.drawingtestjv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CanvasImageView extends ImageView {

    private Bitmap dwbmp;       // 描画保持用 For drawing retention
    private Canvas dwcanvas;    // キャンバス canvas
    private Paint dwpaint;      // ペイント paint
    private Object drawLock;
    public int ImgWidth, ImgHeight;

    private Path tcpath;                    // タッチパス Touch Path
    private PointF stpoint, edpoint;        // 開始-終了のポイント Start-End point

    public CanvasImageView(Context context) {
        super(context);
        initSet();
    }

    public CanvasImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSet();
    }

    private void initSet() {
        // 描画ペン初期化
        // Drawing pen initialization
        dwpaint = new Paint();
        dwpaint.setFilterBitmap(true);
        dwpaint.setColor(0xFF008800);
        dwpaint.setStyle(Paint.Style.STROKE);
        dwpaint.setStrokeCap(Paint.Cap.ROUND);
        dwpaint.setStrokeWidth(8);

        tcpath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        // 描画表示初期化
//        ImgWidth = w / 2;
//        ImgHeight = h / 2;
//        dwbmp = Bitmap.createBitmap(w / 2, h / 2, Bitmap.Config.ARGB_8888);
//        Canvas initCanvas = new Canvas(dwbmp);
//        initCanvas.drawColor(android.graphics.Color.BLUE);
//        this.setImageBitmap(dwbmp);
//        dwcanvas = new Canvas(dwbmp);
    }

    public void setDrawBitmap(Bitmap bmp) {
        dwbmp = bmp;
        this.setImageBitmap(dwbmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(rect, paint);

//        canvas.drawBitmap(dwbmp, 0, 0, dwpaint);
//        canvas.drawPath(tcpath, dwpaint);
//        canvas.drawLine(stpoint.x, stpoint.y, edpoint.x, edpoint.y, dwpaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int act = event.getAction();

        switch(act) {
            case MotionEvent.ACTION_DOWN:
//                tcpath.moveTo(event.getX() /2, event.getY()/ 2);
                stpoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
//                dwcanvas.drawPath(tcpath, dwpaint);
//                tcpath = new Path();
                stpoint = null;
                edpoint = null;

                this.setImageBitmap(dwbmp);
                break;
            case MotionEvent.ACTION_MOVE:
//                tcpath.lineTo(event.getX() / 2, event.getY() / 2);
                if (edpoint != null) stpoint = edpoint;
                edpoint = new PointF(event.getX(), event.getY());

                Canvas actCanvas = new Canvas(dwbmp);
                actCanvas.drawLine(stpoint.x / 2, stpoint.y / 2,
                                    edpoint.x / 2, edpoint.y / 2, dwpaint);
                break;
            case MotionEvent.ACTION_CANCEL:
                // something to do
                return false;
        }

//        invalidate();

        return true;
    }

}
