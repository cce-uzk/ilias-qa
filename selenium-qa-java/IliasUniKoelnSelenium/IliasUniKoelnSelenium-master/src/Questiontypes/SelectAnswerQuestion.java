package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import seleniumUniversalTest.ToDo;
import xmlParser.Answers;

import java.util.List;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class SelectAnswerQuestion {
    private int status;

    public void doSelectAnswerQuestion(List<Answers> answers, int iliasAnswerBehaviour, int j) {
        status = iliasAnswerBehaviour;
        //suche alle select input felder
        List<WebElement> listOfPickWindows = ToDo.webdriver.findElements(By.tagName("select"));
        for (WebElement picker : listOfPickWindows) {
            if (iliasAnswerBehaviour == 2) {
                status = ToDo.randInt(0, 1);
            }
            Select option = new Select(picker);
            //finde die elemente die auswaehlbar sind fuer das Feld
            List<WebElement> listOfOptions = option.getOptions();
            Answers result = null;
            //Finde antwort fuer das passende Antwortverhalten, hierfuer waehle entweder eine Option die die meisten Punkte bringt oder eben eine die keine bringt

            //waehle hier die antwort im html aus
            for (WebElement element : listOfOptions)
            {
                String parentElementName = element.findElement(By.xpath("..")).getAttribute("name");

                for (Answers answer : answers)
                {
//                    if ((Double.parseDouble(answer.getSetvarActionInline()) <= 0 && Integer.parseInt(answer.getVarequalRespident().split("_")[1]) == j && status == 0) ||
//                            (Double.parseDouble(answer.getSetvarActionInline()) > 0 && Integer.parseInt(answer.getVarequalRespident().split("_")[1]) == j && status == 1)) {

                    if(Double.parseDouble(answer.getSetvarActionInline()) > 0)
                    {
                        if (parentElementName.equals(answer.getVarequalRespident())) {
                            result = answer;
                            break;
                        }
                    }
                        //j++;
                   // }
                }
                String optionValue = element.getText();
                String resultValue = result.getVarequalRespidentinline();
                String optionTag = element.getTagName();

                if(optionTag.equals("option"))
                    if (resultValue.toLowerCase().replace(" ", "").equals(optionValue.toLowerCase().replace(" ", ""))) {
                        try {
                            Thread.sleep(ToDo.loadingTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            element.clear();
                        } catch (Exception ex){

                        }
                        element.click();
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
                Thread.sleep(150);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        String counter = "";
        for (int i = 0; i < 20; i++) {
            counter = ""+i;
            if (i < 10)
                counter = "0"+i;
            try {
                String searchString = String.format("#TEXT,input[name='gap_%1$s'],input[name=multiple_choice_result],input[name=multiple_choice_result_%1$s] ,div[class='draggable ui-draggable ui-droppable'],div[class='errortext ilClearFloat'],li[class='ilOrderingValue ui-sortable-handle'], select[name='gap_%1$s'], input[name='TEXTSUBSET_%2$s']",""+i, counter);
                By by = By.cssSelector(searchString);
                if(ToDo.isElementPresent(by, 2)) {
                    WebElement webelement = ToDo.webdriver.findElement(by);

                    if (webelement.getAttribute("name").equals("TEXTSUBSET_" + counter) || webelement.getAttribute("name").equals("gap_" + i) && webelement.getTagName().equals("input"))
                    {
                        System.out.println("listInputQuestion");
                        ListInputQuestion listInputQuestion = new ListInputQuestion();
                        listInputQuestion.doListInputQuestion(answers, iliasAnswerBehaviour);
                        break;
                    }
                }
            } catch (Exception ex) {

            }
        }
    }
}
