package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import static io.munkush.app.Container.*;
import static io.munkush.app.Container.containerX;
import static io.munkush.app.MyGdxGame.*;

public class Test1 {
    Texture targetTexture;

    List<Texture> otherTextures;

    Texture selectedTexture;

    public Test1(){
        initVariables();
    }

    private void initVariables() {

        targetTexture = new Texture("left" + RandomUtil.getOneToZero() + ".png");

        otherTextures = new ArrayList<>();

        otherTextures.add(new Texture(AnimalUtil.getForName(targetTexture.toString())));
        otherTextures.add(new Texture(AnimalUtil.randomForName(targetTexture.toString())));

    }


    public void dispose(){
       targetTexture.dispose();

       for(Texture texture : otherTextures){
           texture.dispose();
       }

    }
    public void render(SpriteBatch batch) {
        setDefault();
        float targetY = containerY + containerHeight / 2 + 50;
        float targetX = containerX + containerWidth / 2 - 150;


        if (selectedTexture != null) {
            String s = AnimalUtil.getForName(targetTexture.toString());
            if (!selectedTexture.toString().equals(s)) {
                countOfFalseAnswer++;
                initVariables();
            } else {
                testCount = 2;
            }
            selectedTexture = null;
        }

        batch.begin();
        batch.draw(targetTexture, targetX - 50, targetY, 350, 350);
        batch.end();

        float otherY = containerY + 100;
        float otherX = containerX + 150;
        float spacing = 0;

        batch.begin();
        for (Texture texture : otherTextures) {
            batch.draw(texture, otherX + spacing, otherY, 350, 350);
            spacing += 500;

            // Проверка нажатия на текстуру
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                float textureX = otherX + spacing - 500; // X-координата начала текстуры
                float textureWidth = 300; // Ширина текстуры
                float textureHeight = 300; // Высота текстуры

                if (touchX >= textureX && touchX <= textureX + textureWidth &&
                        touchY >= otherY && touchY <= otherY + textureHeight) {
                    selectedTexture = texture;
                }
            }
        }
        batch.end();

    }
}

