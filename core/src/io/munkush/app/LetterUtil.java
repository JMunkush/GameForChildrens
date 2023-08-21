package io.munkush.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LetterUtil {


    public static String getForName(String toString) {
        if(toString.equals("A.png")){
            return "apple.png";
        }
        if(toString.equals("E.png")){
            return "boots.png";
        }
        if(toString.equals("M.png")){
            return "car.png";
        }
        if(toString.equals("B.png")){
            return "axe.png";
        }

        return null;
    }

    public static String getRandomLetter(){
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        String[] strings = new String[]{"A.png", "E.png", "M.png", "B.png"};
        return strings[randomNumber];
    }
    public static String randomForName(String toString) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);

        List<String> strings = new ArrayList<>();
        strings.add("car.png");
        strings.add("axe.png");
        strings.add("boots.png");
        strings.add("apple.png");
        strings.remove(getForName(toString));

        return strings.get(randomNumber);
    }

}
