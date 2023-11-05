import java.util.ArrayList;
import java.util.List;

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


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    public void admitAbiturient(Abiturient abiturient, Faculty faculty) {
        if (faculty.isAdmissible(abiturient)) {
            addObserver(abiturient);
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.receiveLetter();
        }
    }
}
