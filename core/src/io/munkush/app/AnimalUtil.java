package io.munkush.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalUtil {
    public static String getForName(String toString) {
        if(toString.equals("left1.png")){
            return "right1.png";
        }
        if(toString.equals("left2.png")){
            return "right2.png";
        }
        if(toString.equals("left3.png")){
            return "right3.png";
        }
        if(toString.equals("left4.png")){
            return "right4.png";
        }

        return null;
    }

    public static String randomForName(String toString) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);

        List<String> strings = new ArrayList<>();
        strings.add("right1.png");
        strings.add("right2.png");
        strings.add("right3.png");
        strings.add("right4.png");
        strings.remove(getForName(toString));

        return strings.get(randomNumber);
    }
}
