import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class PatientDumper {
    static String file = "dump.txt";
    static DequeWrapper newPatientStrings = new DequeWrapper();
    static List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Thread thread1 = new Thread(new PatientString());
        Thread thread2 = new Thread(new Patients());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join(5000);

        patients.forEach(System.out::println);

    }


    static class PatientString implements Runnable {

        @Override
        public void run() {
            try (Scanner scanner = new Scanner(new FileReader(file))) {
                String newPatientString;
                while (scanner.hasNext()) {
                    newPatientString = scanner.nextLine();
                    newPatientStrings.add(newPatientString);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Patients implements Runnable {
        @Override
        public void run() {
            while (true) {
                String temp;
                try {
                    temp = newPatientStrings.pop().replaceAll(",", "")
                            .replaceAll("'", "")
                            .replace("(", "")
                            .replace(")", "");
                    Patient newPatient = new Patient(temp);
                    patients.add(newPatient);
                } catch (InterruptedException e) {
                    System.out.println("Поток чтения из очереди прерван");
                }
            }
        }
    }
}

class DequeWrapper {
    final Deque<String> newPatientStrings;
    public DequeWrapper() {
        this.newPatientStrings = new ArrayDeque<>();
    }

    public synchronized void add (String st) {
        newPatientStrings.add(st);
        notify();
    }

    public synchronized String pop() throws InterruptedException {
        while (newPatientStrings.isEmpty()) {
            wait();
        }
        return newPatientStrings.pop();
    }

    public synchronized boolean isEmpty() {
        return newPatientStrings.isEmpty();
    }

}
