package com.vaani.gojek.parking.builder;

import java.util.List;

/**
 * Builder abstract class to generate final Response. For the assignment , Sysout Implementation has been provided. It
 * can be easily extended to provide implementations for other type of Responses For ex- In case of Web services ,
 * Another Implementation can be JsonResponse Builder
 * 
 * @author kchandra
 *
 */
public abstract class ResponseBuilder {

  public void buildResponse(String msg) {
    System.out.println(msg);
  }

  public abstract void buildTabDelimiterResponse(List<? extends Object> obList);

  public void buildCommaSeperatedResponse(List<? extends Object> res) {
    int i = 0;
    for (i = 0; i < res.size() - 1; i++) {
      System.out.print(res.get(i).toString() + ", ");
    }
    System.out.println(res.get(i).toString());

  }

}
