package com.group.innowise;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserContact {
    private static final String PATH = "src/resources/users.txt";
    private static final String PATH_XML = "src/resources/users.xml";
    private List<User> users = new ArrayList<>();
    private File file;
    File xmlFile;

    public UserContact() {
//        readFile();
        readXML();
    }

    private void readFile() {
        file = new File(PATH);
        List<String> roles;
        List<String> phones;
        if (file.exists() && file.canRead()) {
            try (Scanner in = new Scanner(new FileReader(file))) {
                while (in.hasNextLine()) {
                    roles = new ArrayList<>();
                    phones = new ArrayList<>();
                    String firstName = in.nextLine();
                    String lastName = in.nextLine();
                    roles.add(in.nextLine());
                    roles.add(in.nextLine());
                    roles.add(in.nextLine());
                    String email = in.nextLine();
                    phones.add(in.nextLine());
                    phones.add(in.nextLine());
                    phones.add(in.nextLine());

                    users.add(new User(firstName, lastName, roles, email, phones));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveToFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (file.setWritable(true)) {
            try (PrintWriter out = new PrintWriter(file)) {
                for (User user : users) {
                    out.println(user.getFirstName());
                    out.println(user.getLastName());
                    out.println(user.getRoles().get(0));
                    out.println(user.getRoles().get(1));
                    out.println(user.getRoles().get(2));
                    out.println(user.getEmail());
                    out.println(user.getPhones().get(0));
                    out.println(user.getPhones().get(1));
                    out.println(user.getPhones().get(2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error save file");
        }
    }

    public void readXML() {
        File xmlFile = new File(PATH_XML);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("User");

            for (int i = 0; i < nodeList.getLength(); i++) {
                users.add(getUser(nodeList.item(i)));
            }
        } catch (Exception exc) {
//            exc.printStackTrace();
            ConsoleHelper.writeMessage("The database file is missing, a new xml file will be created... ");

        }
    }

    public void saveToXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElementNS("", "Users");
            doc.appendChild(rootElement);

            for (User user : users) {
                rootElement.appendChild(getUser(doc, user.getFirstName(), user.getLastName(),
                        user.getRoles().get(0), user.getRoles().get(1), user.getRoles().get(2),
                        user.getEmail(), user.getPhones().get(0), user.getPhones().get(1), user.getPhones().get(2)));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

//            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(PATH_XML));

//            transformer.transform(source, console);
            transformer.transform(source, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User getUser(Node node) {
        User user = new User();

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            user.setFirstName(getTagValue("firstName", element));
            user.setLastName(getTagValue("lastName", element));
            user.setRoleFoXML(getTagValue("role1", element));
            user.setRoleFoXML(getTagValue("role2", element));
            user.setRoleFoXML(getTagValue("role3", element));
            user.setEmail(getTagValue("email", element));
            user.setPhoneForXML(getTagValue("phone1", element));
            user.setPhoneForXML(getTagValue("phone2", element));
            user.setPhoneForXML(getTagValue("phone3", element));
        }
        return user;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private Node getUser(Document doc, String firstName, String lastName, List<String> role, String email, List<String> phone) {
        Element user = doc.createElement("User");

        user.appendChild(getUserElements(doc, user, "firstName", firstName));
        user.appendChild(getUserElements(doc, user, "lastName", lastName));
        user.appendChild(getUserElements(doc, user, "role", role));
        user.appendChild(getUserElements(doc, user, "email", email));
        user.appendChild(getUserElements(doc, user, "phone", phone));

        return user;
    }

    private Node getUser(Document doc, String firstName, String lastName, String role1, String role2, String role3, String email, String phone1, String phone2, String phone3) {
        Element user = doc.createElement("User");

        user.appendChild(getUserElements(doc, user, "firstName", firstName));
        user.appendChild(getUserElements(doc, user, "lastName", lastName));
        user.appendChild(getUserElements(doc, user, "role1", role1));
        user.appendChild(getUserElements(doc, user, "role2", role2));
        user.appendChild(getUserElements(doc, user, "role3", role3));
        user.appendChild(getUserElements(doc, user, "email", email));
        user.appendChild(getUserElements(doc, user, "phone1", phone1));
        user.appendChild(getUserElements(doc, user, "phone2", phone2));
        user.appendChild(getUserElements(doc, user, "phone3", phone3));

        return user;
    }

    private Node getUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    private Node getUserElements(Document doc, Element element, String name, List<String> value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(String.valueOf(value)));
        return node;
    }

    public int numOfUsers() {
        if (users.size() == 0) {
            return 0;
        } else {
            return users.size();
        }

    }

    public void printAll() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void printAll(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> searchUsers(String string) {
        List<User> usersTmp = users.stream()
                .filter(x -> x.getFirstName().contains(string) || x.getLastName().contains(string) ||
                        x.getEmail().equalsIgnoreCase(string) || x.getPhones().get(0).equalsIgnoreCase(string))
                .collect(Collectors.toList());
        return usersTmp;
    }

    public void deleteUser(List<User> users) {
        for (User user : users) {
            this.users.remove(user);
        }
    }


}
