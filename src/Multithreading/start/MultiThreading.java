package Multithreading.start;

public class MultiThreading {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ThreadMethods::getFirst);
        Thread thread2 = new Thread(ThreadMethods::getSecond);
        Thread thread3 = new Thread(ThreadMethods::getThird);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

}

class ThreadMethods {
    static void getFirst(){
        System.out.println("first...");
    }

    static void getSecond(){
        System.out.println("second...");
    }

    static void getThird(){
        System.out.println("third...");
    }
}
