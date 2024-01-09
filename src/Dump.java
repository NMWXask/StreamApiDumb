import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Dump {
    static String file = "dump.txt";
    public static List<Patient> getDump() {
        try(Stream<String> strings = Files.lines(Paths.get(file))) {
            return strings
                    .map(e -> e
                            .replaceAll(",", "")
                            .replaceAll("'", "")
                            .replace("(", "")
                            .replace(")", ""))
                    .map(Patient::new)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void patients() throws IOException {
        InputStream inStream = new FileInputStream(file);

        InputStreamReader inputStreamReader = new InputStreamReader(inStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;

        String[] patients = new String[0];

        while ((line = bufferedReader.readLine()) != null) {
            patients = line.split(" ");
        }

        System.out.println(Arrays.toString(patients));
    }
}

