/*
 * 
 * 
 * 
 * @author Janina, Benjamin
 */


package de.fhtrier.gdw.ss2013.util;

import java.io.File;
import java.util.List;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.Image;

import de.fhtrier.gdw.commons.jackson.JacksonReader;

public class AssetLoader {

    public AssetLoader() {
        // TODO Auto-generated constructor stub
    }

    public static Image loadImage(String filename, String name){
        List<ImageInfo> imageInfos;

            try {
                imageInfos = JacksonReader.readList(filename, ImageInfo.class);
                for (ImageInfo imageInfo : imageInfos) {
                    if (imageInfo.name.equals(name)) {
                        return new Image(imageInfo.pfad);
                    }
                }
               // return new Image(imageInfo.pfad);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        return null;
    }
    public static void main(String args[]){
        System.setProperty("org.lwjgl.librarypath",
                new File(
                        new File(System.getProperty("user.dir"), "native"),
                        LWJGLUtil.getPlatformName()).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath",
                System.getProperty("org.lwjgl.librarypath"));
        System.out.println(loadImage("res/json/images.json", "testbild"));
        
    }
    
}
