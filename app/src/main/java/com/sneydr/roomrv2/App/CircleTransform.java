package com.sneydr.roomrv2.App;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.core.content.ContextCompat;

import com.sneydr.roomrv2.R;
import com.squareup.picasso.Transformation;

public class CircleTransform implements Transformation {

    private Context context;

    public CircleTransform(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.colorPrimary);

        BitmapShader shader = new BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        Paint paint1 = new Paint();
        paint1.setColor(color);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(2);
        canvas.drawCircle(r, r, r, paint1);

        squaredBitmap.recycle();
        return bitmap;
    }



    @Override
    public String key() {
        return "circle";
    }
}