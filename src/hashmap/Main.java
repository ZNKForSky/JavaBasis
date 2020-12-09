package hashmap;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "888");
        String oldValue = hashMap.put("1", "666");
        System.out.println("oldValue == " + oldValue);
    }
}
