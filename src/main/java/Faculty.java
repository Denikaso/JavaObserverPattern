import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Faculty {

    private String name;
    private int minScore;
    private List<String> requiredSubjects = new ArrayList<>();
    private Map<String, Integer> requiredScores = new HashMap<>();
    public void setMinScore(int minScore){
        this.minScore = minScore;
    }
    public int getMinScore(){
        return this.minScore;
    }

    public Faculty(String name) {
        this.name = name;
    }
    public String getName(){return this.name;}

    public void addRequiredSubject(String subject, int minScore) {
        requiredSubjects.add(subject);
        requiredScores.put(subject, minScore);
    }

    public boolean isAdmissible(Abiturient abiturient) {
        if (abiturient == null) {
            return false;
        }

        for (String subject : requiredSubjects) {

            if (!abiturientHasSubject(abiturient, subject) || abiturient.getSubjectScore(subject) < requiredScores.get(subject)) {
                return false;
            }
        }
        return true;
    }

    private boolean abiturientHasSubject(Abiturient abiturient, String subjectName) {
        List<Subject> abiturientSubjects = abiturient.getSubjects();
        for (Subject abiturientSubject : abiturientSubjects) {
            if (abiturientSubject.getName().equals(subjectName)) {
                return true;
            }
        }
        return false;
    }

}
