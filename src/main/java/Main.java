import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class   Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue <String> queueA = new ArrayBlockingQueue<>(100);
        ArrayBlockingQueue <String> queueB = new ArrayBlockingQueue<>(100);
        ArrayBlockingQueue <String> queueC = new ArrayBlockingQueue<>(100);

        new Thread(() -> {
            addText(queueA);
            addText(queueB);
            addText(queueC);
        }).start();

        Thread a = new Thread(() -> {
            try {
                int maxA = getText(queueA,'a');
                System.out.println("Максимальное количество a " + maxA);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        a.start();

       Thread b = new Thread(() -> {
           try {
               int maxB = getText(queueB,'b');
               System.out.println("Максимальное количество b " + maxB);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       });
       b.start();

        Thread c =new Thread(() -> {
            try {
                int maxC = getText(queueC,'c');
                System.out.println("Максимальное количество c " + maxC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        c.start();

        a.join();
        b.join();
        c.join();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void addText (ArrayBlockingQueue a){
        for (int i = 0; i < 10000; i++) {
            try {
                a.put(generateText("abc", 100000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public static int getText (ArrayBlockingQueue<String> b, char abc) throws InterruptedException {
        int COUT = 0;
        int MAX= 0;
        for (int i = 0; i < 10000; i++) {
            String a = b.take();
            for (char c : a.toCharArray()) {
                if (c == abc) COUT++;
            }
           if (COUT > MAX){
               MAX = COUT;
               COUT = 0;
           }
        }
        return MAX;
    }
}
