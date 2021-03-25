package design.single;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luffy
 * @Classname Singleton6
 * @Description 使用容器实现单例
 * @Date 2021/3/8 16:11
 */
public class Singleton6 {
    private static Map<String, Object> map = new HashMap<>();

    public static void registerService(String key, Object instance) {
        if (!map.containsKey(key)) {
            map.put(key, instance);
        }
    }

    public static Object getService(String key) {
        return map.get(key);
    }
}
