package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
///////////this is four sound////////////////////
public class Drop extends ApplicationAdapter {
    private Texture dropImage;
    private Texture marioBackground;
    private Sound dropSound;


    @Override
    public void create() {
        // load the images for the droplet and the bucket, 64x64 pixels each


        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));


        // ... more to come ...

    }
}