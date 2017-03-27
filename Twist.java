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
public class Twist extends Plugin {

    private double a = 3;
    private double b = 50;

    public void process(Image imgIn, Image imgOut) {

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        // obliczamy punkt centralny obrazka
        double center_i = 0.5 * width;
        double center_j = 0.5 * height;
        Image img = new Image(width, height);

        // iterujemy po wszystkich pikselach
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                //obliczamy odleglosci od danego puntu do środka
                double delta_i = i - center_i;
                double delta_j = j - center_j; 
                
                // korzystając ze wzorów wyliczam potrzbne parametry
                double angle = a * Math.exp(-(Math.pow(delta_i, 2)
                        + Math.pow(delta_j, 2)) / Math.pow(b, 2));

                int coordinate_i = (int) (delta_i * Math.cos(angle)
                        + delta_j * Math.sin(angle) + center_i);
                int coordinate_j = (int) (-delta_i * Math.sin(angle)
                        + delta_j * Math.cos(angle) + center_j);

                // upewniam się, że nowe współrzędne punktu mieszczą się
                // w wymaganym zakresie
                if ((coordinate_i >= 0) && (coordinate_i < width)
                        && (coordinate_j >= 0) && (coordinate_j < height)) {
                    
                    // jeśli tak to umieszczam piksel w obiekcie img
                    img.setRGB(i, j, imgIn.getRed(coordinate_i, coordinate_j),
                            imgIn.getGreen(coordinate_i, coordinate_j),
                            imgIn.getBlue(coordinate_i, coordinate_j));
                }
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
