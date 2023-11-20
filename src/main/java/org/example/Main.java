package org.example;

import org.example.models.Abiturient;
import org.example.models.Faculty;
import org.example.models.University;
import org.example.readers.AbiturientsReader;
import org.example.readers.FacultiesReader;

import java.util.List;
import java.util.Scanner;

public class Main {
    public enum FileType {
        TXT, XML, JSON
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        FileType fileType;
        System.out.println("Выберите формат файла для считывания (TXT, XML, JSON): ");
        String format = scanner.nextLine().toUpperCase();
        try {
            fileType = FileType.valueOf(format);
        } catch (IllegalArgumentException e) {
            System.err.println("Неподдерживаемый формат файла.");
            return;
        }

        List<Abiturient> abiturients = null;
        List<Faculty> faculties = null;

        if (fileType == FileType.TXT) {
            abiturients = AbiturientsReader.readAbiturients("Abiturients.txt");
            faculties = FacultiesReader.readFaculties("Faculties.txt");
        } else if (fileType == FileType.XML) {
            abiturients = AbiturientsReader.readAbiturientsFromXml("Abiturients.xml");
            faculties = FacultiesReader.readFacultiesFromXml("Faculties.xml");
        } else if (fileType == FileType.JSON) {
            abiturients = AbiturientsReader.readAbiturientsFromJson("Abiturients.json");
            faculties = FacultiesReader.readFacultiesFromJson("Faculties.json");
        }

        University university = new University();
        for (Abiturient abiturient : abiturients) {
            university.addAbiturient(abiturient);
        }
        for (Faculty faculty : faculties) {
            university.addFaculties(faculty);
        }
        for (Abiturient abiturient : abiturients) {
            for (Faculty faculty : faculties) {
                university.admitAbiturient(abiturient, faculty);
            }
        }
        university.notifyAbiturients();
    }
}
