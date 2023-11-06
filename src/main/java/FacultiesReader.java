import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;

public class FacultiesReader {
    public static List<Faculty> readFaculties(String fileName) {
        List<Faculty> faculties = new ArrayList<>();
        ClassLoader classLoader = FacultiesReader.class.getClassLoader();
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
                            if (Objects.equals(parts[0], "Минимальный проходной балл")) {
                                int score = Integer.parseInt(parts[1]);
                                faculty.setMinScore(score);
                            } else {
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
                if (faculty != null) {
                    faculties.add(faculty);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Ресурс не найден: " + fileName);
        }
        return faculties;
    }
    public static List<Faculty> readFacultiesFromXml(String xmlResourcePath) {
        List<Faculty> faculties = new ArrayList<>();

        try {
            ClassLoader classLoader = FacultiesReader.class.getClassLoader();
            InputStream xmlStream = classLoader.getResourceAsStream(xmlResourcePath);

            if (xmlStream != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlStream);
                doc.getDocumentElement().normalize();

                NodeList facultyList = doc.getElementsByTagName("faculty");

                for (int i = 0; i < facultyList.getLength(); i++) {
                    Element facultyElement = (Element) facultyList.item(i);
                    String name = facultyElement.getElementsByTagName("name").item(0).getTextContent();
                    int minScore = Integer.parseInt(facultyElement.getElementsByTagName("minimum_passing_score").item(0).getTextContent());

                    Faculty faculty = new Faculty(name);
                    faculty.setMinScore(minScore);

                    NodeList subjectList = facultyElement.getElementsByTagName("subject");

                    for (int j = 0; j < subjectList.getLength(); j++) {
                        Node subjectNode = subjectList.item(j);

                        if (subjectNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element subjectElement = (Element) subjectNode;
                            String subjectName = subjectElement.getAttribute("name");
                            int subjectScore = Integer.parseInt(subjectElement.getTextContent());
                            faculty.addRequiredSubject(subjectName, subjectScore);
                        }
                    }

                    faculties.add(faculty);
                }
            } else {
                System.err.println("Ресурс не найден: " + xmlResourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return faculties;
    }
    public static List<Faculty> readFacultiesFromJson(String jsonResourcePath) {
        List<Faculty> faculties = new ArrayList<>();

        try {
            ClassLoader classLoader = FacultiesReader.class.getClassLoader();
            InputStream jsonStream = classLoader.getResourceAsStream(jsonResourcePath);

            if (jsonStream != null) {
                JSONTokener tokener = new JSONTokener(jsonStream);
                JSONObject jsonData = new JSONObject(tokener);
                JSONArray facultyArray = jsonData.getJSONArray("faculties");

                for (int i = 0; i < facultyArray.length(); i++) {
                    JSONObject facultyObject = facultyArray.getJSONObject(i);
                    String name = facultyObject.getString("name");
                    int minimumPassingScore = facultyObject.getInt("minimum_passing_score");

                    Faculty faculty = new Faculty(name);
                    faculty.setMinScore(minimumPassingScore);

                    JSONObject subjectsObject = facultyObject.getJSONObject("subjects");

                    for (String subjectName : subjectsObject.keySet()) {
                        int subjectScore = subjectsObject.getInt(subjectName);
                        faculty.addRequiredSubject(subjectName, subjectScore);
                    }

                    faculties.add(faculty);
                }
            } else {
                System.err.println("Ресурс не найден: " + jsonResourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return faculties;
    }
}