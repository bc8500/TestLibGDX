package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Images {
     List<String> imageList = new ArrayList<>();
     List<String> imageList2 = new ArrayList<>();
    List<String> imageList3 = new ArrayList<>();
    List<String> imageList4 = new ArrayList<>();
    List<String> imageList5 = new ArrayList<>();
     Random random = new Random();
    public static void main(String[] args) {
        int x = 1600;
        int y = 960;
        for (int i = 0; i < 20; i++) {
            System.out.println(x+"x");
            x += 1600;
            System.out.println(y+"y");
            y += 960;
        }


    }
    public Images() {
        imageList.add("masterchieftpose.jfif");
        imageList.add("trevor.jpg");
        imageList.add("batman.jpg");
    }

    public String getImages() {
        int randomNumber = random.nextInt(imageList.size());
        String imagePath =  imageList.get(randomNumber);
        imageList.remove(randomNumber);
        return imagePath;
    }
    public String lavaImage() {
        imageList2.add("lava.png");
        int randomNumber = random.nextInt(imageList2.size());
        return imageList2.get(randomNumber);
    }

     public String deathImage() {
        imageList3.add("minecraftDeathScene.jpg");
        int randomNumber = random.nextInt(imageList3.size());
        return imageList3.get(randomNumber);
    }
    public String flagPole() {
        imageList4.add("flag.jpg");
        int randomNumber = random.nextInt(imageList4.size());
        return imageList4.get(randomNumber);
    }
    public String playerImages() {
        imageList5.add("clownBen.JPG");
        imageList5.add("billy.jpg");
        int randomNumber = random.nextInt(imageList5.size());
        return imageList5.get(randomNumber);
    }
}