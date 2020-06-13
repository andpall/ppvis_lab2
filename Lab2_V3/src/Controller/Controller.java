package Controller;

import javafx.collections.ObservableList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.SNP;
import model.Student;

public class Controller {
    private Student model;
    private DocOpener docOpener;
    private int rowsOnPage;
    private int currentPage = 1;
    private int numberOfPages;
    private String pagination;
    private String itemsCount;

    public Controller(Student model) {
        this.model = model;
    }

    public List<Student> getStudentList() {
        return model.getStudentList();
    }

    public void addStudent(String name, String surname, String patronym, String group, int skippingCauseOfSick, int skippingCauseOfOther, int skippingWithNoReason) {
        model.addStudent(new Student(new SNP(surname, name, patronym), group, skippingCauseOfSick, skippingCauseOfOther, skippingWithNoReason));
    }

    public void openDoc(File file) {
        docOpener = new DocOpener();
        try {
            model.setStudentList(docOpener.openDoc(file));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void saveDoc(File file) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            document.setXmlStandalone(true);
            Element root = document.createElement("students");
            document.appendChild(root);
            List<Student> students = model.getStudentList();
            for (Student student : students) {
                Element studentElement = document.createElement("student");

                Element snp = document.createElement("snp");
                snp.setTextContent(student.getSnp().toString());

                Element group = document.createElement("group");
                group.setTextContent(student.getGroup());

                Element skippingCauseOfSick = document.createElement("skippingCauseOfSick");
                skippingCauseOfSick.setTextContent(Integer.toString(student.getSkippingCauseOfSick()));

                Element skippingCauseOfOther = document.createElement("skippingCauseOfOther");
                skippingCauseOfOther.setTextContent(Integer.toString(student.getSkippingCauseOfOther()));

                Element skippingWithNoReason = document.createElement("skippingWithNoReason");
                skippingWithNoReason.setTextContent(Integer.toString(student.getSkippingWithNoReason()));

                Element skippingAtAll = document.createElement("skippingAtAll");
                skippingAtAll.setTextContent(Integer.toString(student.getSkippingWithNoReason() + student.getSkippingCauseOfOther() +
                        student.getSkippingWithNoReason()));

                studentElement.appendChild(snp);
                studentElement.appendChild(group);
                studentElement.appendChild(skippingCauseOfSick);
                studentElement.appendChild(skippingCauseOfOther);
                studentElement.appendChild(skippingWithNoReason);
                studentElement.appendChild(skippingAtAll);

                root.appendChild(studentElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch (Exception e) {
        }
    }

    public List<Student> search(String selectedItem, List<String> criteriaListText) {
        List<Student> StudentList = getStudentList();
        List<Student> resultList;
        resultList = new ArrayList<>();
        SearchCriteria criteriaForSelection = SearchCriteria.getCriteriaByName(selectedItem);
        switch (criteriaForSelection) {
            case GROUP_AND_SURNAME: {
                final String GROUP = criteriaListText.get(0);
                final String SURNAME = criteriaListText.get(1);
                for (Student Student : StudentList) {
                    if (Student.getSurname().equals(SURNAME) || Student.getGroup().equals(GROUP)) {
                        resultList.add(Student);
                    }
                }
            }
            break;
            case SURNAME_AND_TYPE_OF_SKIP: {
                final String SURNAME = criteriaListText.get(2);
                final String TYPE_OF_SKIP = criteriaListText.get(3);

                for (Student Student : StudentList) {
                    if (Student.getSurname().equals(SURNAME) || Student.getSkipByName(TYPE_OF_SKIP)>0) {
                        resultList.add(Student);
                    }
                }
            }
            break;
            case SURNAME_AND_AMOUNT_SKIP: {
                final String SURNAME = criteriaListText.get(4);
                String []strings = criteriaListText.get(5).split("-");
                final int AMOUNT_DOWN = Integer.parseInt(strings[0]);
                final int AMOUNT_UP = Integer.parseInt(strings[1]);
                for (Student Student : StudentList) {
                    if (Student.getSurname().equals(SURNAME) || (Student.getSkippingAtAll() >= AMOUNT_DOWN && Student.getSkippingAtAll() <= AMOUNT_UP)) {
                        resultList.add(Student);
                    }
                }
            }
                break;
        }

        return resultList;
    }


    public void delete(List<Student> indexList) {
        for (Student Student : indexList) {
            getStudentList().remove(Student);
        }
    }

    public void setRowsOnPage(String rowText, ObservableList<Student> StudentObsList, ObservableList<Student> curStudentObsList) {
        rowsOnPage = Integer.parseInt(rowText);
        currentPage = 1;

        refreshPage(StudentObsList, curStudentObsList);
    }

    public void goBegin(ObservableList<Student> StudentObsList, ObservableList<Student> curStudentObsList) {
        currentPage = 1;
        refreshPage(StudentObsList, curStudentObsList);
    }

    public void goLeft(ObservableList<Student> StudentObsList, ObservableList<Student> curStudentObsList) {
        if (currentPage > 1) {
            currentPage--;
        }
        refreshPage(StudentObsList, curStudentObsList);
    }

    public void goRight(ObservableList<Student> StudentObsList, ObservableList<Student> curStudentObsList) {
        if (currentPage < numberOfPages) {
            currentPage++;
        }
        refreshPage(StudentObsList, curStudentObsList);
    }

    public void goEnd(ObservableList<Student> StudentObsList, ObservableList<Student> curStudentObsList) {
        currentPage = numberOfPages;
        refreshPage(StudentObsList, curStudentObsList);
    }

    private void refreshPage(ObservableList<Student> StudentObsList, ObservableList<Student> curStudentObsList) {
        int fromIndex = (currentPage - 1) * rowsOnPage;
        int toIndex = currentPage * rowsOnPage;

        if (toIndex > StudentObsList.size()) {
            toIndex = StudentObsList.size();
        }

        curStudentObsList.clear();
        curStudentObsList.addAll(
                StudentObsList.subList(
                        fromIndex,
                        toIndex
                )
        );

        refreshPagination(StudentObsList);
    }

    private void refreshPagination(ObservableList<Student> StudentObsList) {
        numberOfPages = (StudentObsList.size() - 1) / rowsOnPage + 1;
        pagination = currentPage + "/" + numberOfPages;
        itemsCount = "/" + StudentObsList.size() + "/";
    }

    public String getPagination() {
        return pagination;
    }

}

