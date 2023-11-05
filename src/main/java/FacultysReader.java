import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FacultysReader {
    public static List<Faculty> readFaculties(String fileName) {
        List<Faculty> faculties = new ArrayList<>();
        ClassLoader classLoader = FacultysReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                Faculty faculty = null;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        if (faculty == null) {
                            faculty = new Faculty(line);
                        } else {
                            String[] parts = line.split(": ");
                            if (parts.length == 2) {
                                String subjectName = parts[0];
                                int score = Integer.parseInt(parts[1]);
                                faculty.addRequiredSubject(subjectName, score);
                            }
                        }
                    } else {
                        if (faculty != null) {
                            faculties.add(faculty);
                            faculty = null;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Ресурс не найден: " + fileName);
        }
        return faculties;
    }
}


