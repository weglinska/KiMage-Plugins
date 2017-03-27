/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my2;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugins.color.Grayscale;
import kimage.utils.gui.ImageFrame;
import sample.use.my.Greyscale;
import sample.use.my.sprawozdanie1.SaltPepper;

/**
 *
 * @author Weronika
 */
public class PicErosion extends Plugin {

    private int dimension = 5;
    // wartość reprezntuje 'grubość' obwódki wokół analizowanego piksela
    private int frame_size=dimension/2;

    public void process(Image imgIn, Image imgOut) {

        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        // wartości wszystkich pikseli nowego obrazu
        int[][] pic = new int[width][height];

        // przejście po wszyskich pikselach obrazu
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                int min=imgIn.getRed(i, j);
                // przejście po wszystkich pikselach otaczających dany piksel
                // zgodnie z wymiarem SE
                for (int x = i - frame_size, x2 = 0;
                        x <= i + frame_size; ++x, ++x2) {
                    for (int y = j - frame_size, y2 = 0;
                            y <= j + frame_size; ++y, ++y2) {
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            // sprawdzenie czy piksel z otoczenia pokrywa
                            // jest mniejszy od aktualnego min
                            if (min>imgIn.getRed(x, y)) {
                                min=imgIn.getRed(x, y);
                            }
                        }
                    }
                }
                pic[i][j]=min;            
            }
        }

        // skopiowanie wyniku do obrazu wyjściowego
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                imgOut.setRGB(i, j, pic[i][j], pic[i][j], pic[i][j]);
            }
        }

    }
    
}