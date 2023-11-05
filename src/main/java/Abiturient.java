import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Abiturient implements Observer {
    private String fullName;
    private List<Subject> subjects = new ArrayList<>();

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


    @Override
    public void receiveLetter() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Уважаемый " + fullName + ",");
            contentStream.newLine();
            contentStream.showText("Поздравляем! Вы приняты на факультет.");
            contentStream.endText();
            contentStream.close();

            document.save("C:\\Уник\\Java\\lab2.2\\results" + fullName + ".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
