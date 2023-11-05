import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AbiturientsReader {
    public static List<Abiturient> readAbiturients(String fileName) {
        List<Abiturient> abiturients = new ArrayList<>();
        ClassLoader classLoader = AbiturientsReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                Abiturient student = null;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        if (student == null) {
                            student = new Abiturient(line);
                        } else {
                            String[] parts = line.split(": ");
                            if (parts.length == 2) {
                                String subjectName = parts[0];
                                int score = Integer.parseInt(parts[1]);
                                student.addSubject(subjectName, score);
                            }
                        }
                    } else {
                        if (student != null) {
                            abiturients.add(student);
                            student = null;
                        }
                    }
                }
                if (student != null) {
                    abiturients.add(student);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Ресурс не найден: " + fileName);
        }
        return abiturients;
    }
}
