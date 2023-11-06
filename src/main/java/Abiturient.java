import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Abiturient implements Observer {
    private String fullName;
    private List<Subject> subjects = new ArrayList<>();
    private List<Faculty> admittedFaculties = new ArrayList<>();

    public Abiturient(String fullName) {
        this.fullName = fullName;
    }

    public void addSubject(String subjectName, int score) {
        subjects.add(new Subject(subjectName, score));
    }

    public String getFullName() {
        return fullName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getSubjectScore(String subjectName) {
        for (Subject abiturientSubject : subjects) {
            if (abiturientSubject.getName().equals(subjectName)) {
                return abiturientSubject.getScore();
            }
        }
        return 0;
    }
    public List<Faculty> getFaculties(){return admittedFaculties;}
    public void addFaculty(Faculty faculty) {admittedFaculties.add(faculty);}

    @Override
    public void receiveLetter() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDType0Font font = PDType0Font.load(document, new File("C:\\Уник\\arial.ttf"));
            contentStream.setFont(font, 12);

            float yPosition = 700;

            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("Уважаемый " + fullName);

            if (admittedFaculties.isEmpty()) {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("К сожалению, вы не прошли ни на один факультет.");
            } else {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Поздравляем! Вы приняты на следующие факультеты:");

                for (Faculty faculty : admittedFaculties) {
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(faculty.getName());
                }
            }

            contentStream.endText();
            contentStream.close();

            document.save("C:\\Уник\\Java\\lab2.2\\results\\" + fullName + ".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
