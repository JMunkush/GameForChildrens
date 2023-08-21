package io.munkush.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


import java.util.*;

public class MyGdxGame extends ApplicationAdapter {
    float containerWidth;
    float containerHeight;
    float containerX;
    float containerY;
    float lineY = containerY + containerHeight / 2;
    Texture backgroundTexture;
    Texture startButtonTexture;
    Texture image1Texture;
    Texture image2Texture;
    Texture image3Texture;

    List<Texture> listOfRep;
    Texture rep;

    boolean isRepShuffled = false;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    List<Texture> numberTextures;

    Texture targetFruit;
    List<Texture> otherFruits;

    boolean isDigitsShuffled = false;
    boolean isFruitsShuffled = false;
    int countOfTrueAnswer = 0;

    int testCount = 0;
    boolean isTesting = false;

    @Override
    public void create() {

        containerWidth = Gdx.graphics.getWidth() - 800;
        containerHeight = Gdx.graphics.getHeight() - 200;
        containerX = (Gdx.graphics.getWidth() - containerWidth) / 2;
        containerY = (Gdx.graphics.getHeight() - containerHeight) / 2;
        lineY = containerY + containerHeight / 2;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        backgroundTexture = new Texture("background.jpg");
        startButtonTexture = new Texture("start.png");
        image1Texture = new Texture("left1.png");
        image2Texture = new Texture("right1.png");
        image3Texture = new Texture("right2.png");


        setPersons();

        targetFruit = new Texture("apple.png");
        otherFruits = new ArrayList<>();

        setFruits();

        numberTextures = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            numberTextures.add(new Texture(i + ".png"));
        }
        setupUI();
    }

    private void setPersons() {
        listOfRep = new ArrayList<>();
        rep = new Texture("b1.png");
        for(int i = 0; i < 6; i++){
            listOfRep.add(new Texture("b" + (i+2) + ".png"));
        }
    }

    private void setupUI() {
        float buttonWidth = 200;
        float buttonHeight = 50;
        float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(buttonX, buttonY, buttonWidth, buttonHeight);
        shapeRenderer.end();
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        if (isTesting && testCount == 1) {
            doTest1();
        } else if(isTesting && testCount == 2){
            doTest2();
        } else if(isTesting && testCount == 3){
            doTest3();
        } else if(isTesting && testCount == 4){
            doTest4();
        } else if(isTesting && testCount == 5){
            doTest5();
        }

        else {
            setBackgroundImage();
            batch.begin();
            batch.draw(startButtonTexture, (Gdx.graphics.getWidth() - startButtonTexture.getWidth()) / 2f,
                    (Gdx.graphics.getHeight() - startButtonTexture.getHeight()) / 2f);
            batch.end();

            if (Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                float buttonWidth = startButtonTexture.getWidth();
                float buttonHeight = startButtonTexture.getHeight();
                float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
                float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;

                if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                        touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                    isTesting = true;
                    testCount = 1;
                }
            }
        }
    }

    private void doTest5() {
        setDefault();
    }

    private final List<Texture> collectedRep = new ArrayList<>();

    private void doTest4() {
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





    private void setDefault() {
        setBackgroundImage();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        setContainer();
    }

    private final ArrayList<Texture> collectedTextures = new ArrayList<>(); // Список для сохранения порядка нажатий


    private final List<Texture> collectedFruits = new ArrayList<>(); // List to store collected fruit textures

    private void doTest3() {
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

    private void doTest2() {
        setDefault();




        if(collectedTextures.size() == 10){
            boolean isCorrect = true;
            int counter = 1;
            for (Texture texture : collectedTextures){
                int i = Integer.parseInt(texture.toString().replace(".png", ""));
                System.out.println(counter +":" + i);
                if(i != counter){
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
        // Отображение цифр внизу горизонтальной линии
        batch.begin();
        for (int i = 1; i <= 10; i++) {
            batch.draw(numberTextures.get(i - 1), containerX + (i - 1) * numberSpacing, containerY + 50, 51, 51);
        }
        batch.end();

        // Обработка нажатий только в области цифр
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

        // Отображение собранных цифр сверху горизонтальной линии
        batch.begin();
        float offsetX = containerX;
        for (Texture collectedTexture : collectedTextures) {
            batch.draw(collectedTexture, offsetX, lineY + 50, 51, 51);
            offsetX += numberSpacing;
        }
        batch.end();

    }






    private void setContainer() {

        shapeRenderer.rect(containerX, lineY, containerWidth, 2);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 0.5f);
        shapeRenderer.rect(containerX, containerY, containerWidth, containerHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {

        for(Texture texture :numberTextures){
            texture.dispose();
        }

        batch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
        startButtonTexture.dispose();
        image1Texture.dispose();
        image2Texture.dispose();
        image3Texture.dispose();
    }

    public void setBackgroundImage(){
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
    public void doTest1(){
        setDefault();

        float containerWidth = Gdx.graphics.getWidth() - 800;
        float containerHeight = Gdx.graphics.getHeight() - 200;
        float containerX = (Gdx.graphics.getWidth() - containerWidth) / 2;
        float containerY = (Gdx.graphics.getHeight() - containerHeight) / 2;

        batch.begin();
        batch.draw(image1Texture, containerX + 400, containerY + 400);
        batch.draw(image2Texture, containerX - 30 + containerWidth / 3, containerY);
        batch.draw(image3Texture, containerX - 30 + 2 * containerWidth / 3, containerY - 40);
        batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float image2X = containerX - 30 + containerWidth / 3;

            // Правильный ответ
            if (touchX >= image2X && touchX <= image2X + image2Texture.getWidth() &&
                    touchY >= containerY && touchY <= containerY + image2Texture.getHeight() - 150) {

                Gdx.gl.glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 1, 0, 1f); // Зеленый цвет
                shapeRenderer.rect(image2X, containerY + 90, image2Texture.getWidth(), image2Texture.getHeight() - 220);
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
                testCount = 2;
            }

        }

        // Неправильный ответ
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float image3X = containerX - 30 + 2 * containerWidth / 3;
            float image3Y = containerY - 40;
            if (touchX >= image3X && touchX <= image3X + image3Texture.getWidth() &&
                    touchY >= image3Y && touchY <= image3Y + image3Texture.getHeight() - 100) {

                Gdx.gl.glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1, 0, 0, 0.8f); // Красный цвет
                shapeRenderer.rect(image3X, image3Y + 110, image3Texture.getWidth(), image3Texture.getHeight() - 220);
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
        }
    }
}