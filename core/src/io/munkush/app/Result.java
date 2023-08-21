package io.munkush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static io.munkush.app.Container.*;
import static io.munkush.app.Container.containerHeight;
import static io.munkush.app.MyGdxGame.*;

public class Result {

    public void render(SpriteBatch batch){
        setBackgroundImage();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.setColor(1f, 1f, 1f, 0.5f);
        shapeRenderer.rect(containerX, containerY, containerWidth, containerHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        String text = "QATE URINISLAR SANI: " + countOfFalseAnswer;

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.0f);
        font.setColor(Color.BLACK);

        GlyphLayout layout = new GlyphLayout(font, text);

        float textX = containerX + (containerWidth - layout.width) / 2;
        float textY = containerY + (containerHeight + layout.height) / 2;

        batch.begin();
        font.draw(batch, layout, textX, textY);
        batch.end();
    }


}
