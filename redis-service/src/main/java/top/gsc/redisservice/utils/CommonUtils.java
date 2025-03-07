package top.gsc.redisservice.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CommonUtils {
    public static int generateCode(){
        return ThreadLocalRandom.current().nextInt(1000,9999);
    }
}
