package io.munkush.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;




public class MyGdxGame extends ApplicationAdapter {
    Texture backgroundTexture;
    Texture startButtonTexture;
    Texture image1Texture;
    Texture image2Texture;
    Texture image3Texture;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    int countOfTrueAnswer = 0;

    int testCount = 0;
    boolean isTesting = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        backgroundTexture = new Texture("background.jpg");
        startButtonTexture = new Texture("start.png");
        image1Texture = new Texture("left1.png");
        image2Texture = new Texture("right1.png");
        image3Texture = new Texture("right2.png");

        backgroundTexture = new Texture("background.jpg");
        startButtonTexture = new Texture("start.png");

        setupUI();
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

    private void doTest2() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        setBackgroundImage();
        setContainer();


    }

    private void setContainer() {
        float containerWidth = Gdx.graphics.getWidth() - 800;
        float containerHeight = Gdx.graphics.getHeight() - 200;
        float containerX = (Gdx.graphics.getWidth() - containerWidth) / 2;
        float containerY = (Gdx.graphics.getHeight() - containerHeight) / 2;
        float lineY = containerY + containerHeight / 2;

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
        setBackgroundImage();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);

        setContainer();

        float containerWidth = Gdx.graphics.getWidth() - 800;
        float containerHeight = Gdx.graphics.getHeight() - 200;
        float containerX = (Gdx.graphics.getWidth() - containerWidth) / 2;
        float containerY = (Gdx.graphics.getHeight() - containerHeight) / 2;

        batch.begin();
        batch.draw(image1Texture, containerX + 400, containerY + 400);
        batch.draw(image2Texture, containerX - 30 + containerWidth / 3, containerY);
        batch.draw(image3Texture, containerX - 30 + 2 * containerWidth / 3, containerY - 40);
        batch.end();

        if (Gdx.input.isTouched()) {
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