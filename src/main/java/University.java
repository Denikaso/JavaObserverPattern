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

    public void removeAbiturient(Abiturient abiturient) {
        abiturients.remove(abiturient);
    }
    public void addFaculties(Faculty faculty) {
        faculties.add(faculty);
    }

    public void removeFaculties(Faculty faculty) {
        faculties.remove(faculty);
    }


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


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
