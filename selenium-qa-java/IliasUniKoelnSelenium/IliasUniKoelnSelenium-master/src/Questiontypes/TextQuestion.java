package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class TextQuestion {
    /**
     * Beantwortet eine Textfrage
     *
     */
    public void doTextQuestion() {
        WebElement textFeld = ToDo.webdriver.findElement(By.cssSelector("#TEXT"));

        //fuer Ilias 5.0 und vorgaenger gibt es unterschiedliche weisen in das Textfeld zu schrieben, deshalb, probiere zuerst die ein und dann die andere
        try {
            fillFreeText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.v");
        } catch (Exception e) {
            textFeld.sendKeys("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.v");
        }


    }

    public void fillFreeText(String text) {
        //Wechsel in das richtige Frame
        WebElement frame = ToDo.webdriver.findElement(By.id("TEXT_ifr"));
        ToDo.webdriver.switchTo().frame(frame);
        //und in das richtige Feld
        WebElement textfield = ToDo.webdriver.findElement(By.cssSelector("body"));

        //sende die Keys an das Textfeld
        textfield.sendKeys("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        //setze das Frame zur?ck auf den default Wert!
        ToDo.webdriver.switchTo().defaultContent();
    }

}
