/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my.sprawozdanie1;

import sample.use.my.sprawozdanie1.SaltPepper;
import java.util.Arrays;
import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Weronika
 */
public class MedianFilter extends Plugin {

    private int dimension = 5;

    public void process(Image imgIn, Image imgOut) {

        int[][] pic = new int[imgIn.getWidth()][imgIn.getHeight()];
        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        // szerokość "obwódki" maski
        int frame_size = dimension / 2;
        // ilość pikseli w masce
        int size=dimension*dimension;

        // iteracja po wszystkich pikselach, które mają pełną maskę
        for (int i = frame_size; i < width - frame_size; ++i) {
            for (int j = frame_size; j < height - frame_size; ++j) {

                int[] tab = new int[size];
                int x = 0;
                for (int a = i - frame_size; a <= i + frame_size; ++a) {
                    for (int b = j - frame_size; b <= j + frame_size; ++b) {
                        // zapamiętywanie w pikseli wartości z maski
                        tab[x++] = imgIn.getRed(a, b);
                    }
                }
                // sortowanie maski i wybór mediany jako nowej wartości piksela
                Arrays.sort(tab);
                pic[i][j] = tab[size/2+1];

            }
        }

        for (int i = frame_size; i < width - frame_size; ++i) {
            for (int j = frame_size; j < height - frame_size; ++j) {
                imgOut.setRGB(i, j, pic[i][j], pic[i][j], pic[i][j]);
            }
        }

    }
}
