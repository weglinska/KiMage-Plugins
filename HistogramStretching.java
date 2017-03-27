/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my.Projekt;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.utils.histogram.Histogram;

/**
 *
 * @author Weronika
 */

public class HistogramStretching extends Plugin {
    @Override
    public void process(Image imgIn, Image imgOut) {
        int min =0;
        int max =255;
        int[] hist = new Histogram(imgIn, true).getRedHistogram();
        while(hist[min]<=0){
            min++;
        }

        while(hist[max]<=0){
            max--;
        }

        int[] LUT = new int[256];
        double skal = 255./(max-min);
        for(int i =0;i<=255;i++){
            LUT[i]=(int) (skal*(i-min));
        }
        
        for (int i = 0; i < imgIn.getWidth(); ++i) {
            for (int j = 0; j < imgIn.getHeight(); ++j) {
                int color = imgIn.getRed(i, j);
                imgIn.setRGB(i, j, LUT[color],LUT[color],LUT[color]);
            }
        }
    }

}