/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.my2;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugins.color.SimpleInvert;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Weronika
 */
// klasa służąca rozwiązywaniu labiryntów
// działa przy założeniu, że obrazy które wykorzystuje są binarne
public class Maze extends Plugin {

    // wymiar macierzy
    private final int size = 3;

    // deklaracja SE dla pocieniania
    private int[][] thinningSE1;
    private int[][] thinningSE2;


    // deklaracja SE dla pruningu
    private int[][] pruningSE1;
    private int[][] pruningSE2;


    // wartość reprezntuje 'grubość' obwódki wokół analizowanego piksela
    private final int frame_size=size / 2;;

    // blok inicjalizacyjny
    {

        // stworzenie elementów strukturalnych dla pocieniania
        thinningSE1 = new int[size][size];
        thinningSE1[0][0] = thinningSE1[0][1] = thinningSE1[0][2] = -1;
        thinningSE1[1][0] = thinningSE1[1][2] = 0;
        thinningSE1[2][0] = thinningSE1[2][1] = thinningSE1[2][2] = thinningSE1[1][1] = 1;


        thinningSE2 = new int[size][size];
        thinningSE2[0][0] = thinningSE2[2][0] = thinningSE2[2][2] = 0;
        thinningSE2[0][1] = thinningSE2[0][2] = thinningSE2[1][2] = -1;
        thinningSE2[1][0] = thinningSE2[1][1] = thinningSE2[2][1] = 1;



        // stworzenie elementów strukturalnych dla pruningu
        pruningSE1 = new int[size][size];
        pruningSE1[0][0] = pruningSE1[1][0] = pruningSE1[2][0] = pruningSE1[2][1] = pruningSE1[2][2] = pruningSE1[1][2] = -1;
        pruningSE1[1][1] = 1;
        pruningSE1[0][1] = pruningSE1[0][2] = 0;


        pruningSE2 = new int[size][size];
        pruningSE2[0][0] = pruningSE2[0][1] = 0;
        pruningSE2[0][2] = pruningSE2[1][0] = pruningSE2[1][2] = pruningSE2[2][0] = pruningSE2[2][1] = pruningSE2[2][2] = -1;
        pruningSE2[1][1] = 1;


    }

    // funkcja umożliwiająca obracanie macierzy zgodnie ze wskazówkami zegara
    private int[][] rotation(int[][] mask) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[j][size - i - 1] = mask[i][j];
            }
        }
        return matrix;
    }

    // wykonuje operacje Hir or Miss na obrazie wejściowym dla danego SE
    private void HitOrMiss(Image img, int[][] SE) {

        int width = img.getWidth();
        int height = img.getHeight();
        // wartości wszystkich pikseli nowego obrazu
        int[][] pic = new int[width][height];

        // przejście po wszyskich pikselach obrazu
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                pic[i][j] = 255;
                // przejście po wszystkich pikselach otaczających dany piksel
                // zgodnie z wymiarem SE
                for (int x = i - frame_size, x2 = 0;
                        x <= i + frame_size; ++x, ++x2) {
                    for (int y = j - frame_size, y2 = 0;
                            y <= j + frame_size; ++y, ++y2) {
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            // sprawdzenie czy piksel z otoczenia
                            // spełnia kryteria zgodne z SE
                            if ((SE[x2][y2] == 1 && img.getRed(x, y) == 0)
                                    || (SE[x2][y2] == -1
                                    && img.getRed(x, y) == 255)) {
                                pic[i][j] = 0;
                            }
                        }
                    }
                }
            }
        }

        // skopiowanie wyniku do obrazu danego jako argument
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                img.setRGB(i, j, pic[i][j], pic[i][j], pic[i][j]);
            }
        }

    }

    // funkcja obliczająca różnicę pomiędzy obrazami, umieszcza wynik
    // w pierwszym argumencie
    public void Diff(Image destination, Image temporary) {

        for (int i = 0; i < destination.getWidth(); ++i) {
            for (int j = 0; j < destination.getHeight(); ++j) {
                int tmp = Math.abs(destination.getRed(i, j) - temporary.getRed(i, j));
                destination.setRGB(i, j, tmp, tmp, tmp);
            }
        }
    }

    // spawdza czy dwa obrazy są identyczne
    private boolean isIdentical(Image img1, Image img2) {
        for (int i = 0; i < img1.getWidth(); ++i) {
            for (int j = 0; j < img1.getHeight(); ++j) {
                if (img1.getRed(i, j) != img2.getRed(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void thinning(Image img) {

        int width = img.getWidth();
        int height = img.getHeight();
        Image img_dst = img.copy();
        Image prev;
        Image img_temp;
        // liczba iteracji determinuje liczbe obrotów SE
        int iterations = 4;

        do {
            // kopiujemy obraz co umożliwi sprawdzenie czy został on zmieniony
            // w kolejnej iteracji
            prev = img_dst.copy();
            
            // pierwsza iteracja zachodzi dla oryginalnych SE
            for (int i = 0; i < iterations; ++i) {
                img_temp = img_dst.copy();
                HitOrMiss(img_temp, thinningSE1);
                // odejmowanie od obrazu wyniku z HitOrMiss
                // wynik jest zapisywany w img_dst
                Diff(img_dst, img_temp);
                img_temp = img_dst.copy();
                HitOrMiss(img_temp, thinningSE2);
                Diff(img_dst, img_temp);
                // obrócenie macierzy
                thinningSE1 = rotation(thinningSE1);
                thinningSE2 = rotation(thinningSE2);
            }

            // dopóki się różnią
        } while (!isIdentical(prev, img_dst));

        // skopiowanie wyniku
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                img.setRGB(i, j, img_dst.getRed(i, j),
                        img_dst.getRed(i, j), img_dst.getRed(i, j));
            }
        }

    }

    private void pruning(Image img) {

        int width = img.getWidth();
        int height = img.getHeight();
        Image img_dst = img.copy();
        Image img_temp;
        
        // liczba iteracji determinuje liczbe obrotów SE        
        int iterations=4;
        
        // pierwsza iteracja zachodzi dla oryginalnych SE
        for (int i=0; i<iterations; ++i)
        {
            img_temp = img_dst.copy();
            HitOrMiss(img_temp, pruningSE1);
            // odejmowanie od obrazu wyniku z HitOrMiss
            // wynik jest zapisywany w img_dst
            Diff(img_dst, img_temp);
            img_temp = img_dst.copy();
            HitOrMiss(img_temp, pruningSE2);
            Diff(img_dst, img_temp);
            // obrócenie macierzy
            pruningSE1=rotation(pruningSE1);
            pruningSE2=rotation(pruningSE2);
        }

        // skopiowanie wyniku do obrazu wyjściowego
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                img.setRGB(i, j, img_dst.getRed(i, j), img_dst.getRed(i, j), 
                        img_dst.getRed(i, j));
            }
        }

    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        
        int width=imgIn.getWidth();
        int height=imgIn.getHeight();
        
        // wprowadzenie dodatkowej białej obwódki wokól danego labiryntu
        int extra_frame=4;
        Image copy=new Image(width+2*extra_frame, height+2*extra_frame);
        // pokolorowanie kopii na biało
        for (int i = 0; i < width+2*extra_frame; ++i) {
            for (int j = 0; j < height+2*extra_frame; ++j) {
                copy.setRGB(i, j, 255,255,255);
            }
        }
         
        // przekopiowanie czarnych elementów z danego labiryntu 
        // wraz z wyśrodkowaniem
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (imgIn.getRed(i, j)==0)
                    copy.setRGB(i+extra_frame-1, j+extra_frame-1, 0,0,0);
            }
        }

        // wykonanie pocieniania
        thinning(copy);

        // 30-krotnie wykonanie pruningu
        for (int i=0; i<30; ++i)
            pruning(copy);

        // zmiana kolorów na negatyw
        Plugin pl=new SimpleInvert();
        pl.process(copy);
        
        // naniesienie rozwiązania na obraz wyjściowy
        for (int i=0; i<imgIn.getWidth(); ++i)
            for (int j=0; j<imgIn.getHeight(); ++j)
            {
                // kopiujemy do obrazu piksele z trasą
                int val=copy.getRed(i+extra_frame-1, j+extra_frame-1);
                if (val==0)
                    imgOut.setRGB(i, j, val, val, val);
            }            
    }
}
