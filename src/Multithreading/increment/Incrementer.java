package Multithreading.increment;

public class Incrementer {
    private static volatile Integer counter1 =0;

    private static volatile   Long counter2 = 0L;


    public static synchronized void increment() {
        counter1++;
        counter2++;


    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Counter());
        Thread thread2 = new Thread(new Counter());
        Thread thread3 = new Thread(new Counter());


        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(counter1);
        System.out.println(counter2);

    }
    }


class Counter implements Runnable {

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            Incrementer.increment();
        }

    }
}
