import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int SLEEP = 1;
        public static final int SIZE = 100;
//    public static final int SIZE = 5_000;

    public static void main(String[] args) throws InterruptedException {
        String[] names = new String[]{
                "Света",
                "Петр",
                "Алексей",
                "Иван",
                "Дмитрий",
                "Станислав"
        };

        List<String> namesList = new ArrayList<>();
        String name;
        for (int i = 0; i < SIZE; i++) {
            name = names[new Random().nextInt(names.length - 1)];
            namesList.add(name);
        }
        Thread.sleep(3000);


        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

        new Thread(() -> {
            try {
                addCount(concurrentHashMap, namesList, "concurrentHashMap");
                readCount(concurrentHashMap, "concurrentHashMap");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                addCount(map, namesList, "map");
                readCount(map, "map");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void addCount(Map<String, Integer> map, List<String> str, String nameMap) throws InterruptedException {
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (String name : str) {
            if (!map.containsKey(name))
                map.put(name, random.nextInt(10));
            else {
                int value = map.get(name) + random.nextInt(10);
                map.put(name, value);
            }
            Thread.sleep(SLEEP);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Время на операцию \"%s\" в \"%s\" - [%d]\n", "добавление", nameMap, end - start);
    }

    public static void readCount(Map<String, Integer> map, String nameMap) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.printf("[%s]%s: >> %d\n", nameMap, entry.getKey(), entry.getValue());
            Thread.sleep(SLEEP);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Время на операцию \"%s\" в \"%s\" - [%d]\n", "чтение", nameMap, end - start);
    }
}
