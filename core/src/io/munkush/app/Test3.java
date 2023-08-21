package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.munkush.app.MyGdxGame.*;

public class Test3 {
    Texture targetFruit;
    List<Texture> otherFruits;
    boolean isFruitsShuffled = false;
    private final List<Texture> collectedFruits = new ArrayList<>();


    public Test3(){
        targetFruit = new Texture("apple.png");
        otherFruits = new ArrayList<>();
        setFruits();
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

        if (collectedFruits.size() == 7) {
            boolean allApples = true;

            for (Texture texture : collectedFruits) {
                String textureName = texture.toString();
                if (!textureName.equals("apple.png")) {
                    allApples = false;
                    collectedFruits.clear();
                    isFruitsShuffled = false;
                    otherFruits.clear();
                    setFruits();
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

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                int index = row * 7 + col;
                if (index < otherFruits.size()) {
                    Texture texture = otherFruits.get(index);
                    float textureX = containerX + col * textureWidth;
                    float textureY = containerY + row * textureHeight + 10;
                    batch.draw(texture, textureX + 50, textureY, 51, 51);
                }
            }
        }

        // Draw targetFruit texture at the center on top of the horizontal line
        float targetFruitX = containerX + (containerWidth - textureWidth) / 2;
        float targetFruitY = lineY + containerHeight / 2;
        batch.draw(targetFruit, targetFruitX, targetFruitY - 300, textureWidth, textureHeight);

        batch.end();

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Handle touch input
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            int touchedFruitIndex = (int) ((touchX - containerX) / textureWidth);
            int rowIndex = (int) ((touchY - containerY) / (textureHeight + 10));

            int indexToRemove = rowIndex * 7 + touchedFruitIndex;
            if (indexToRemove >= 0 && indexToRemove < otherFruits.size()) {
                Texture removedTexture = otherFruits.remove(indexToRemove);
                collectedFruits.add(removedTexture);
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

