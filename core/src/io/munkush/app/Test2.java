package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.munkush.app.Container.*;
import static io.munkush.app.MyGdxGame.*;

public class Test2 {
    List<Texture> numberTextures;
    private final ArrayList<Texture> collectedTextures = new ArrayList<>();
    boolean isDigitsShuffled = false;


    public Test2() {
        numberTextures = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            numberTextures.add(new Texture(i + ".png"));
        }
    }

    public void render(SpriteBatch batch){
        setDefault();


        if(collectedTextures.size() == 10){
            boolean isCorrect = true;
            int counter = 1;
            for (Texture texture : collectedTextures){
                int i = Integer.parseInt(texture.toString().replace(".png", ""));
                if(i != counter){
                    countOfFalseAnswer++;
                    collectedTextures.clear();
                    isDigitsShuffled = false;
                    isCorrect = false;
                    break;
                }
                counter++;
            }
            if(isCorrect){
                testCount = 3;
                collectedTextures.clear();
            }
        }

        float numberSpacing = containerWidth / 10;

        if(!isDigitsShuffled){
            Collections.shuffle(numberTextures);
            isDigitsShuffled = true;
        }

        batch.begin();
        for (int i = 1; i <= 10; i++) {
            batch.draw(numberTextures.get(i - 1), containerX + (i - 1) * numberSpacing, containerY + 50, 51, 51);
        }
        batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchY >= containerY + 50 && touchY <= containerY + 100) {
                int touchedNumberIndex = (int) ((touchX - containerX) / numberSpacing);
                if (touchedNumberIndex >= 0 && touchedNumberIndex < 10) {
                    Texture touchedTexture = numberTextures.get(touchedNumberIndex);
                    if (!collectedTextures.contains(touchedTexture)) {
                        collectedTextures.add(touchedTexture);
                    }
                }
            }
        }

        batch.begin();
        float offsetX = containerX;
        for (Texture collectedTexture : collectedTextures) {
            batch.draw(collectedTexture, offsetX, lineY + 50, 51, 51);
            offsetX += numberSpacing;
        }
        batch.end();

    }

    public void dispose(){

        for(Texture texture : numberTextures){
            texture.dispose();
        }

        for(Texture texture : collectedTextures){
            texture.dispose();
        }

    }
}
