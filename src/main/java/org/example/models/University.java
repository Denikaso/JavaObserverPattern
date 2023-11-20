package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class University {
    private List<Abiturient> abiturients = new ArrayList<>();
    private List<Faculty> faculties = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addAbiturient(Abiturient abiturient) {
        abiturients.add(abiturient);
    }
    public List<Abiturient> getAbiturients(){return abiturients;}

    public void addFaculties(Faculty faculty) {
        faculties.add(faculty);
    }
    public List<Faculty> getFaculties(){return faculties;}

    public void admitAbiturient(Abiturient abiturient, Faculty faculty) {
        if (faculty.isAdmissible(abiturient)) {
            abiturient.addFaculty(faculty);
        }
    }
    public void notifyAbiturients()
    {
        for (Abiturient abiturient : abiturients) {
            abiturient.receiveLetter();
        }
    }
}
