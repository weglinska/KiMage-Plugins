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
public class PoissonNoise extends Plugin {

    private double lambda = 0.5;
    private double probability = 0.9;

    private int generatePoisson() {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }

    public void process(Image imgIn, Image imgOut) {

        Plugin pl = new Grayscale();
        pl.process(imgIn, imgOut);

        for (int i = 0; i < imgIn.getWidth(); ++i) {
            for (int j = 0; j < imgIn.getHeight(); ++j) {

                double rand = Math.random();
                if (rand < probability) {
                    // dodanie szumu do piksela
                    int tmp = imgIn.getRed(i, j) + generatePoisson();

                    if (tmp < 0) {
                        imgOut.setRGB(i, j, 0, 0, 0);
                    } else if (tmp > 255) {
                        imgOut.setRGB(i, j, 255, 255, 255);
                    } else {
                        imgOut.setRGB(i, j, tmp, tmp, tmp);
                    }
                }
            }

        }
    }
}
