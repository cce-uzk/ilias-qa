package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.Answers;

import java.util.List;

import static seleniumUniversalTest.ToDo.randInt;


/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class SingleChoiceQuestion {
    private int status;

    /**
     * Beantwortet eine SingleChoiceFrage
     *
     * @param answer
     * @param iliasAnswerBehaviour
     */
    public void doSingleChoiceQuestion(List<Answers> answer, int iliasAnswerBehaviour) {
        status = iliasAnswerBehaviour;
        //zaehle alle moeglichen antworten
        List<WebElement> choices = ToDo.webdriver.findElements(By.name("multiple_choice_result"));
        outerloop:
        for (Answers ans : answer
                ) {
            if (iliasAnswerBehaviour == 2) {
                status = randInt(0, 1);
            }
            if ((Double.parseDouble(ans.getSetvarActionInline()) <= 0 && status == 0) || (Double.parseDouble(ans.getSetvarActionInline()) > 0 && status == 1)) {
                for (WebElement choice : choices
                        ) {
                    if(ToDo.iliasVersion.equals("5.2")) {
                        if (choice.getAttribute("id").equals("answer_"+ans.getVarequalRespidentinline())) {
                            choice.click();
                            break outerloop;
                        }
                    }else{
                        if (choice.getAttribute("id").equals(ans.getVarequalRespidentinline())) {
                            choice.click();
                            break outerloop;
                        }
                    }
                }
            }
        }
    }

}
