/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my.sprawozdanie1;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Weronika
 */
public class Shear extends Plugin {

    private double a = 0.3; // stała przesunięcia w pionie
    private double b = 0.5; // stała przesunięcia w poziomie

    public void process(Image imgIn, Image imgOut) {

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();

        // wyliczenie wymiarów nowego obiektu Image
        int img_width = (int) (width + Math.abs(b) * height);
        int img_height = (int) (height + Math.abs(a) * width);
        Image img = new Image(img_width, img_height);

        // uzupełnienie img piksealami z imgIn na odpowiednich pozycjach
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                img.setRGB((int) (Math.abs(b) * j + i),
                        (int) (Math.abs(a) * i + j),
                        imgIn.getRed(i, j), imgIn.getGreen(i, j),
                        imgIn.getBlue(i, j));
            }
        }

        // umieszczenie obrazu z img w imgOut, 
        // z jednoczesnym jego wyśrodkowaniem
        for (int i = (img_width - width) / 2, q = 0;
                i < (img_width - width) / 2 + width; ++i, ++q) {
            for (int j = (img_height - height) / 2, p = 0;
                    j < (img_height - height) / 2 + height; ++j, ++p) {
                imgOut.setRGB(q, p, img.getRed(i, j),
                        img.getGreen(i, j), img.getBlue(i, j));
            }
        }
    }
}
