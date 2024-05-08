package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Images {
     List<String> imageList = new ArrayList<>();
     List<String> imageList2 = new ArrayList<>();
    List<String> imageList3 = new ArrayList<>();
     Random random = new Random();

    public Images() {
        imageList.add("clownBen.JPG");
        imageList.add("masterchieftpose.jfif");
        imageList.add("billy.jpg");
        imageList.add("halo.jpg");
        imageList.add("houseRainbow.jpg");
        imageList.add("rocket leauge map.jpg");
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
        int randomNumber = random.nextInt(imageList2.size());
        return imageList3.get(randomNumber);
    }
}