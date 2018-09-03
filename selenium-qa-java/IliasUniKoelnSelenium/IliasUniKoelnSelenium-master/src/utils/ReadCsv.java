package utils;

import seleniumUniversalTest.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by EPC-Nils on 22.09.2016.
 */
public class ReadCsv {

    String csvPath = "C://PerformanceTests/IpAdressListAachen.csv";


    public String getLocalIP() {

        String address = null;
        try {
            address = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(address);
        return address;
    }

    public void writeToFile(String text, String filePath) {
        try {
            PrintWriter out = new PrintWriter(filePath);
            out.print(text);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readCsvAndWriteToFile() {
        String line;
        String name;
        String pw;
        String ip = getLocalIP();
        try (Scanner scanner = new Scanner(new File(csvPath))) {
            scanner.useDelimiter(";");
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.split(";")[0].equals(ip)) {
                    name = line.split(";")[1];
                    System.out.println(name);
                    writeToFile(name, ToDo.usernameFilePath);

                    pw = line.split(";")[2];
                    System.out.println(pw);
                    writeToFile(pw, ToDo.passwordFilePath);
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
