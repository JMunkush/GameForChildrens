package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import static io.munkush.app.Container.*;
import static io.munkush.app.MyGdxGame.*;

public class Test5 {
    Texture targetTexture;
    List<Texture> otherTextures;
    private Texture selectedTexture = null; // Переменная для хранения выбранной текстуры
    public Test5(){
        initVariables();
    }


    public void render(SpriteBatch batch) {
        setDefault();
        float targetY = containerY + containerHeight / 2 + 50;
        float targetX = containerX + containerWidth / 2 - 150;


        if (selectedTexture != null) {
            String s = LetterUtil.getForName(targetTexture.toString());
            if (!selectedTexture.toString().equals(s)) {
                countOfFalseAnswer++;
                initVariables();
            } else {
                testCount = 1;
                isTesting = false;
                isEnded = true;
            }
            selectedTexture = null;
        }

        batch.begin();
        batch.draw(targetTexture, targetX, targetY, 300, 300);
        batch.end();

        float otherY = containerY + 100;
        float otherX = containerX + 150;
        float spacing = 0;

        batch.begin();
        for (Texture texture : otherTextures) {
            batch.draw(texture, otherX + spacing, otherY, 300, 300);
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
    public void initVariables(){

        targetTexture = new Texture(LetterUtil.getRandomLetter());

        otherTextures = new ArrayList<>();

        otherTextures.add(new Texture(LetterUtil.getForName(targetTexture.toString())));
        otherTextures.add(new Texture(LetterUtil.randomForName(targetTexture.toString())));
    }

    public void dispose(){
        targetTexture.dispose();
        for(Texture texture : otherTextures){
            texture.dispose();
        }
    }
}
