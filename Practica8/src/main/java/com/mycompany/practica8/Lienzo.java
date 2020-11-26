/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica8;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author carlo
 */
public class Lienzo extends JPanel{
    
    private Image imageShare;
    private Mat imageMat, imageMatOriginal;
    
    public Lienzo(){    
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imageShare, 0, 0, null);
    }
    
    public void openImage(String path){
        imageMatOriginal = Imgcodecs.imread(path);
        imageMat = imageMatOriginal.clone();
        imageShare = HighGui.toBufferedImage(imageMat);
        this.repaint();
    }
    
    public void originalImageFilter(){
        imageMat = imageMatOriginal.clone();
        imageShare = HighGui.toBufferedImage(imageMat);
        this.repaint();
    }
    
    public void thresHoldImageFilter(String path, int thresHold){
       imageMat = umbralizar(Imgcodecs.imread(path), thresHold);
       imageShare = HighGui.toBufferedImage(imageMat);
       this.repaint();
    }
    
    public void closeImage(){
        imageShare=null;
        imageMat=null;
        this.repaint();
    }
    
    public void saveImage(String path){
        imageMatOriginal = imageMat.clone();
        Imgcodecs.imwrite(path,imageMat);
    }
    
    
    private Mat umbralizar(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(),
            imagen_original.cols(), CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(),
            imagen_original.cols(), CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original, imagenGris, Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris, imagenUmbralizada, umbral, 255,
            Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }
}
