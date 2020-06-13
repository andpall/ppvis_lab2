package Controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import model.SNP;
import model.Student;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocOpener {

    private List<Student> studentList;

    public List<Student> openDoc (File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory parserFactory;
        SAXParser parser;
        XMLHandler handler;

        studentList = new ArrayList<>();

        handler = new XMLHandler();
        parserFactory = SAXParserFactory.newInstance();
        parser = parserFactory.newSAXParser();
        parser.parse(file, handler);
        return studentList;
    }

    private class XMLHandler extends DefaultHandler {
        private String elem;
        private SNP snp;
        private String group;
        private int skippingCauseOfSick;
        private int skippingCauseOfOther;
        private int skippingWithNoReason;
        private int skippingAtAll;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
            elem = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (elem.equals("snp")) {
                String string = new String(ch, start, length);
                String[] strings = string.split(" ");
                snp = new SNP(strings[0], strings[1], strings[2]);
            } else if (elem.equals("group")) {
                group = new String(ch,start, length);
            } else if (elem.equals("skippingCauseOfSick")) {
                skippingCauseOfSick = Integer.parseInt(new String(ch, start, length));
            } else if (elem.equals("skippingCauseOfOther")) {
                skippingCauseOfOther = Integer.parseInt(new String(ch, start, length));
            } else if (elem.equals("skippingWithNoReason")) {
                skippingWithNoReason = Integer.parseInt(new String(ch, start, length));
            } else if (elem.equals("skippingAtAll")) {
                skippingAtAll = Integer.parseInt(new String(ch, start, length));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException{
            if(elem.equals("skippingAtAll")){
                studentList.add(new Student(
                    snp,
                    group,
                    skippingCauseOfSick,
                    skippingCauseOfOther,
                    skippingWithNoReason
                    ));
            }
            elem = "";
        }
    }
}
