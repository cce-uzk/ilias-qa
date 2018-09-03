package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.Order;
import xmlParser.XmlReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class OrderDragDropQuestion {

    //TODO iliasantwortverhalten 0/2

    /**
     * Beantwortet eine drag and drop frage
     *
     * @param iliasAnswerBehaviour
     */
    public void doOrderDragDropQuestion(String answerTitle, int iliasAnswerBehaviour, String iliasAnswer) {
        List<WebElement> list = ToDo.webdriver.findElements(By.cssSelector("li[class='ilOrderingValue ui-sortable-handle']"));
        XmlReader xmlReader = new XmlReader();
        List<Order> order = xmlReader.createOrderAnswerList(answerTitle, iliasAnswer);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        if (iliasAnswerBehaviour == 0) {

        } else if (iliasAnswerBehaviour == 1) {
            int place = 0;
            outerloop:
            for (WebElement e : list
                    ) {
                innerloop:
                for (Order o : order
                        ) {
                    if (o.getOrderName().replace(" ", "").toLowerCase().equals(e.getText().replace(" ", "").toLowerCase())) {
                        List<WebElement> list2 = ToDo.webdriver.findElements(By.cssSelector("li[class='ilOrderingValue ui-sortable-handle']"));
                        for (int i = 0; i < list2.size(); i++) {
                            if (list2.get(i).getText().replace(" ", "").toLowerCase().equals(o.getOrderName().toLowerCase().replace(" ", ""))) {
                                place = i;
                                break;
                            }
                        }
                        if (o.getOrderPosition() < place) {
                            robot.setAutoDelay(500);
                            ToDo.action.clickAndHold(e).moveToElement(list2.get(o.getOrderPosition())).build().perform();
                            robot.keyPress(KeyEvent.VK_CONTROL);
                            ToDo.action.release().build().perform();
                            robot.keyRelease(KeyEvent.VK_CONTROL);

                        } else if (o.getOrderPosition() > place) {
                            int orderPos = o.getOrderPosition();


                            for (int j = place + 1; j <= orderPos; j++) {
                                robot.setAutoDelay(500);
                                ToDo.action.clickAndHold(list2.get(j)).moveToElement(e).build().perform();
                                robot.keyPress(KeyEvent.VK_CONTROL);
                                ToDo.action.release().build().perform();
                                robot.keyRelease(KeyEvent.VK_CONTROL);
                            }
                        }
                        break;
                    }
                }
            }
            List<WebElement> list3 = ToDo.webdriver.findElements(By.cssSelector("li[class='ilOrderingValue ui-sortable-handle']"));
            for (int i = 0; i < list3.size(); i++
                    ) {

                for (Order o : order
                        ) {
                    if (o.getOrderName().replace(" ", "").toLowerCase().equals(list3.get(i).getText().replace(" ", "").toLowerCase())) {
                        if (o.getOrderPosition() != i) {
                            //verfahren kann vorne zu Fehlern fuehren ein erneutes durchlaufen ist noetig
                            //TODO optimieren und Fehler finden
                            doOrderDragDropQuestion(answerTitle, iliasAnswerBehaviour, iliasAnswer);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < ToDo.randInt(1, list.size() - 1); i++) {
                int drag = ToDo.randInt(0, list.size() - 1);
                int drop = ToDo.randInt(0, list.size() - 1);
                ToDo.action.dragAndDrop(list.get(drag), list.get(drop)).build().perform();
                list.remove(drag);
            }
        }
    }
}
