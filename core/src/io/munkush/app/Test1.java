package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static io.munkush.app.MyGdxGame.setDefault;
import static io.munkush.app.MyGdxGame.testCount;

public class Test1 {
    Texture image1Texture;
    Texture image2Texture;
    Texture image3Texture;

    public Test1(){
        image1Texture = new Texture("left1.png");
        image2Texture = new Texture("right1.png");
        image3Texture = new Texture("right2.png");

    }


    public void dispose(){
        image1Texture.dispose();
        image2Texture.dispose();
        image3Texture.dispose();
    }
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
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
    }
}
