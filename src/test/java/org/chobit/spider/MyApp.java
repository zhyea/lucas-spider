package org.chobit.spider;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.List;

public class MyApp {


    public static void main(String[] args) {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read("http://localhost:82/example.xml");

            System.out.println("Root element :" + document.getRootElement().getName());

            Element classElement = document.getRootElement();

            List<Node> nodes = document.selectNodes("/class/student");
            System.out.println("----------------------------");

            for (Node node : nodes) {
                System.out.println("\nCurrent Element :" + node.getName());
                System.out.println("Student roll no : " + node.valueOf("@rollno"));
                System.out.println("First Name : " + node.selectSingleNode("firstname").getText());
                System.out.println("Last Name : " + node.selectSingleNode("lastname").getText());
                System.out.println("First Name : " + node.selectSingleNode("nickname").getText());
                System.out.println("Marks : " + node.selectSingleNode("marks").getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}