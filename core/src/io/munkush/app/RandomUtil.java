package io.munkush.app;

import java.util.Random;

public class RandomUtil {
    public static int getOneToZero(){
        Random random = new Random();
        return random.nextInt(4) + 1;
    }
}
