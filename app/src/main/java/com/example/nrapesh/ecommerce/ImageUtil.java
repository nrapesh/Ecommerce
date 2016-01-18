package com.example.nrapesh.ecommerce;

import android.graphics.Bitmap;
import android.util.Log;


/**
 * Created by VineetR on 18-01-2016.
 */

public class ImageUtil {

    public static Bitmap cropTopBackgroud(Bitmap image) {
        Bitmap newBitmap = null;

        int width = image.getWidth();
        int height = image.getHeight();

        int y = findY(image);
        y = (y > 20) ? y - 20 : 0;
        newBitmap = Bitmap.createBitmap(image, 0, y, width, height-y);
        return newBitmap;
    }

    private static int findY(Bitmap image)
    {
        int backgroundColor = image.getPixel(1, 1);

        for(int y = 0; y < image.getHeight(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                if(image.getPixel(x, y) != backgroundColor)
                {
                    Log.d("Y is: ", String.valueOf(y));
                    return y;
                }
            }
        }
        return 0;
    }
}
