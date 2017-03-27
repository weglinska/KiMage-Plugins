/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my.sprawozdanie1;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.utils.gui.ImageFrame;
import sample.use.my.Greyscale;

/**
 *
 * @author Weronika
 */
public class WhiteRohreBinarization extends Plugin {

    private int dimension = 5;
    private double k = 1.2;

    public void process(Image imgIn, Image imgOut) {

        // zmiana obrazu na monochromatyczny
        Plugin pl = new Greyscale();
        pl.process(imgIn, imgOut);

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        // szerokość "obwódki" wokół analizowanego piksela
        int frame_size = dimension / 2;
        int[][] pic = new int[width][height];

        // iteracja po obrazie z wyłączeniem pikseli bez pełnego otoczenia
        for (int i = frame_size; i < width - frame_size; ++i) {
            for (int j = frame_size; j < height - frame_size; ++j) {

                int sum = 0;
                // obliczanie sumy otoczenia
                for (int x = i - frame_size; x <= i + frame_size; ++x) {
                    for (int y = j - frame_size; y <= j + frame_size; ++y) {
                        sum += imgIn.getRed(x, y);
                    }
                }

                // uzupełnianie tablicy pic zgodnie z warunkiem
                if (imgIn.getRed(i, j) >= sum / (k * Math.pow(dimension, 2))) {
                    pic[i][j] = 255;
                } else {
                    pic[i][j] = 0;
                }

            }
        }

        for (int i = frame_size; i < width - frame_size; ++i) {
            for (int j = frame_size; j < height - frame_size; ++j) {
                imgIn.setRGB(i, j, pic[i][j], pic[i][j], pic[i][j]);
            }
        }

    }

}
