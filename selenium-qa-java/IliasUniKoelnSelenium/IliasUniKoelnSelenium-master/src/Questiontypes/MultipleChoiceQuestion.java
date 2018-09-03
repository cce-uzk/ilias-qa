package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.Answers;

import java.util.List;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class MultipleChoiceQuestion {
    private int status;

    public void doMultipleChoiceQuestion(List<Answers> answer, int iliasAnswerBehaviour) {
        status = iliasAnswerBehaviour;

        List<WebElement> choices = ToDo.webdriver.findElements(By.cssSelector("input[type='checkbox']"));


        for (Answers ans : answer
                ) {
            if (iliasAnswerBehaviour == 2) {
                status = ToDo.randInt(0, 1);
            }
            //varequalrespidentNot ist ein feld die so in der xml benannt wurde und steht fuer den fall das ein attribut negativ punkte vergeben wuerde
            //setvaraction ist die punktzahl die man fuer die antwort bekommt
            if (((Double.parseDouble(ans.getSetvarActionInline()) < 0 && ans.isVarequalRespidentNot() == false && status == 0) || (ans.isVarequalRespidentNot() == true && Double.parseDouble(ans.getSetvarActionInline()) > 0 && status == 0))
                    || ((Double.parseDouble(ans.getSetvarActionInline()) > 0 && ans.isVarequalRespidentNot() == false && status == 1) || (Double.parseDouble(ans.getSetvarActionInline()) < 0 && ans.isVarequalRespidentNot() == true && status == 1))) {
                {
                    for (WebElement choice : choices)
                    {
                        if(ToDo.iliasVersion.equals("5.2")) {
                            //varequalrespident ist die antwort die gegeben wird
                            if (choice.isSelected() == false && choice.getAttribute("id").equals("answer_"+ans.getVarequalRespidentinline())) {
                                choice.click();
                                break;
                            }
                        }
                        else{
                            //varequalrespident ist die antwort die gegeben wird
                            if (choice.isSelected() == false && choice.getAttribute("id").equals(ans.getVarequalRespidentinline())) {
                                choice.click();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


}
