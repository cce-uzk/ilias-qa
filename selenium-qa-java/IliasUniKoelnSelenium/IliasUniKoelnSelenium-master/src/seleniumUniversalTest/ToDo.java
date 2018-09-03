package seleniumUniversalTest;

import Questiontypes.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import utils.RegexMatcher;
import xmlParser.Answers;
import xmlParser.XmlReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Nils on 26.06.2015.
 */
public class ToDo
{
    public static WebDriver webdriver;
    public static Actions action;
    //test
    private XmlReader xmlReader;

    private static String rootDirectory = "C:/PerformanceTests/";
    public static String usernameFilePath= rootDirectory + "iliasuser.txt";
    public static String passwordFilePath= rootDirectory + "iliaspass.txt";
    private String baseUrlFilePath= rootDirectory + "iliasbaseurl.txt";
    private String courseNameFilePath= rootDirectory + "iliaskursname.txt";
    private String testNameFilePath= rootDirectory + "iliastestname.txt";
    private String testPasswordFilePath= rootDirectory + "iliasTestPassword.txt";
    private String pauseTimeFilePath = rootDirectory + "iliasPauseTime.txt";
    private String iliasAnswerFilePath = rootDirectory + "iliasAntwortenPfad.txt";
    private String iliasAnswerBehaviourFilePath = rootDirectory + "iliasAntwortverhalten.txt";
    private String fireFoxFilePath = rootDirectory + "firefoxPfad.txt";

    private String baseUrl;
    private String userName;
    private String userPassword;
    private String courseName;
    private String testName;
    private String testPassword;
    private String fireFoxPath;
    private int pauseTime;
    public static int loadingTime = 100;
    public String iliasAnswers;
    public final int iliasAnswerBehaviour;// 0 ->falsch 1->richtig 2->zufall
    public static String iliasVersion = null;


    /**
     * Liest alle textdateien die benoetigt werden ein und startet den browser
     */
    public ToDo(String _iliasVersion)
    {
        iliasVersion = _iliasVersion;
        userName=setWithFileReader(usernameFilePath);
        userPassword=setWithFileReader(passwordFilePath);
        baseUrl=setWithFileReader(baseUrlFilePath);
        courseName=setWithFileReader(courseNameFilePath);
        testName=setWithFileReader(testNameFilePath);
        testPassword= setWithFileReader(testPasswordFilePath);
        pauseTime = Integer.parseInt(setWithFileReader(pauseTimeFilePath));
        iliasAnswers = setWithFileReader(iliasAnswerFilePath);
        iliasAnswerBehaviour = Integer.parseInt(setWithFileReader(iliasAnswerBehaviourFilePath));
        try {
            fireFoxPath = setWithFileReader(fireFoxFilePath);
        } catch (NullPointerException npe){

        }
        if(fireFoxPath == null)
        {
            // No FireFox Config File so using default Path
            fireFoxPath = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
        }
    	
        try{
            File pathBinary = new File(fireFoxPath);
            FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            firefoxOptions.setProfile(firefoxProfile);
            //System.setProperty("webdriver.firefox.driver","C:\\Program Files (x86)\\Mozilla Firefox (v36)\\firefox.exe");
            //System.setProperty("webdriver.firefox.driver","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Selenium\\Driver\\geckodriver-v0.17.0-win64\\geckodriver.exe");

            //System.setProperty("webdriver.firefox.profile", "default");
            webdriver =  new FirefoxDriver(firefoxOptions);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        action= new Actions(webdriver);
        webdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        webdriver.manage().window().maximize(); //fullscreen
        webdriver.get(baseUrl);
    }



    /**
     * Loggt einen User ein.
     */
    public void userLogin() {
        webdriver.findElement(By.id("username")).clear();
        webdriver.findElement(By.id("username")).sendKeys(userName);
        webdriver.findElement(By.id("password")).clear();
        webdriver.findElement(By.id("password")).sendKeys(userPassword);

        if(iliasVersion.equals("5.2"))
        {
            webdriver.findElement(By.name("cmd[doStandardAuthentication]")).click();
        }
        else
        {
            webdriver.findElement(By.name("cmd[showLogin]")).click();
            try {
                Thread.sleep(loadingTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * Waehlt den Kurs aus indem der zu absolvierende Test liegt
     * Nutzt die Textdatei: iliaskursname.txt
     * !Wichtig! es kann hier nur ein Wort aus dem Namen des Kurses angegeben werden: Wahle ein unique Wort,
     * das es nur in diesem Kurs gibt. !Wichtig!
     */
    public void selectKurs()
    {
        if(iliasVersion.equals("5.2"))
        {
            try {
                if(isElementPresent(By.id("mm_desk_tr"), 5)) {
                    webdriver.findElement(By.id("mm_desk_tr")).click();
                }
                if(isElementPresent(By.id("mm_pd_crs_grp"), 5)) {
                    webdriver.findElement(By.id("mm_pd_crs_grp")).click();
                }
            } catch (Exception ex){

            }
            try {
                Thread.sleep(loadingTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //webdriver.findElement(By.name("cmd[jumpToSelectedItems]")).click();
            //webdriver.findElement(By.name("cmd[jumpToMemberships]")).click();
        }
        List<WebElement> webElements = null;
        webElements = webdriver.findElements(By.partialLinkText(courseName));
        if(webElements != null && !webElements.isEmpty()){
            for (WebElement webElement: webElements) {
                if(webElement.getAttribute("class").contains("il_ContainerItemTitle")){
                    webElement.click();
                    break;
                }
            }
        }
        try {
            Thread.sleep(loadingTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Waehlt den Test aus der absolviert werden soll
     * Nutzt die Textdatei: iliastestname.txt
     * !Wichtig! es kann hier nur ein Wort aus dem Namen des Tests angegeben werden: Wahle ein unique Wort,
     * das es nur in diesem Test gibt. !Wichtig!
     */
    public void selectTest(){
        List<WebElement> webElements = null;
        webElements = webdriver.findElements(By.partialLinkText(testName));
        if(webElements != null && !webElements.isEmpty()){
            for (WebElement webElement: webElements) {
                if(webElement.getAttribute("class").contains("il_ContainerItemTitle")) {
                    webElement.click();
                    break;
                }
            }
        }
        try {
            Thread.sleep(loadingTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void  doCheckKursAndQuestionTablePassword(){
    	WebElement webelement= webdriver.findElement(By.cssSelector("#password, #il_table_top, input[name='cmd[showQuestionList]']"));
    	
    	if(webelement.getAttribute("id").equals("password")){
    		webelement.clear();
    		webelement.sendKeys(testPassword);
    		
    		webdriver.findElement(By.name("cmd[saveEnteredPassword]")).click();
    	}

    	else if(webelement.getAttribute("id").equals("il_table_top")){
    		
    		 String[] currentUrl=webdriver.getCurrentUrl().split("/");
    	        String redirectUrl="";
    	        for(int i=0; i<(currentUrl.length-1); i++){
    	            redirectUrl=redirectUrl+currentUrl[i]+"/";
    	        }

    	        redirectUrl= redirectUrl+ "ilias.php?ref_id=67&sequence=1&active_id=288&activecommand=gotoquestion&cmd=redirectQuestion&cmdClass=iltestplayerfixedquestionsetgui&cmdNode=8j:sy:pg&baseClass=ilrepositorygui";
    	        webdriver.navigate().to(redirectUrl);
    	}
    		
    }
    


    /*public String getCorrectAnswer(){
    	 String header = webdriver.findElement(By.className("ilc_page_title_PageTitle")).getText();
    	 System.out.println(header);

    	 //----------------------------------Get Questionname----------------------------------
    	 int anfang = header.indexOf("-");
    	 int ende = header.indexOf("(");
    	 
    	 System.out.println(anfang + " | " + ende);
    	 
    	 //Prueft auf weitere aufgehende Klammern
    	 String text = header;
    	 int counter = 0;
    	 while(ende != -1){

    		 text = text.substring(ende +1, text.length());
    		 counter = counter + ende +1;
    		 
    		 //pruefe im subString auf weitere Klammern
    		 if(text.indexOf("(") != -1){
    			 ende = text.indexOf("(");
    		 }
    		 else{
    			 break;
    		 }
    		 
    	 }
    	 
    	 //Verschiebe so dass die auzuschneidenen Elemente nicht mehr drin sind
    	 anfang++;
    	 ende = counter -1;
    	 //Frage (ggf. mit bevorstehenden Leerzeichen)
    	 String questionname = header.substring(anfang, ende);
    	 
    	 //entferne vorstehende Leerzeichen
    	 while(questionname.charAt(0) == ' '){
    		 questionname = questionname.substring(1, questionname.length());
    	 }
   
    	 //entferne Leerzeichen am Ende
    	 while(questionname.charAt(questionname.length()-1) == ' '){
    		 questionname = questionname.substring(0, questionname.length()-1);
    	 }

    	 System.out.println(questionname+ "   |   "+ questionname.length());
    	 
    	 String answer = setWithFileReader("C:/PerformanceTests/Answers/" + questionname + ".txt");
    	 System.out.println("LoeSUNG: "+answer);
    	 return answer;
    }*/

    private String getQuestionSubtitle()
    {
        try{
            if(iliasVersion.equals("5.2")) {
                return webdriver.findElement(By.className("ilTestQuestionSubtitleBlocks")).getText();
            }
            else {
                return null;
            }
        }
        catch (Exception ex){
            return null;
        }
    }

    private String getQuestionTitle()
    {
        try{
            return webdriver.findElement(By.className("ilc_page_title_PageTitle")).getText();
        }
        catch (Exception ex){
            return null;
        }
    }

    private int getCurrentPosition(){
        try{
            if(iliasVersion.equals("5.2")){
                List<Integer> navigationInformation = new ArrayList<Integer>();
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(getQuestionSubtitle());

                while (m.find()) {
                    navigationInformation.add(Integer.parseInt(m.group()));
                }

                if(navigationInformation.size() == 3)
                {
                    return navigationInformation.get(0);
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    private int getNumberOfQuestions(){
        try{
            if(iliasVersion.equals("5.2")){
                List<Integer> navigationInformation = new ArrayList<Integer>();
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(getQuestionSubtitle());

                while (m.find()) {
                    navigationInformation.add(Integer.parseInt(m.group()));
                }

                if(navigationInformation.size() == 3)
                {
                    return navigationInformation.get(1);
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    private int getPointsOfQuestion(){
        try{
            if(iliasVersion.equals("5.2")){
                List<Integer> navigationInformation = new ArrayList<Integer>();
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(getQuestionSubtitle());

                while (m.find()) {
                    navigationInformation.add(Integer.parseInt(m.group()));
                }

                if(navigationInformation.size() == 3)
                {
                    return navigationInformation.get(2);
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    public static boolean isElementPresent(By selector, int timeout)
    {
        webdriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean returnVal = true;
        try{
            webdriver.findElement(selector);
        } catch (NoSuchElementException e){
            returnVal = false;
        } finally {
            webdriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        }
        return returnVal;
    }

    /**
     * Absolviert den Test
     */
    public void doTest(){
        int numberOfQuestions;
        int numberOfPendingQuestions;

        //lese antworten ein
        xmlReader = new XmlReader();
        List<Answers> answerList = xmlReader.getAnswersFromXML(iliasAnswers);
        boolean getAnswerBoolean;

        //Starttest
        if(isElementPresent(By.cssSelector("input[name='cmd[startPlayer]'],input[name='cmd[resumePlayer]']"),5 ))
    	    webdriver.findElement(By.cssSelector("input[name='cmd[startPlayer]'],input[name='cmd[resumePlayer]']")).click();
        
        try
        {
        	doCheckKursAndQuestionTablePassword();
        	doCheckKursAndQuestionTablePassword();
        }
        catch(Exception e){
        	//die einstellung mit password schutz des tests und/oder am anfang fragenuebersicht wurde nicht vorgenommen
        }

        if(iliasVersion.equals("5.2"))
        {
            numberOfPendingQuestions = getNumberOfQuestions() - (getCurrentPosition() - 1);
        }
        else
        {
            numberOfQuestions=Integer.parseInt(getQuestionTitle().substring(12,14));
            numberOfPendingQuestions = numberOfQuestions;
        }

        String questionType;
        System.out.println(numberOfPendingQuestions);

        while(numberOfPendingQuestions>0) {
            RegexMatcher regexMatcher = new RegexMatcher();
            getAnswerBoolean = false;
            int i = 0;
            List<Answers> answer = null;
            String answerTitle = null;

            //finde hier den Titel der Frage, ueber den die Frage eindeutig identifiziert werden kann um die richtige lesoung in der xml zu finden
            //der Titel muss dabei aus dem kompletten Fragentitel text extrahiert werden
            String[] questionTitleSplit;
            try {
                questionTitleSplit = getQuestionTitle().split("-");
            } catch (Exception ex) {
                break;
            }
            String questionTitle = "";
            for (int y = 0; y < questionTitleSplit.length; y++) {
                questionTitle = questionTitle + questionTitleSplit[y];
            }

            if (!iliasVersion.equals("5.2")) {
                String erg = regexMatcher.findMatch(questionTitle, "(.*)\\(");
                if (erg != null) {
                    questionTitle = erg;
                }
            }

            //suche hier die antwort zu der Frage
            questionType=checkQuestionType();
            String questionTitleCurrent = questionTitle.replace(" ", "").toLowerCase();
            if(questionType!="stack") {
                while (getAnswerBoolean == false && !questionType.equals("text")) {
                    String questionTitleQti = (answerList.get(i).getItemTitle().replace("-", "").replace(" ", "").toLowerCase());
                    if (questionTitleQti.equals(questionTitleCurrent)) {
                        getAnswerBoolean = true;
                        answer = answerList.get(i).getResponses();
                        answerTitle = answerList.get(i).getItemTitle();
                    } else {
                        if(i+1 < answerList.size()) {
                            i++;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
            }

            //Hier wird die richtige Antwort zurueckgegeben, nun muss diese in das Klickverhalten eingebunden werden...
            //getCorrectAnswer();

            System.out.println("numberOfPendingQuestions: " + numberOfPendingQuestions);

            if(answer !=null) {
                //hier fuehre nun die richtige ausfuehrungsweise fuer den Fragetypen aus und uebergebe diesem das vordefinierte Antwortverhalten unddie passende antwort
                if (questionType == "text") {
                    System.out.println("TextQuestion");
                    TextQuestion textQuestion = new TextQuestion();
                    textQuestion.doTextQuestion();
                } else if (questionType == "singlechoice") {
                    System.out.println("SinglechoiceQuestion");
                    SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion();
                    singleChoiceQuestion.doSingleChoiceQuestion(answer, iliasAnswerBehaviour);
                } else if (questionType == "multiplechoice") {
                    System.out.println("MultiplechoiceQuestion");
                    MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
                    multipleChoiceQuestion.doMultipleChoiceQuestion(answer, iliasAnswerBehaviour);
                } else if (questionType == "draganddrop") {
                    System.out.println("DragandDropQuestion");
                    DragAndDropQuestion dragAndDropQuestion = new DragAndDropQuestion();
                    dragAndDropQuestion.doDragAndDropQuestion(answer, iliasAnswerBehaviour);
                } else if (questionType == "clickSomeWords") {
                    System.out.println("ClickSomeWordsQuestion");
                    ClickSomeWordsQuestion clickSomeWordsQuestion = new ClickSomeWordsQuestion();
                    clickSomeWordsQuestion.doClickSomeWordsQuestion(answerTitle, iliasAnswerBehaviour, iliasAnswers);
                } else if (questionType == "orderDragDrop") {
                    System.out.println("OrderDragDropQuestion");
                    OrderDragDropQuestion orderDragDropQuestion = new OrderDragDropQuestion();
                    orderDragDropQuestion.doOrderDragDropQuestion(answerTitle, iliasAnswerBehaviour, iliasAnswers);
                } else if (questionType == "selectAnswerQuestion") {
                    System.out.println("selectAnswerQuestion");
                    SelectAnswerQuestion selectAnswerQuestion = new SelectAnswerQuestion();
                    selectAnswerQuestion.doSelectAnswerQuestion(answer, iliasAnswerBehaviour, 0);
                } else if (questionType == "listInputQuestion") {
                    System.out.println("listInputQuestion");
                    ListInputQuestion listInputQuestion = new ListInputQuestion();
                    listInputQuestion.doListInputQuestion(answer, iliasAnswerBehaviour);
                } else if (questionType == "numeric") {
                    System.out.println("numericQuestion");
                    NumericQuestion numericQuestion = new NumericQuestion();
                    numericQuestion.doNumericQuestion(answer, iliasAnswerBehaviour);
                } else if (questionType == "stack") {
                    System.out.println("stackQuestion");
                    StackQuestion stackQuestion = new StackQuestion();
                    stackQuestion.doStackQuestion();
                }
            }
            //naechste Frage
            try {
				Thread.sleep(pauseTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(iliasVersion.equals("5.2")){
                try{

                    if(isElementPresent(By.id("nextbutton"), 5)){
                        webdriver.findElement(By.id("nextbutton")).click();
                    } else if (isElementPresent(By.partialLinkText("Test beenden"), 5))
                    {
                        webdriver.findElement(By.partialLinkText("Test beenden")).click();
                    }

                    try {
                        Thread.sleep(loadingTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    WebElement webElement = webdriver.findElement(By.id("tst_save_on_navigation_modal"));
                    if(webElement != null){
                        // Wenn PopUp nach "Weiter"-Klick erscheint
                        if(webElement.getAttribute("aria-hidden").contains("false"))
                        {
                            /*
                                Behandlung der Meldung
                                "Diese Meldung nicht mehr anzeigen."
                                - optional -
                             */
                            //webdriver.findElement(By.name("save_on_navigation_prevent_confirmation")).click();

                            webdriver.findElement(By.id("tst_save_on_navigation_button")).click();
                        }
                    }
                }
                catch (Exception ex){

                }
            }
            else {
                webdriver.findElement(By.id("bottomnextbutton")).click();
            }
            try {
                Thread.sleep(loadingTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(iliasVersion.equals("5.2"))
                numberOfPendingQuestions = getNumberOfQuestions() - (getCurrentPosition() - 1);
            else
                numberOfPendingQuestions--;
        }

    }

    /**
     * Ueberprueft um welchen Fragetypen es sich handelt.
     * @return gibt den gefundenen Fragetypen zurueck.
     */
    public String checkQuestionType(){
        try{
            WebElement webelement=webdriver.findElement(By.cssSelector("#TEXT,input[name='gap_0'],input[name=multiple_choice_result],input[name=multiple_choice_result_0] ,div[class='draggable ui-draggable ui-droppable'],div[class='errortext ilClearFloat'],li[class='ilOrderingValue ui-sortable-handle'], div[class='cf nestable-lists'], select[name='gap_0'], input[name='TEXTSUBSET_01']"));
            System.out.println("elment: "+webelement.getAttribute("id"));
               
            try{
            if(webelement.getAttribute("id").equals("TEXT")||webelement.getAttribute("id").equals("tinymce")){

                return "null";
            }
            }catch(Exception e){
            	System.out.println(e);
            }
            
            try{
            if(webelement.getAttribute("name").equals("multiple_choice_result")){

                    return "singlechoice";
            }
            }catch(Exception e){
            	System.out.println(e);
            }
            
            try{
            if(webelement.getAttribute("name").equals("multiple_choice_result_0")){
                    return "multiplechoice";
            }
            }catch(Exception e){
            	System.out.println(e);
            }
            
            try{
            if(webelement.getAttribute("class").equals("draggable ui-draggable ui-droppable")){
            	return "draganddrop";
            }
            
            }catch(Exception e){
            	System.out.println(e);
            }
            
            try{
            if(webelement.getAttribute("class").equals("errortext ilClearFloat")){
            	return "clickSomeWords";
            }
            }catch(Exception e){
            	System.out.println(e);
            }
            
            try{
            if(webelement.getAttribute("class").equals("ilOrderingValue ui-sortable-handle") ||
                    webelement.getAttribute("class").equals("cf nestable-lists")){
            	return "orderDragDrop";
            }
            }catch(Exception e){
            	System.out.println(e);
            }
            
            try{
            if(webelement.getAttribute("name").equals("gap_0")&&webelement.getTagName().equals("select")){
            	return "selectAnswerQuestion";
            }
            }catch(Exception e){
            	System.out.println(e);
            }
            try{
            if(webelement.getAttribute("name").equals("TEXTSUBSET_01")||webelement.getAttribute("name").equals("gap_0")&&webelement.getTagName().equals("input")){
            	return "listInputQuestion";
            }
            }catch(Exception e){
            	System.out.println(e);
            }

        }
        catch(Exception e){
        	System.out.println(e);
        	return null;
        }
        System.err.println("Something went wrong");
		return null;
    }


    /**
     * Loggt den Nutzer aus Ilias aus
     */
    public void userLogout(){
        //reminder "bitte aus ilias ausloggen" weiter klicken
        if(iliasVersion.equals("5.2")){
            if(isElementPresent(By.name("cmd[finishTest]"), 5)) {
                webdriver.findElement(By.name("cmd[finishTest]")).click();
            }
        } else {
            if(isElementPresent(By.name("cmd[afterTestPassFinished]"), 5)) {
                webdriver.findElement(By.name("cmd[afterTestPassFinished]")).click();
            }
        }
        //ausloggen
        String[] currentUrl=webdriver.getCurrentUrl().split("/");
        String logoutUrl="";
        for(int i=0; i<(currentUrl.length-1); i++){
            logoutUrl=logoutUrl+currentUrl[i]+"/";
        }
        logoutUrl= logoutUrl+ "logout.php?lang=de";
        webdriver.navigate().to(logoutUrl);
        //webdriver.findElement((By.linkText("Abmelden"))).click();
    }

    /**
     * Schliesst den Browser
     */
    public void closeBrowser(){
        webdriver.close();
        webdriver.quit();
    }

    /**
     * Gibt eine Zufallszahl zwischen zwei Zahlen wieder.
     * @param min kleinste moegliche Zahl
     * @param max groesste moegliche Zahl
     * @return Gibt eine Zahl zwischen min und max wieder (einschliesslich min und max)
     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Liesst eine textdatei ein.
     * @param Pfad Gibt den Pfad an indem die Textdatei zu finden ist
     * @return Gibt den eingelesenen Text aus der datei als String zurueck
     */
    public String setWithFileReader(String Pfad){
        String readResult=null;
        File baseUrlFile= new File(Pfad);
        FileReader reader=null;
        try {
            reader = new FileReader(baseUrlFile);
            int ch;
            String giveMeTheChars="";
            while ((ch = reader.read()) != -1)
            {

                if(ch == '\n' || ch =='\t' || ch == '\r')
                {
                    //mache garnichts
                }
                else
                {
                	
                    giveMeTheChars += (char) ch;
                }
            }

            readResult = giveMeTheChars;
        }
        catch(Exception e){
            System.out.println("Exception:"+e);
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception e) {
            }
        }

        return readResult;
    }
    
}