/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my2;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Weronika
 */
public class Erosion extends Plugin{
 
    private final int dimension=15;
    private final boolean [][] SE;
    int size;
    int frame_size;
    {
        frame_size=dimension/2;
        size=frame_size*2+1;
        SE=new boolean[size][size];
        for (int i=0; i<size; ++i)           
            for (int j=0; j<size; ++j)
                SE[i][j]=false;
        
        for (int i=0; i<size; ++i)
        

            SE[7][i]=SE[6][i]=SE[8][i]=true;


            


    }

    

    public void process(Image imgIn, Image imgOut) {

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        int[][] pic = new int[width][height];

        // iteracja po obrazie z wyłączeniem pikseli bez pełnego otoczenia
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                pic[i][j] = imgIn.getRed(i, j);
                for (int x = i - frame_size, x2 = 0;
                        x <= i + frame_size; ++x, ++x2) {
                    for (int y = j - frame_size, y2 = 0;
                            y <= j + frame_size; ++y, ++y2) {
                        // sprawdzenie czy nie wyszliśmy poza zakres
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            // jeśłi jakikolwiek piksel prykryty przez SE
                            // jest biały
                            if (imgIn.getRed(x, y) == 255 && SE[x2][y2]) {
                                // to kolorujemy piksel główny na biało
                                pic[i][j] = 255;
                            }
                        }
                    }

                }
            }
        }

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                imgOut.setRGB(i, j, pic[i][j], pic[i][j], pic[i][j]);
            }
        }

    }
}