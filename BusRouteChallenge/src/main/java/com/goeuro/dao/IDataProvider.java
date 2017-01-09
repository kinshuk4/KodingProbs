package com.goeuro.dao;


import java.io.IOException;
import java.util.List;

import com.goeuro.model.BusStop;


public interface IDataProvider {

    List<List<BusStop>> fetch() throws IOException;
}
