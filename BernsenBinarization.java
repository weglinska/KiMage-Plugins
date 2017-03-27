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
public class BernsenBinarization extends Plugin {

    private int epsilon = 30;
    private int Tg = 100;
    private int dimension = 5;

    public void process(Image imgIn, Image imgOut) {

        // zamiana obrazu na monochromatyczny
        Plugin pl = new Grayscale();
        pl.process(imgIn, imgOut);

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();

        // tablica dwuwymiarowa zawierająca wartości poszczególnych
        // pikseli obrazu wyjściowego
        int[][] pic = new int[width][height];
        // rozmiar "obwódki" piksela
        int frame_size = dimension / 2;

        // iteracja po pikselach obrazu
        for (int i = frame_size; i < width - frame_size; ++i) {
            for (int j = frame_size; j < height - frame_size; ++j) {

                // ustawienie początkowych wartości min i max dla kazdego okna
                int min = 255;
                int max = 0;
                
                // iteracja po pikselach okna
                for (int x = i - frame_size; x <= i + frame_size; ++x) {
                    for (int y = j - frame_size; y <= j + frame_size; ++y) {
                        
                        // szukanie wartosci min i max w oknie
                        if (imgIn.getRed(x, y) > max) {
                            max = imgIn.getRed(x, y);
                        }
                        if (imgIn.getRed(x, y) < min) {
                            min = imgIn.getRed(x, y);
                        }
                    }
                }

                // obliczenie progu lokalnego
                int T = (max + min) / 2;

                // sprawdzenie stosunku kontrastu do parametru epsilon
                if (max - min <= epsilon) {
                    // jeśli warunek jest spełniony progiem staje się 
                    // próg globalny
                    T = Tg;
                }

                // zapisanie odpowiednich wartości do tablicy pic
                if (imgIn.getRed(i, j) > T) {
                    pic[i][j] = 255;
                } 
                
                else {
                    pic[i][j] = 0;
                }

            }
        }

        // przekopiowanie zawartości tablicy pic do obrazu wyjściowego
        for (int i = frame_size; i < width - frame_size; ++i) {
            for (int j = frame_size; j < height - frame_size; ++j) {
                imgOut.setRGB(i, j, pic[i][j], pic[i][j], pic[i][j]);
            }
        }

    }
}
