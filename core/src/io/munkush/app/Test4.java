package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.munkush.app.MyGdxGame.*;

public class Test4 {
    List<Texture> listOfRep;
    Texture rep;
    boolean isRepShuffled = false;
    private final List<Texture> collectedRep = new ArrayList<>();

    public Test4(){
        setPersons();
    }
    private void setPersons() {
        listOfRep = new ArrayList<>();
        rep = new Texture("b1.png");
        for(int i = 0; i < 6; i++){
            listOfRep.add(new Texture("b" + (i+2) + ".png"));
        }
    }

    public void render(SpriteBatch batch){
        setDefault();

        if (collectedRep.size() == 6) {
            boolean isCorrect = true;
            int i = 2;
            for (Texture texture : collectedRep) {
                if (!texture.toString().equals("b" + i++ + ".png")) {
                    isCorrect = false;
                    isRepShuffled = false;
                    collectedRep.clear();
                    listOfRep.clear();
                    setPersons();
                    break; // Выход из цикла после очистки списка
                }
            }

            if (isCorrect) {
                testCount = 5;
                isRepShuffled = false;
                collectedRep.clear();
                listOfRep.clear();
                setPersons();
            }
        }

        if (!isRepShuffled) {
            Collections.shuffle(listOfRep);
            isRepShuffled = true;
        }

        batch.begin();
        batch.draw(rep, containerX, containerY + containerHeight / 2 + 100, 120, 170);
        batch.end();

        float characterWidth = 186.5f; // Фиксированная ширина текстур персонажей
        float textureHeight = containerHeight / 8; // Выберите подходящую высоту

        batch.begin();

        // Отобразить текстуры списка listOfRep внизу горизонтальной линии
        for (int i = 0; i < listOfRep.size(); i++) {
            Texture texture = listOfRep.get(i);
            float textureX = containerX + i * characterWidth; // Используйте фиксированную ширину
            float textureY = containerY;
            batch.draw(texture, textureX, textureY, characterWidth, textureHeight + 100);
        }

        // Создать временную копию collectedRep для итерации
        List<Texture> tempCollectedRep = new ArrayList<>(collectedRep);

        // Отобразить выбранные текстуры наверху
        for (int i = 0; i < tempCollectedRep.size(); i++) {
            Texture texture = tempCollectedRep.get(i);
            float textureX = containerX + i * characterWidth + 120; // Новая позиция X
            float textureY = containerY + containerHeight / 2 + 100; // Новая позиция Y
            batch.draw(texture, textureX, textureY, characterWidth, textureHeight + 60); // Используйте фиксированную ширину
        }

        batch.end();

        // Обработка нажатия на персонажей внизу горизонтальной линии
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Проверяем, что нажатие было в области текстур внизу
            if (touchY >= containerY && touchY <= containerY + textureHeight + 100) {
                int touchedCharacterIndex = (int) ((touchX - containerX) / characterWidth);
                if (touchedCharacterIndex >= 0 && touchedCharacterIndex < listOfRep.size()) {
                    Texture selectedCharacter = listOfRep.remove(touchedCharacterIndex);
                    collectedRep.add(selectedCharacter); // Добавляем выбранного персонажа в список
                }
            }
        }
    }

    public void dispose(){
        rep.dispose();

        for(Texture texture : listOfRep){
            texture.dispose();
        }
    }

}
