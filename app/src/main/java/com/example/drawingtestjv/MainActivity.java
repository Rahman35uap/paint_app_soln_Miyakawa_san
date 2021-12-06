package com.example.drawingtestjv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resoText;
//    private CanvasView cvView;

    private ImageView imgView;
    private Bitmap dwbmp;       // 描画保持用 //For drawing retention
    private Canvas dwcanvas;    // キャンバス //canvas
    private Paint dwpaint;      // ペイント // paint
    private Object drawLock;
    public int ImgWidth, ImgHeight;

    private Path tcpath;                    // タッチパス //Touch path
    private PointF stpoint, edpoint;        // 開始-終了のポイント //Start-End point
    private int dwflg = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UIとの紐づけ //Linking with UI
        resoText = (TextView) findViewById(R.id.ResoText);
//        cvView = (CanvasView) findViewById(R.id.CanvasView);
        imgView = (ImageView) findViewById(R.id.CanvasImage);

        imgView.post(()-> {
//            resoText.setText(String.format("W %d x H %d", cvView.getWidth(), cvView.getHeight()));

            // Viewサイズ取得
            // View size acquisition
            int imgWidth = imgView.getWidth();
            int imgHeight = imgView.getHeight();
            resoText.setText(String.format("W %d x H %d", imgWidth, imgHeight));

            // Viewサイズの半分のBitmapを生成(わかりやすく塗りつぶしている)
            // Generate a Bitmap that is half the size of the View (filled in clearly)
            dwbmp = Bitmap.createBitmap(imgWidth / 2, imgHeight / 2, Bitmap.Config.ARGB_8888);
            Canvas initCanvas = new Canvas(dwbmp);
            initCanvas.drawColor(Color.GRAY);
//            imgView.setDrawBitmap(dwbmp);

            // ひとまずセット
            // Set for the time being
            imgView.setImageBitmap(dwbmp);
        });
        imgView.setOnTouchListener(new ImageViewHighlighter());
        initSet();
    }

    private void initSet() {
        // 描画ペン初期化
        // Drawing pen initialization
        dwpaint = new Paint();
        dwpaint.setAntiAlias(true);
        dwpaint.setColor(0xFF008800);
        dwpaint.setStyle(Paint.Style.STROKE);
        dwpaint.setStrokeCap(Paint.Cap.ROUND);
        dwpaint.setStrokeWidth(8);
    }



    public class ImageViewHighlighter implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int act = event.getAction();

            switch(act) {
                case MotionEvent.ACTION_DOWN:
//                tcpath.moveTo(event.getX() /2, event.getY()/ 2);
                    // 開始座標(※中身の画像の縮尺を縦横半分にしているので座標も半分にする)
                    // Start coordinates (* Since the scale of the content image is halved vertically and horizontally, the coordinates are also halved)
                    stpoint = new PointF(event.getX() / 2, event.getY() / 2);
                    break;
                case MotionEvent.ACTION_UP:
//                dwcanvas.drawPath(tcpath, dwpaint);
//                tcpath = new Path();
                    // 座標クリア // Clear coordinates
                    stpoint = null;
                    edpoint = null;
                    imgView.setImageBitmap(dwbmp);
                    break;
                case MotionEvent.ACTION_MOVE:
//                tcpath.lineTo(event.getX() / 2, event.getY() / 2);
                    // 終了座標(※中身の画像の縮尺を縦横半分にしているので座標も半分にする)
                    // End coordinates (* Since the scale of the content image is halved vertically and horizontally, the coordinates are also halved)
                    edpoint = new PointF(event.getX() / 2, event.getY() / 2);

                    // 開始-終了線を描画
                    // Draw start-end line
                    Canvas actCanvas = new Canvas(dwbmp);
                    actCanvas.drawLine(stpoint.x, stpoint.y,
                            edpoint.x, edpoint.y, dwpaint);
                    if (dwflg < 0) {
                        // 再度セットしないと描画は表示されない
                        // The drawing will not be displayed unless it is set again.
                        imgView.setImageBitmap(dwbmp);
                        dwflg = 2;
                    }
                    dwflg--;
//                    imgView.setImageBitmap(dwbmp);

                    // 終了座標を次の開始座標に
                    // Set the end coordinate to the next start coordinate
                    stpoint = edpoint;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    // something to do
                    return false;
            }

            return true;
        }
    }

}