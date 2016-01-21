package com.example.nrapesh.ecommerce;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;


/**
 * Created by VineetR on 18-01-2016.
 */

public class ImageUtil {

    public static Bitmap widthAdjust(Bitmap image)
    {
        Bitmap newBitmap = null;

        int width = image.getWidth();
        int height = image.getHeight();
//        System.out.println("Border Colors: " + image.getPixel(0,0) + "," + image.getPixel(width-1, height-1));

        int topCornerColor = image.getPixel(0,0);
        int bottomRightColor = image.getPixel(width - 1, height - 1);
        if ((width < height) && (topCornerColor == -1 || topCornerColor == -65794) && (bottomRightColor == -1 || bottomRightColor == -65794))
        {
            int sideAdjust = (height - width) / 2;
            int newWidth = width + 2*sideAdjust;
            int[] colors = new int[newWidth*height];
            //fill with existing
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    colors[i*newWidth + j + sideAdjust] = image.getPixel(j, i);
                }
            }
            //fill remaining width on both sides with border color
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < sideAdjust; j++)
                {
                    colors[newWidth*i + j] = colors[newWidth*i + sideAdjust + 1];
                    colors[newWidth*i + width + sideAdjust + 1] = colors[newWidth*i + width + sideAdjust - 1];
                }
            }

/*            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < newWidth; j++)
                {
                    System.out.print(colors[newWidth*i + j]);
                    System.out.print(' ');
                }
                System.out.println();
            }*/
            System.out.println("Adding Border to Image");
            return Bitmap.createBitmap(colors, newWidth, height, image.getConfig());
        }
        return image;
    }

    public static Bitmap cropTopBackgroud(Bitmap image) {
        Bitmap newBitmap = null;

        int width = image.getWidth();
        int height = image.getHeight();

        int y = findY(image);
        y = (y > 20) ? y - 20 : 0;
        newBitmap = Bitmap.createBitmap(image, 0, y, width, height - y);
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
