package seleniumUniversalTest;

/**
 * Created by nadimo on 17.07.2017.
 */
public class Seleniumtest
{
    public static void main(String[]args)
    {
        ToDo toDo= new ToDo("5.2");

        //logge den User ein
        toDo.userLogin();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //waehle den Kurs aus in dem der Test liegt
        toDo.selectKurs();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //wahle den TEst aus der absolviert werden soll
        toDo.selectTest();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //absolviere den Test
        toDo.doTest();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //loge den Benutzer aus
        toDo.userLogout();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // schliesse den Browser
        toDo.closeBrowser();
    }
}
