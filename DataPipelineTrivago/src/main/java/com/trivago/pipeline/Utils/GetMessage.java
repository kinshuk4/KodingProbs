package com.trivago.pipeline.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Chaklader on 11/20/16.
 */
public class GetMessage {


    public String welcomeTrivagoDevTeam() {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(Constant.WELCOME_FILE).getFile());

        String temp = "";

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {
                temp += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
