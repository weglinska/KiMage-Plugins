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
public class Sinus extends Plugin {

    private double amplitude = 20;
    private double period = 60;

    public void process(Image imgIn, Image imgOut) {

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        Image img = new Image(width, height);

        // do nowoutworzonego obiektu klasy Image wpisujemy na odpowiednich
        // pozycjach odpowiednie piksele
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                img.setRGB(i, (j + (int) (amplitude * 
                        Math.sin(i * 2 * Math.PI / period)) + height) % height,
                        imgIn.getRed(i, j), imgIn.getGreen(i, j),
                        imgIn.getBlue(i, j));

            }
        }

        // kopiujemy rezultat do obrazu wyjsciowego
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                imgOut.setRGB(i, j, img.getRed(i, j), img.getGreen(i, j), 
                        img.getBlue(i, j));
            }
        }
    }
}
