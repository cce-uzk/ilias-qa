package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.XmlReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class ClickSomeWordsQuestion {
    private int status;

    public void doClickSomeWordsQuestion(String answerTitle, int iliasAnswerBehaviour, String iliasAnswers) {
        status = iliasAnswerBehaviour;
        List<WebElement> list = ToDo.webdriver.findElements(By.cssSelector("a[href='#']"));

        Iterator<WebElement> iter = list.iterator();

        //entferne leere Webelemente
        while (iter.hasNext()) {
            WebElement e = iter.next();

            if (e.getText().isEmpty())
                iter.remove();
        }

        //errortext == text der in dem Feld steht der anklickbar ist
        XmlReader xmlReader = new XmlReader();
        String errorText = xmlReader.getErrorText(answerTitle, iliasAnswers);
        String[] errorTextArray = errorText.split("\\s");
        List<String> errorTextList = new ArrayList<String>(Arrays.asList(errorTextArray));
        errorTextList.removeAll(Arrays.asList(""));
        errorTextArray = errorTextList.toArray(errorTextArray);
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (iliasAnswerBehaviour == 2) {
                status = ToDo.randInt(0, 1);
            }
            //klicke hier, je nach einstellung entweder falsche oder || richtige woerter an
            //2 bedingung sagt wenn errortext nicht gleich ist, da richtige antworten sich im errortext vom text im html unterscheiden
            if ((errorTextArray[i].toLowerCase().equals(list.get(i).getText().toLowerCase()) && count < errorTextArray.length && status == 0) || (!errorTextArray[i].toLowerCase().equals(list.get(i).getText().toLowerCase()) && status == 1)) {
                list.get(i).click();
                count++;
            }
        }


    }
}
