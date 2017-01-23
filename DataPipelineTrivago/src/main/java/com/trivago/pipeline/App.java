package com.trivago.pipeline;

import java.util.Scanner;

import com.trivago.pipeline.Utils.Constant;
import com.trivago.pipeline.Utils.GetMessage;


public class App {

    public static void main(String[] args) throws Exception {

        DataConverter dataConverter = new DataConverter(Constant.HOTEL_DATA);

        System.out.println(new GetMessage().welcomeTrivagoDevTeam());
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {

            String input = scanner.nextLine();
            System.out.println("ENTER Q OR QUIT TO QUIT \n");

            if (input.toLowerCase().equals("q") || input.toLowerCase().equals("quit")) {
                System.out.println("SEE YOU");
                System.exit(0);
            }

            try {

                int caseValue = Constant.DESIRED_OUTPUT_FILE = Integer.parseInt(input);
                switch (caseValue) {

                    case 1:
                        System.out.println("INPUT : " + input + " " + " :XML ");
                        System.out.println();
                        break;

                    case 2:
                        System.out.println("INPUT : " + input + " " + " :JSON ");
                        System.out.println();
                        break;

                    default:
                        System.out.println("OUTPUT TYPE NOT SUPPORTED ");
                }
            } catch (Exception e) {

                e.printStackTrace();
                System.exit(0);
            }

            System.out.println("IF WOULD YOU LIKE TO SORT/GROUP THE RESULT, \nENTER YES OR Y");
            System.out.println("OTHERWISE HIT ENTER TO CONTINUE");

            input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.println("LETS GET THE OUTPUT FILE \n");
                break;
            } else if (input.toLowerCase().equals("yes") || input.toLowerCase().equals("y")) {

                System.out.println("HOW WOULD YOU LIKE TO SORT THE RESULT ?");
                System.out.println("ENTER 1 FOR BASED ON NAME ");
                System.out.println("ENTER 2 FOR BASED ON THE HOTEL RATINGS");

                input = scanner.nextLine();

                if (input.toLowerCase().equals("1")) {
                    Constant.DESIRED_SORTING = 1;
                    break;
                } else if (input.toLowerCase().equals("2")) {
                    Constant.DESIRED_SORTING = 2;
                    break;
                } else
                    return;
            } else
                System.out.println("FOLLOW THE INSTRUCTIONS");
            return;
        }

        dataConverter.changeDataFormat(Constant.DESIRED_OUTPUT_FILE, Constant.DESIRED_SORTING);
    }
}
