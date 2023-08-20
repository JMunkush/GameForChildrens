package io.munkush.app;

import com.badlogic.gdx.graphics.Texture;

public class Puzzle {
    private Texture[] puzzleTextures;
    private int currentTextureIndex;

    public Puzzle(Texture[] textures) {
        puzzleTextures = textures;
        currentTextureIndex = 0;
    }

    public Texture getCurrentTexture() {
        return puzzleTextures[currentTextureIndex];
    }

    public void nextTexture() {
        currentTextureIndex = (currentTextureIndex + 1) % puzzleTextures.length;
    }
}
