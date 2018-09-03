package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.Answers;

import java.util.List;
import java.util.Random;

/**
 * Created by EPC-Nils on 17.05.2016.
 */
public class NumericQuestion {

    //TODO fertigstellen

    public void doNumericQuestion(List<Answers> answer, int iliasAnswerBehaviour) {
        List<WebElement> gaps = ToDo.webdriver.findElements(By.cssSelector("input[name='numeric_result']"));
        String[] erg;
        Random rand = new Random();

        if (iliasAnswerBehaviour == 0) {

        } else if (iliasAnswerBehaviour == 1) {
            for (int i = 0; i < gaps.size(); i++
                    ) {
                erg = answer.get(i).getVarequalRespidentinline().split(",");
                Double randomNum = Double.parseDouble(erg[0]) + (Double.parseDouble(erg[1]) - Double.parseDouble(erg[0])) * rand.nextDouble();
                gaps.get(i).sendKeys(randomNum + "");
            }
        } else {

        }
    }
}
