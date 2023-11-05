import java.util.List;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Abiturient> abiturients = AbiturientsReader.readAbiturients("Abiturients.txt");
        List<Faculty> faculties = FacultysReader.readFaculties("Faculties.txt");

        University university = new University();
        for (Abiturient abiturient : abiturients) {
            for (Faculty faculty : faculties) {
                university.admitAbiturient(abiturient, faculty);
            }
        }
        university.notifyObservers();
    }
}
