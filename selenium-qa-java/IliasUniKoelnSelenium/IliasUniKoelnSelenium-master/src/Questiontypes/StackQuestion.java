package Questiontypes;

import org.openqa.selenium.By;
import seleniumUniversalTest.ToDo;

/**
 * Created by EPC-Nils on 07.03.2017.
 */
public class StackQuestion {

    public void doStackQuestion(){
        ToDo.webdriver.findElement(By.cssSelector("input[type='text']")).sendKeys("a^b*c/w^g^g");

        ToDo.webdriver.findElement(By.cssSelector("input[value='Validieren']")).click();

    }
}
