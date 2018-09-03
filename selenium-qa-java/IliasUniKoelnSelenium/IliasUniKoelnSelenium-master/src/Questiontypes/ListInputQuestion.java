package Questiontypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import seleniumUniversalTest.ToDo;
import xmlParser.Answers;

import java.util.List;

/**
 * Created by EPC-Nils on 26.04.2016.
 */
public class ListInputQuestion {
    private int status;

    public void doListInputQuestion(List<Answers> answer, int iliasAnswerBehaviour)
    {
        status = iliasAnswerBehaviour;
        List<WebElement> textWindows = ToDo.webdriver.findElements(By.cssSelector("input[type='text']")); //("input[type='text'], select"));
        boolean selectionDone = false;


        Answers max = null;
        double maxDouble = 0;
        int i = 0;
        for (WebElement element: textWindows)
        {
            int j = 0;
            try {
                j = Integer.parseInt(element.getAttribute("name").split("_")[1]);
            } catch (Exception ex){

            }
            //fuer alle inputfelder
            //for (int j = 0; j < textWindows.size(); j++)
            //{
                if (iliasAnswerBehaviour == 2)
                {
                    status = ToDo.randInt(0, 1);
                }

                //hier kann es sein, dass zwischen den Listinputfeldern auch selcet felder sin, diese muessen dann mit der selectanswerquastion bearbeitet werden
                if (element.getTagName().equals("select") && selectionDone == false) {
                    SelectAnswerQuestion selectAnswerQuestion = new SelectAnswerQuestion();
                    selectAnswerQuestion.doSelectAnswerQuestion(answer, iliasAnswerBehaviour, j);
                    selectionDone = true;

                    //hier kein select feld und falsche antwort geben
                } else if ((element.getAttribute("name").contains("gap") && !element.getTagName().equals("select") && status == 0) || (element.getAttribute("name").contains("TEXTSUBSET") && status == 0))
                {
                    //f repraesentiert die falsche antwort
                    element.sendKeys("f");

                    //hier richtige antwort eintragen
                } else if (element.getAttribute("name").contains("gap") && !element.getTagName().equals("select") && status == 1) {
                    maxDouble = 0;
                    element.clear();
                    for (Answers ans : answer
                            ) {
                        //hier finde die antwort die am meisten punkte gibt
                        if (Double.parseDouble(ans.getSetvarActionInline()) > 0 && Integer.parseInt(ans.getVarequalRespident().split("_")[1]) == j && Double.parseDouble(ans.getSetvarActionInline()) > maxDouble) {
                            max = ans;
                            maxDouble = Double.parseDouble(ans.getSetvarActionInline());
                        }
                    }

                    element.sendKeys(max.getVarequalRespidentinline());

                } else if (element.getAttribute("name").contains("TEXTSUBSET") && status == 1) {
                    element.clear();
                    maxDouble = 0;
                    for (Answers ans : answer
                            ) {
                        if (Double.parseDouble(ans.getSetvarActionInline()) > 0 && Integer.parseInt(ans.getVarequalRespident().split("_")[1]) - 1 == j && Double.parseDouble(ans.getSetvarActionInline()) > maxDouble) {
                            //System.out.println(Integer.parseInt(ans.getVarequalRespident().split("_")[1]));
                            max = ans;
                            maxDouble = Double.parseDouble(ans.getSetvarActionInline());

                        }
                    }
                    //verschiedene Antworten moeglichen, daher waehle nur eine von diesen
                    String[] answerChoices = max.getVarequalRespidentinline().split(",");
                    element.sendKeys(answerChoices[i]);
                    i++;
                }
            //}
        }

    }


}
