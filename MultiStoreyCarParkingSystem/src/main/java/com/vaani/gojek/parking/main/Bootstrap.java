package com.vaani.gojek.parking.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vaani.gojek.entity.type.VehicleType;
import com.vaani.gojek.parking.orchestrator.Orchestrator;

/**
 * Bootstrap class to read input from command line and file Input to call the underlying functionality. Please note that
 * this code is the client to the carParking software and can be used to test API's written for the assignment.
 * 
 * @author kchandra
 */
public class Bootstrap {

  public static void main(String[] args) throws IOException {
    BufferedReader br = null;
    Orchestrator or = new Orchestrator();
    if (args.length > 0) {
      String fileName = args[0];
      br = new BufferedReader((new FileReader(fileName)));
    } else {
      br = new BufferedReader(new InputStreamReader(System.in));
    }
    String command;
    String color;
    String regNumber;
    while ((command =br.readLine()) != null) {
      String[] commandArray = command.split(" ");
      String commandPrefix = commandArray[0];

      switch (commandPrefix) {
        case "create_parking_lot":
          int n = Integer.parseInt(commandArray[1]);
          or.createParkingLot(n);
          break;
        case "park":
          regNumber = commandArray[1];
          color = commandArray[2];
          // introduced a vehicleType to showcase
          // how implementation can be extended
          or.ParkVehicle(regNumber, color, VehicleType.Car);
          break;
        case "leave":
          int slotNumber = Integer.parseInt(commandArray[1]);// gets slot Number
          or.freeSlot(slotNumber);
          break;
        case "status":
          or.getParkingStatus();
          break;
        case "registration_numbers_for_cars_with_colour":
          color = commandArray[1];
          or.getRegistrationNumbers(color);
          break;
        case "slot_numbers_for_cars_with_colour":
          color = commandArray[1];
          or.getSlotNumbersForColor(color);
          break;
        case "slot_number_for_registration_number":
          regNumber = commandArray[1];
          or.getSlotNumbersForRegistrationNumbers(regNumber);
          break;
        default:
          /**
           * throwing an exception here will cause the program to stop if one does not want it to stop , please comment
           * exception code and instead uncomment Sysout written below . In production code , one would always write
           * exception so going with that here
           **/
          System.out.println("Invalid command . There is no support for this command");
      }

    }
    br.close();

  }

}
