package Multithreading.truckpool;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
public class TruckPool {
    public static void main(String[] args) {
        Semaphore smp = new Semaphore(5,true);

        Truck.startPool(smp);
    }
}
    class Truck extends Thread {

        private  Semaphore smp;
        private int number;

        private boolean isFree = false;

        public Truck(Semaphore smp, int number) {
            this.smp = smp;
            this.number = number;
        }

        public void run () {
           try {
               if(!isFree) {
                   smp.acquire();
                   System.out.printf("Грузовик номер %d заехал на КПП\n", number);
                   sleep(2000);
                   isFree = true;
                   System.out.printf("\t\tГрузовик номер %d выехал из КПП\n", number);
                   smp.release();
               }
           }
           catch (InterruptedException e){
               System.out.printf("\t Грузовик номер %d не заехал на КПП\n", number);
           }
        }

        public static void startPool(Semaphore smp) {
            List<Truck>trucks = new ArrayList<>();

            int number;
            for (number = 1; number <= 12; number++) {
                Truck t = new Truck(smp, number);
                trucks.add(t);
                t.start();
            }
        }

    }