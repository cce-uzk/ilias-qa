package xmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {

    List<Answers> allResp = new ArrayList<>();
    int reprocessingPosition = 4;
    int itemTitlePosition = 2;

    public List<Answers> getAnswersFromXML(String pfad) {

        try {
            //Dokument einlesen
            File inputFile = new File(pfad);
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // fragen auslesen
            NodeList itemFatherList = doc.getElementsByTagName("item");
            XmlReader xmlReader = new XmlReader();


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return xmlReader.createResponseLists(itemFatherList);


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    public List<Order> createOrderAnswerList(String itemTitle, String pfad) {
        try {
            //Dokument einlesen
            File inputFile = new File(pfad);
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // fragen auslesen
            //NodeList itemFatherList = doc.getElementsByTagName("item");

            NodeList itemFatherList = doc.getElementsByTagName("item");
            List<Order> orderList = new ArrayList<>();

            int orderPos;
            String orderName;
            outerloop:
            for (int i = 0; i < itemFatherList.getLength(); i++) {
                if (itemFatherList.item(i).getAttributes().item(2).getNodeValue().toString().replace(" ", "").toLowerCase().equals(itemTitle.replace(" ", "").toLowerCase())) {
                    NodeList childNodes = itemFatherList.item(i).getChildNodes().item(3).getFirstChild().getFirstChild().getNextSibling().getFirstChild().getChildNodes();
                    for (int y = 0; y < childNodes.getLength(); y++) {
                        orderPos = Integer.parseInt(childNodes.item(y).getAttributes().item(0).getNodeValue());
                        orderName = childNodes.item(y).getFirstChild().getFirstChild().getFirstChild().getTextContent();
                        Order order = new Order(orderPos, orderName);
                        orderList.add(order);
                    }

                    break outerloop;
                }
            }


            return orderList;


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    private List<Answers> createResponseLists(NodeList itemFatherList) {
        List<Answers> responseList;
        responseList = null;
        for (int i = 0; i < itemFatherList.getLength(); i++
                ) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String itemTitle = itemFatherList.item(i).getAttributes().item(itemTitlePosition).getNodeValue();

            try {
                responseList = getResponseListElements(itemFatherList.item(i));
            } catch (NullPointerException noe) {
                noe.printStackTrace();
            }
            Answers answers = new Answers(itemTitle, responseList);


            allResp.add(answers);

        }
        return allResp;

    }


    private List<Answers> getResponseListElements(Node reprocessingChild) {

        List<Answers> responseList = new ArrayList<>();
        NodeList respconditionChildren = reprocessingChild.getChildNodes().item(reprocessingPosition).getChildNodes();
        String varequalRespident = null;
        String varequalRespidentInline = null;
        String setvarAction = null;
        boolean varequalRespidentNot = false;
        //4 Kind ist conditionvar, welches vater von varequal ist.


        for (int i = 1; i < respconditionChildren.getLength(); i++) {

            //Hier kann es sein das es noch eine Ebene teifer liegt, wegen einem "not"


            if (respconditionChildren.item(i).getFirstChild().getFirstChild().getNodeName() != "not" && respconditionChildren.item(i).getFirstChild().getFirstChild().getNodeName().equals("vargte") ||
                    respconditionChildren.item(i).getFirstChild().getFirstChild().getNodeName() != "not" && respconditionChildren.item(i).getFirstChild().getFirstChild().getNodeName().equals("varlte")) {

                //dieser fall bei numerischen fragen, hier wird die Range in der eine zahl gegeben wird zB 7 bis 10 im format 7,10 in varequalrespidentInline angegeben
                //respident bleibt fragetyp
                varequalRespident = respconditionChildren.item(i).getFirstChild().getFirstChild().getAttributes().item(0).getNodeValue();
                varequalRespidentInline = respconditionChildren.item(i).getFirstChild().getFirstChild().getTextContent() + "," + respconditionChildren.item(i).getFirstChild().getFirstChild().getNextSibling().getTextContent();
                varequalRespidentNot = false;
            } else if (respconditionChildren.item(i).getFirstChild().getFirstChild().getNodeName() == "not") {
                varequalRespident = respconditionChildren.item(i).getFirstChild().getFirstChild().getFirstChild().getAttributes().item(0).getNodeValue();
                varequalRespidentNot = true;
                varequalRespidentInline = respconditionChildren.item(i).getFirstChild().getFirstChild().getFirstChild().getTextContent();
            } else {
                varequalRespident = respconditionChildren.item(i).getFirstChild().getFirstChild().getAttributes().item(0).getNodeValue();
                varequalRespidentNot = false;
                varequalRespidentInline = respconditionChildren.item(i).getFirstChild().getFirstChild().getTextContent();
            }

            try {
                setvarAction = respconditionChildren.item(i).getFirstChild().getNextSibling().getTextContent();
            } catch (NullPointerException noe) {
                noe.printStackTrace();
            }

            Answers answer = new Answers(varequalRespident, varequalRespidentInline, setvarAction, varequalRespidentNot);


            try {
                responseList.add(answer);

            } catch (NullPointerException noe) {
                noe.printStackTrace();
            }

        }


        return responseList;

    }

    public String getErrorText(String itemTitle, String pfad) {
        try {
            //Dokument einlesen
            File inputFile = new File(pfad);
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // fragen auslesen
            //NodeList itemFatherList = doc.getElementsByTagName("item");

            NodeList itemFatherList = doc.getElementsByTagName("item");

            String errorText;

            for (int i = 0; i < itemFatherList.getLength(); i++) {
                if (itemFatherList.item(i).getAttributes().item(2).getNodeValue().toString().replace(" ", "").toLowerCase().equals(itemTitle.replace(" ", "").toLowerCase())) {
                    errorText = itemFatherList.item(i).getChildNodes().item(2).getFirstChild().getChildNodes().item(6).getFirstChild().getNextSibling().getTextContent();
                    //errorText=childNodes.item(2).getFirstChild().getChildNodes().item(6).getFirstChild().getNextSibling().getTextContent();
                    return errorText;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}


/**
 * Datensgtruktur der Liste mit den wichtigen Infos aus dem XML Doku:
 * 0: item title ----- Fragenüberschrift
 * 1: antworten. varequal respident// hier werden zum Biepiels "gap_0" info gespeichert
 * 2:  vareuqal inline text: text der MultipleChoide antwortmöglichkeiten
 * 3: setvar. gibt die punktzahl an die bei klicken der antwort addiert wird im inlinetext
 */
