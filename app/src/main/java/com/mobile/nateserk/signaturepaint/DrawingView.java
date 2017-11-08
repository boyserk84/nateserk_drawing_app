package com.mobile.nateserk.signaturepaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * DrawingView.java
 *
 * Drawing View by touch event.
 * Created by natek on 11/2/17.
 */

public class DrawingView extends SurfaceView implements SurfaceHolder.Callback {

    private Path _path = new Path();

    private Paint _drawPaint = new Paint();

    private Bitmap _mBitmap = null;
    private Canvas _mCanvas = null;

    public Bitmap GetBitmapFromView() {
        return _mBitmap;
    }

    public DrawingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        getHolder().addCallback(this);
        SetupDrawingBoard();

        //this.setDrawingCacheEnabled(true);
    }

    private void SetupDrawingBoard()
    {
        _drawPaint.setColor(Color.WHITE);
        _drawPaint.setAntiAlias(true);
        _drawPaint.setStrokeWidth(10);
        _drawPaint.setStyle(Paint.Style.STROKE);
        _drawPaint.setStrokeJoin(Paint.Join.ROUND);
        _drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void ResetDrawingBoardView()
    {
        _path.reset();
        _drawPaint.reset();
        SetupDrawingBoard();

        // NOTE: This needs to be called multiple times to ensure previous frames are cleared out.
        ResetCanvas();
        ResetCanvas();
        ResetCanvas();
        ResetCanvas();
    }

    private void ResetCanvas()
    {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas!=null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            getHolder().unlockCanvasAndPost(canvas);
            _mCanvas = null;
            _mBitmap = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float touchX = event.getX();
        float touchY = event.getY();
        Log.d("DrawingView", "touch event ( " + touchX + ", " + touchY + ")" + " Action=" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _path.moveTo(touchX, touchY);
                TryDrawing(this.getHolder());
                return true;
            case MotionEvent.ACTION_MOVE:
                _path.lineTo(touchX, touchY);
                TryDrawing(this.getHolder());
                break;
            default:
                return false;
        }

        this.postInvalidate();
        return true;
    }

    /**
     * Drawing on the given holder's canvas.
     * @param holder
     */
    private void TryDrawing(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawPath(_path, _drawPaint);
            holder.unlockCanvasAndPost(canvas);
            TryDrawingToBuffer();
        } else {
            Log.d("DEBUG","CANVAS iS NULL!");
        }
    }

    private void TryDrawingToBuffer() {
        if (_mBitmap==null) {
            _mBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        }

        if (_mCanvas==null) {
            _mCanvas = new Canvas(_mBitmap);
        }

        _mCanvas.drawPath(_path, _drawPaint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d("DEBUG", "Surface Created!");
        TryDrawing(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2)
    {
        Log.d("DEBUG", "Surface Changed");
        TryDrawing(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        Log.d("DEBUG","Surface Destroyed!");
    }
}
