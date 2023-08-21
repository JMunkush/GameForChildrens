package io.munkush.app;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FruitUtil {

    public static String getRandomFruit(){
        String[] strings = new String[]{"apple.png", "banana.png", "carrot.png",
                "orange.png", "pumpkin.png", "strawberry.png", "tomato.png"};
        Random random = new Random();

        int randomNumber = random.nextInt(7); // Генерирует числа от 0 (включительно) до 7 (исключительно)
        return strings[randomNumber];
    }

    public static void setFruitsForName(String targetName, List<Texture> otherTextures){
        Random random = new Random();
        List<String> list = new ArrayList<>();
        list.add("apple.png");
        list.add("banana.png");
        list.add("carrot.png");
        list.add("orange.png");
        list.add("pumpkin.png");
        list.add("strawberry.png");
        list.add("tomato.png");

        for(int i = 0; i < 5; i++){
            otherTextures.add(new Texture(targetName));
        }

        list.remove(targetName);

        int rn = 6;
        for(int i = 0; i < 6; i++){
            int randomInt = random.nextInt(rn--);
            String textureName = list.get(randomInt);
            for (int j = 0; j < 5; j++){
                otherTextures.add(new Texture(textureName));
            }
            list.remove(textureName);
        }

    }
}
