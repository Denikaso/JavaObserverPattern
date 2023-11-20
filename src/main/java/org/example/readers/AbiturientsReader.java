package org.example.readers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import org.example.models.Abiturient;
import org.example.models.Subject;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class AbiturientsReader {
    public static List<Abiturient> readAbiturients(String fileName) {
        List<Abiturient> abiturients = new ArrayList<>();
        ClassLoader classLoader = AbiturientsReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                Abiturient abiturient = null;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        if (abiturient == null) {
                            abiturient = new Abiturient(line);
                        } else {
                            String[] parts = line.split(": ");
                            if (parts.length == 2) {
                                String subjectName = parts[0];
                                int score = Integer.parseInt(parts[1]);
                                abiturient.addSubject(subjectName, score);
                            }
                        }
                    } else {
                        if (abiturient != null) {
                            abiturients.add(abiturient);
                            abiturient = null;
                        }
                    }
                }
                if (abiturient != null) {
                    abiturients.add(abiturient);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Ресурс не найден: " + fileName);
        }
        return abiturients;
    }
    public static List<Abiturient> readAbiturientsFromXml(String fileName) {
        List<Abiturient> abiturients = new ArrayList<>();

        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            InputStream xmlStream = classLoader.getResourceAsStream(fileName);

            if (xmlStream != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlStream);
                doc.getDocumentElement().normalize();

                NodeList abiturientList = doc.getElementsByTagName("abiturient");

                for (int i = 0; i < abiturientList.getLength(); i++) {
                    Element abiturientElement = (Element) abiturientList.item(i);
                    String name = abiturientElement.getElementsByTagName("name").item(0).getTextContent();
                    Abiturient abiturient = new Abiturient(name);
                    List<Subject> subjects = new ArrayList<>();

                    NodeList subjectList = abiturientElement.getElementsByTagName("subject");
                    for (int j = 0; j < subjectList.getLength(); j++) {
                        Element subjectElement = (Element) subjectList.item(j);
                        String subjectName = subjectElement.getElementsByTagName("name").item(0).getTextContent();
                        int subjectScore = Integer.parseInt(subjectElement.getElementsByTagName("score").item(0).getTextContent());
                        abiturient.addSubject(subjectName, subjectScore);
                    }

                    abiturients.add(abiturient);
                }
            } else {
                System.err.println("Resource not found: " + fileName);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return abiturients;
    }
    public static List<Abiturient> readAbiturientsFromJson(String jsonResourcePath) {
        List<Abiturient> abiturients = new ArrayList<>();

        try {
            ClassLoader classLoader = AbiturientsReader.class.getClassLoader();
            InputStream jsonStream = classLoader.getResourceAsStream(jsonResourcePath);

            if (jsonStream != null) {
                JSONTokener tokener = new JSONTokener(jsonStream);
                JSONObject jsonData = new JSONObject(tokener);
                JSONArray abiturientArray = jsonData.getJSONArray("abiturients");

                for (int i = 0; i < abiturientArray.length(); i++) {
                    JSONObject abiturientObject = abiturientArray.getJSONObject(i);
                    String name = abiturientObject.getString("name");

                    Abiturient abiturient = new Abiturient(name);
                    JSONObject subjectsObject = abiturientObject.getJSONObject("subjects");

                    for (String subjectName : subjectsObject.keySet()) {
                        int subjectScore = subjectsObject.getInt(subjectName);
                        abiturient.addSubject(subjectName, subjectScore);
                    }

                    abiturients.add(abiturient);
                }
            } else {
                System.err.println("Ресурс не найден: " + jsonResourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return abiturients;
    }
}
