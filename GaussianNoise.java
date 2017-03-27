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
public class GaussianNoise extends Plugin {

    private double average = 1;
    private double deviation = 0.65;
    private double probability = 0.9;

    private int generateGauss() {
        double U1, U2, Z, X;
        // z definicji liczby te mają być różne od 0
        do {
            U1 = Math.random();
            U2 = Math.random();

        } while ((U1 == 0) || (U2 == 0));

        // wykorzystanie odpowiednich wzorów
        Z = Math.sqrt(-2 * Math.log(U1)) * Math.cos(2 * Math.PI * U2);
        X = average + deviation * Z;
        return (int) X;
    }

    public void process(Image imgIn, Image imgOut) {

        Plugin pl = new Grayscale();
        pl.process(imgIn, imgOut);

        for (int i = 0; i < imgIn.getWidth(); ++i) {
            for (int j = 0; j < imgIn.getHeight(); ++j) {

                // dodajemy szum do każdego piksela z 
                // zadanym prawdopodobieństwem
                double rand = Math.random();
                if (rand < probability) {

                    int tmp = imgIn.getRed(i, j) + generateGauss();

                    if (tmp < 0) {
                        imgOut.setRGB(i, j, 0, 0, 0);
                    } else if (tmp > 255) {
                        imgOut.setRGB(i, j, 255, 255, 255);
                    } else {
                        imgOut.setRGB(i, j, (int) tmp, (int) tmp, (int) tmp);
                    }
                }

            }
        }

    }
}
