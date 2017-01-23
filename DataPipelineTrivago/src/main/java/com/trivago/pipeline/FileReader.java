package com.trivago.pipeline;

import static com.trivago.pipeline.Utils.Formatter.parseLine;
import static com.trivago.pipeline.Utils.Validator.isNameIsUTF8;
import static com.trivago.pipeline.Utils.Validator.isUrlValidated;
import static com.trivago.pipeline.Utils.Validator.isValidHotelRating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.trivago.pipeline.Interfaces.FileReadable;
import com.trivago.pipeline.Interfaces.JsonFileConvertable;
import com.trivago.pipeline.Interfaces.XmlFileConvertable;
import com.trivago.pipeline.Utils.FileFinder;
import com.trivago.pipeline.model.Headers;
import com.trivago.pipeline.model.Hotel;


/**
 * Created by Chaklader on 11/18/16.
 */
public abstract class FileReader implements FileReadable, 
                                            XmlFileConvertable, 
                                            JsonFileConvertable {

    protected List<Hotel> rows;
    protected Hotel hotelData;
    protected Headers headers;
    private String fileName = null;

    public FileReader(String fileName) {

        this.fileName = fileName;
        this.rows = new ArrayList<>();
        this.hotelData = null;
        this.headers = null;

        fileReader();
    }

    public void fileReader() {

        try {
            readCsvFile();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void readCsvFile() throws FileNotFoundException {

        File file = new FileFinder().getTheFile(fileName);

        try (Scanner scanner = new Scanner(file)) {

            List<String> line = parseLine(scanner.nextLine());

            if (line != null) {

                // name,address,stars,contact,phone,uri
                headers = new Headers(line.get(0),
                        line.get(1),
                        line.get(2),
                        line.get(3),
                        line.get(4),
                        line.get(5));
            }

            while (scanner.hasNext()) {

                line = parseLine(scanner.nextLine());

                if (line != null) {

                    // name,address,stars,contact,phone,uri
                    String name = line.get(0);
                    String address = line.get(1);
                    String stars = line.get(2);
                    String contact = line.get(3);
                    String phone = line.get(4);
                    String uri = line.get(5);

                    boolean nameValidated = isNameIsUTF8(name);
                    boolean urlIsValidated = isUrlValidated(uri);
                    boolean hotelRatingValidated = isValidHotelRating(stars);

                    // name, uri and the hotel rating validated
                    if (nameValidated && urlIsValidated && hotelRatingValidated) {
                        hotelData = new Hotel(name, address, stars, contact, phone, uri);
                        rows.add(hotelData);
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
