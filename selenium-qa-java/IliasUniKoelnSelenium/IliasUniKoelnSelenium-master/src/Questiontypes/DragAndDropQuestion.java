package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.Answers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class DragAndDropQuestion
{

    private int status;

    public void doDragAndDropQuestion(List<Answers> answer, int iliasAnswerBehaviour) {
        try {
            ((JavascriptExecutor) ToDo.webdriver).executeScript("document.body.style.zoom = '70%';");
        } catch (Exception ex){

        }

        try {
            Thread.sleep(ToDo.loadingTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        status = iliasAnswerBehaviour;
        //finde die drag und drop elemente
        List<WebElement> dragElements = ToDo.webdriver.findElements(By.cssSelector("div[class='draggable'], div[class='draggable ui-draggable ui-droppable']"));
        List<WebElement> dropElements = ToDo.webdriver.findElements(By.cssSelector("div[class='droparea'], div[class='droparea ui-droppable ui-droppable-disabled ui-state-disabled'], div[class='droparea ui-droppable']"));

        try {
            Thread.sleep(ToDo.loadingTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(ToDo.loadingTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sp = null;
        for (WebElement element : dropElements)
        {
            try {
                Thread.sleep(ToDo.loadingTime); //1000
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (iliasAnswerBehaviour == 2) {
                status = ToDo.randInt(0, 1);
            }

            //finde das paarung fuer drop element zu drag  element
            for (int i = 0; i < answer.size(); i++
                    ) {
                String ansSet[] = answer.get(i).getVarequalRespidentinline().split(",");
                if ((!element.getAttribute("data-id").equals(ansSet[1]) && status == 0) || (element.getAttribute("data-id").equals(ansSet[1]) && status == 1)) {
                    sp = ansSet[0];
                    answer.remove(i);
                    try {
                        Thread.sleep(ToDo.loadingTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                   break;
                }
            }

            //ziehe hier nun das passende drag element in das dropelement hinein
            for (WebElement drag : dragElements)
            {
                if (sp.equals(drag.getAttribute("data-id"))) {
                    scrollToElement(drag);
                    try
                    {
                        Thread.sleep(ToDo.loadingTime);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    robot.setAutoDelay(700);
                    int count = 0;
                    int maxTries = 10;
                    boolean successState = false;
                    while(!successState) {
                        try {
                            ToDo.action.clickAndHold(drag).moveToElement(element).build().perform();
                            robot.keyPress(KeyEvent.VK_CONTROL);
                            ToDo.action.release().build().perform();
                            robot.keyRelease(KeyEvent.VK_CONTROL);

//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }

                            successState = true;
                        } catch (Exception e) {
                            if (++count == maxTries) throw e;
                        }
                    }
                    break;
                }
                try
                {
                    Thread.sleep(ToDo.loadingTime);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                Thread.sleep(ToDo.loadingTime);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        ToDo.webdriver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
        try {
            ((JavascriptExecutor) ToDo.webdriver).executeScript("document.body.style.zoom = '100%';");
        } catch (Exception ex){

        }
    }

    private void scrollToElement(WebElement el) {
        if (ToDo.webdriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) ToDo.webdriver)
                    .executeScript("arguments[0].scrollIntoView(true);", el);
        }
    }
}
