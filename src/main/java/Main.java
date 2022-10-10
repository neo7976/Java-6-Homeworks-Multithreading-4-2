import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int SLEEP = 1;
    public static final int COUNT_THREAD = 5;
    //    public static final int SIZE = 100;
    public static final int SIZE = 5_000;

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());

        Runnable concurrentThread = () -> {
            try {
                addCount(concurrentHashMap,  "concurrentHashMap");
                readCount(concurrentHashMap, "concurrentHashMap");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable mapThread = () -> {
            try {
                addCount(map, "map");
                readCount(map, "map");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < COUNT_THREAD; i++) {
            Thread thread = new Thread(concurrentThread);
            thread.start();
        }

        for (int i = 0; i < COUNT_THREAD; i++) {
            Thread thread = new Thread(mapThread);
            thread.start();
        }
    }

    public static void addCount(Map<Integer, Integer> map, String nameMap) throws InterruptedException {
        Random random = new Random();
        int name = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            name = random.nextInt(150);
            if (!map.containsKey(name))
                map.put(name, random.nextInt(10));
            else {
                int value = map.get(name) + random.nextInt(10);
                map.put(name, value);
            }
            Thread.sleep(SLEEP);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Время на операцию \"%s\" в \"%s\" - [%d] <<>> %s\n", "добавление", nameMap, end - start,
                Thread.currentThread().getName());
    }

    public static void readCount(Map<Integer, Integer> map, String nameMap) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.printf("[%s]%s: >> %d\n", nameMap, entry.getKey(), entry.getValue());
            Thread.sleep(SLEEP);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Время на операцию \"%s\" в \"%s\" - [%d] <<>> %s\n", "чтение", nameMap, end - start,
                Thread.currentThread().getName());
    }
}
