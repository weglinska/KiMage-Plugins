/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my.sprawozdanie1;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.utils.gui.ImageFrame;
import kimage.plugins.color.Grayscale;

/**
 *
 * @author Weronika
 */
public class SaltPepper extends Plugin {

    private double probability = 0.1;

    public void process(Image imgIn, Image imgOut) {

        Plugin pl = new Grayscale();
        pl.process(imgIn, imgOut);

        for (int i = 0; i < imgIn.getWidth(); ++i) {
            for (int j = 0; j < imgIn.getHeight(); ++j) {

                // na danym pikselu umieszczamy szum 
                // zgodnie z prawdopodobieństwem
                double ran = Math.random();
                if (ran < probability) {
                    double colour = Math.random();
                    if (colour < 0.5) {
                        imgOut.setRGB(i, j, 255, 255, 255);
                    } else {
                        imgOut.setRGB(i, j, 0, 0, 0);
                    }
                }

            }
        }
    }
}
