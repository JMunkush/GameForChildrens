package io.munkush.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    static float containerWidth;
    static float containerHeight;
    static float containerX;
    static float containerY;
    static float lineY = containerY + containerHeight / 2;
    static Texture backgroundTexture;
    Texture startButtonTexture;

    static SpriteBatch batch;
    static ShapeRenderer shapeRenderer;


    int countOfTrueAnswer = 0;

    public static int testCount = 0;
    boolean isTesting = false;
    Test1 test1;
    Test2 test2;
    Test3 test3;
    Test4 test4;

    @Override
    public void create() {

        test1 = new Test1();
        test2 = new Test2();
        test3 = new Test3();
        test4 = new Test4();

        initContainerFields();
        setupUI();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        backgroundTexture = new Texture("background.jpg");
        startButtonTexture = new Texture("start.png");

    }





    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        if (isTesting && testCount == 1) {
            test1.render(batch, shapeRenderer);
        } else if(isTesting && testCount == 2){
            test2.render(batch);
        } else if(isTesting && testCount == 3){
            test3.render(batch);
        } else if(isTesting && testCount == 4){
            test4.render(batch);
        } else if(isTesting && testCount == 5){

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


    @Override
    public void dispose() {

        test1.dispose();
        test2.dispose();
        test3.dispose();

        batch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
        startButtonTexture.dispose();
    }


    public static void setDefault() {
        setBackgroundImage();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        setContainer();
    }


    private static void setContainer() {
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

    public static void setBackgroundImage(){
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
    private void initContainerFields(){
        containerWidth = Gdx.graphics.getWidth() - 800;
        containerHeight = Gdx.graphics.getHeight() - 200;
        containerX = (Gdx.graphics.getWidth() - containerWidth) / 2;
        containerY = (Gdx.graphics.getHeight() - containerHeight) / 2;
        lineY = containerY + containerHeight / 2;
    }
}