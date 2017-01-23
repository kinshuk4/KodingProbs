package com.trivago.pipeline;

import static com.trivago.pipeline.Utils.Sorter.sortDataList;
import static com.trivago.pipeline.model.Hotel.hotelsToJSON;

import com.trivago.pipeline.Utils.Constant;
import com.trivago.pipeline.model.Hotel;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by Chaklader on 11/18/16.
 */
public class DataConverter extends FileReader {

//    private StringBuilder stringBuilder;

    public DataConverter(String csvFile) {
        super(csvFile);
    }

    public void changeDataFormat(int value, int sort) {

        // sorting is required by the client
        if (sort != -1)
            sortDataList(sort, rows);

        // get the output in the desired format
        switch (value) {

            case 1:
                dataToXmlConverter();
                break;

            case 2:
                dataToJsonConverter();
                break;

            default:
                System.out.println("FILE OUTPUT TYPE IS NOT SUPPORTED");
                break;
        }
    }

    @Override
    public void dataToXmlConverter() {

        if (rows == null || rows.isEmpty())
            return;

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("info");
        doc.appendChild(rootElement);

        for (int i = 0; i < rows.size(); i++) {

            Hotel hotelData = rows.get(i);

            // name,address,stars,contact,phone,uri
            Element content = doc.createElement("row");
            rootElement.appendChild(content);

            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(i + 1));
            content.setAttributeNode(attr);

            Element name = doc.createElement(headers.getName());
            name.appendChild(doc.createTextNode(hotelData.getName()));
            content.appendChild(name);

            Element address = doc.createElement(headers.getAddress());
            address.appendChild(doc.createTextNode(hotelData.getAddress()));
            content.appendChild(address);

            Element stars = doc.createElement(headers.getStars());
            stars.appendChild(doc.createTextNode(hotelData.getStars()));
            content.appendChild(stars);

            Element contact = doc.createElement(headers.getContact());
            contact.appendChild(doc.createTextNode(hotelData.getContact()));
            content.appendChild(contact);

            Element phone = doc.createElement(headers.getPhone());
            phone.appendChild(doc.createTextNode(hotelData.getPhone()));
            content.appendChild(phone);

            Element uri = doc.createElement(headers.getUri());
            uri.appendChild(doc.createTextNode(hotelData.getUri()));
            content.appendChild(uri);
        }

        try {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(Constant.OUTPUT_LOC + "/result.xml"));
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("CONVERTED TO XML");
    }

    @Override
    public void dataToJsonConverter() {

        //  long startTime = System.currentTimeMillis();
        //  JSON CONVERSION TIME : 144 (FOR NAME BASED GROUPING)

        File file = new File(Constant.OUTPUT_LOC + "/result.json");

        String jsonValue = dataToJsonConverterHelper(rows);

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = jsonValue.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("CONVERTED TO JSON");
        } 

        catch (IOException e) {
            e.printStackTrace();
        }

        // long estimatedTime = System.currentTimeMillis() - startTime;
        // System.out.println("JSON CONVERSION TIME : " + estimatedTime);
    }

    public String dataToJsonConverterHelper(List<Hotel> rows) {

        String jsonValue = "";

        if (rows == null || rows.isEmpty())
            return jsonValue;

        jsonValue = hotelsToJSON(rows);
        return jsonValue;
    }


//    @Override
//    public void dataToJsonConverter() {
//
//        ObjectMapper mapper = new ObjectMapper();


//        long startTime = System.currentTimeMillis();
//        JSON CONVERSION TIME : 8114 (FOR NAME BASED GROUPING)

//        try {
//            String jsonInString = "";
//
//            for (HotelData hotels : rows) {
//                jsonInString += mapper.writeValueAsString(hotels);
//            }
//            mapper.writeValue(new File(Constant.OUTPUT_LOC + "/result.json"), jsonInString);
//        }



//        long startTime = System.currentTimeMillis();
    //        JSON CONVERSION TIME : 332 (FOR NAME BASED GROUPING)

    // better performance using the StringBuilder
//        try {
//            stringBuilder = new StringBuilder();
//
//            for (HotelData hotels : rows) {
//                stringBuilder.append(mapper.writeValueAsString(hotels).trim());
//            }
//            mapper.writeValue(new File(Constant.OUTPUT_LOC + "/result.json"), stringBuilder.toString());
//        }



//        catch (JsonGenerationException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        long estimatedTime = System.currentTimeMillis() - startTime;
//        System.out.println("JSON CONVERSION TIME : " + estimatedTime);

//        System.out.println("CONVERTED TO JSON");
//    }

}

