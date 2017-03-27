/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my2;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Weronika
 */
public class Dilation extends Plugin {

    private int dimension = 11;
    private boolean[][] SE;
    // wartość reprezntuje 'grubość' obwódki wokół analizowanego piksela
    private int frame_size;
    private int size;

    // inicjalizacja SE dla macierzy 5x5 z wszystkimi wartościami '1'
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
        // wartości wszystkich pikseli nowego obrazu
        int[][] pic = new int[width][height];

        // przejście po wszyskich pikselach obrazu
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                pic[i][j] = imgIn.getRed(i, j);

                // przejście po wszystkich pikselach otaczających dany piksel
                // zgodnie z wymiarem SE
                for (int x = i - frame_size, x2 = 0;
                        x <= i + frame_size; ++x, ++x2) {
                    for (int y = j - frame_size, y2 = 0;
                            y <= j + frame_size; ++y, ++y2) {
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            // sprawdzenie czy piksel z otoczenia pokrywa
                            // się z odpowiadającą wartością '1' z SE
                            if (SE[x2][y2] && imgIn.getRed(x, y) == 0) {
                                pic[i][j] = 0;
                            }
                        }
                    }
                }
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
