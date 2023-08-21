package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.munkush.app.Container.*;
import static io.munkush.app.MyGdxGame.*;

public class Test3 {
    Texture targetFruit;
    List<Texture> otherFruits;
    boolean isFruitsShuffled = false;
    private final List<Texture> collectedFruits = new ArrayList<>();


    public Test3(){
        targetFruit = new Texture("apple.png");
        otherFruits = new ArrayList<>();
        initFruits();
    }

    private void initFruits() {
        targetFruit = new Texture(FruitUtil.getRandomFruit());

        otherFruits = new ArrayList<>();

        FruitUtil.setFruitsForName(targetFruit.toString(), otherFruits);
    }


    private void setFruits(){
        for (int i = 0; i < 7; i++) {
            otherFruits.add(new Texture("apple.png"));
        }
        for (int i = 0; i < 7; i++) {
            otherFruits.add(new Texture("banana.png"));
        }
        for (int i = 0; i < 7; i++) {
            otherFruits.add(new Texture("carrot.png"));
        }
        for (int i = 0; i < 7; i++) {
            otherFruits.add(new Texture("orange.png"));
        }
    }

    public void render(SpriteBatch batch){
        setDefault();

        if (!isFruitsShuffled) {
            Collections.shuffle(otherFruits);
            isFruitsShuffled = true;
        }

        // Check if all collected fruits are apples
        if (collectedFruits.size() == 5) {
            boolean allApples = true;

            for (Texture texture : collectedFruits) {
                String textureName = texture.toString();
                if (!textureName.equals(targetFruit.toString())) {
                    countOfFalseAnswer++;
                    allApples = false;
                    otherFruits.clear();
                    collectedFruits.clear();
                    initFruits();
                    isFruitsShuffled = false;
                    break;
                }
            }

            if (allApples) {
                isFruitsShuffled = false;
                testCount = 4;
                collectedFruits.clear();
                otherFruits.clear();
                setFruits();
            }
        }

        float textureWidth = containerWidth / 7;
        float textureHeight = containerHeight / 7;

        batch.begin();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 7; col++) {
                int index = row * 7 + col;
                if (index < otherFruits.size()) {
                    Texture texture = otherFruits.get(index);
                    float textureX = containerX + col * textureWidth;
                    float textureY = containerY + row * (textureHeight - 30);
                    batch.draw(texture, textureX + 38, textureY + 5, textureWidth - 80, textureHeight - 80);
                }
            }
        }

        float targetFruitX = containerX + (containerWidth - textureWidth) / 2;
        float targetFruitY = lineY + containerHeight / 2;
        batch.draw(targetFruit, targetFruitX, targetFruitY - 300, textureWidth, textureHeight);

        batch.end();

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Проверяем, что нажатие произошло в пределах контейнера
            if (touchX >= containerX && touchX <= containerX + containerWidth &&
                    touchY >= containerY && touchY <= containerY + containerHeight) {

                // Преобразуем координаты нажатия в локальные координаты контейнера
                float localTouchX = touchX - containerX;
                float localTouchY = touchY - containerY;

                // Находим индекс фрукта по локальным координатам
                int colIndex = (int) (localTouchX / textureWidth);
                int rowIndex = (int) (localTouchY / (textureHeight - 30)); // Учитываем смещение по вертикали
                int index = rowIndex * 7 + colIndex;

                // Если индекс корректен и фрукт существует, удаляем его из otherFruits
                if (index >= 0 && index < otherFruits.size()) {
                    Texture removedTexture = otherFruits.remove(index);
                    collectedFruits.add(removedTexture);
                }
            }
        }

    }


    public void dispose(){
        for(Texture texture : otherFruits){
            texture.dispose();
        }
        targetFruit.dispose();
        for(Texture texture : collectedFruits){
            texture.dispose();
        }
    }
}

