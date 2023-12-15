import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StreamMethods {
    static Map<LocalDate, List<String>> collectMethod(List<Patient> patients) {

        Map<LocalDate, List<String>> mapOfPatientsByDateName = patients.stream()
                .collect(Collectors.groupingBy(Patient::getBirthDate,
                        Collectors.mapping(Patient::getFio, Collectors.toList())));
        System.out.println(mapOfPatientsByDateName.entrySet());


        return mapOfPatientsByDateName;
    }

    static void forEachMethod(List<Patient> patients) {
        patients.forEach(el -> System.out.println(el.getFio() + ":  расходы :" + el.getExpenses()));

    }

    static void minMaxMethod(List<Patient> patients) {
        Patient min = patients.stream().min(Comparator.comparingInt(StreamMethods::compare)).get();
        Patient max = patients.stream().max(Comparator.comparingInt(StreamMethods::compare)).get();

        System.out.println("Минимальный пациент :" + min.getFio() + " " + min.getExpenses());
        System.out.println("Минимальный пациент :" + max.getFio() + " " + max.getExpenses());
    }

    static int compare(Patient patient) {
        return patient.getExpenses().stream()
                .reduce(Integer::sum).get();
    }

    static void findFirstMehod(List<Patient> patients) {
        System.out.println(patients.stream()
                .filter(p -> p.getBirthDate().getYear() == 1999).findFirst());
    }

    static void allMachMethod(List<Patient> patients) {
        patients.stream()
                .filter((StreamMethods::patientWithoutExpenses))
                .collect(Collectors.toList());
    }

    static boolean patientWithoutExpenses(Patient patient) {
        boolean isHealthy = patient.getExpenses().stream()
                .allMatch(n -> n == 0);
        return isHealthy;
    }

    static void noneMatchMethod(List<Patient>patients) {
        boolean isImmortal = patients.stream()
                .noneMatch(p->p.getBirthDate().getYear()<LocalDate.now().getYear()-100);

        if(isImmortal){
            System.out.println("Пациентов старше 100 лет нет.");
        }
        else {
            System.out.println("пациенты старше 100 лет есть.");
        }
    }

    static void anyMatchMethod (List<Patient>patients) {
        boolean anyMatch = patients.stream()
                .anyMatch(p->p.getBirthDate().getYear()<1923);

        if (anyMatch) {
            System.out.println("Пациент старше 100 лет есть в списке.");
        }
        else {
            System.out.println("Пациента старше 100 лет нет в списке.");
        }
    }
}
//1.Метод collect() - Преобразовать стрим из пациентов в Map, где ключ - дата рождения, а значение - фио без преобразований
//2.Метод ForEach() - Вывести на экран только список расходов каждого пациента
//3.Метод min(),max() - Получить минимального и максимального пациента из потока, параметр - сумма всех расходов пациента, с помощью компаратора.
//4.Метод findFirst() - получить первого пациента, кто родился в декабре 1999 года
//5.Метод allMatch() - проверить, есть ли хоть один абсолютно здоровый человек
//6.Метод noneMatch() - проверить, есть ли хоть один человек старше 100 лет
//7.Метод anyMatch() - проверить, есть ли хоть один человек старше 100 лет