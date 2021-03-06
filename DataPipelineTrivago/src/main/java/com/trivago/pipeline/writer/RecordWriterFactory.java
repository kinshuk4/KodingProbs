package com.trivago.pipeline.writer;

import com.trivago.pipeline.writer.impl.JsonRecordWriter;
import com.trivago.pipeline.writer.impl.SqliteSqlWriter;
import com.trivago.pipeline.writer.impl.XmlRecordWriter;
import com.trivago.pipeline.writer.impl.YamlRecordWriter;

/**
 * A factory class to return the specific writers
 * Created by tckb on 27/09/16.
 */
public class RecordWriterFactory {
	/**
	 * returns the specific writer
	 *
	 * @param rootName
	 * 		a context for this writer, this will be used by writers to define some context while writing the data
	 * @param type
	 * 		the type of writer to return; bu default, the files will be saved if serialized with this extension
	 * @return a specific implementation of the type
	 */
	public static IRecordWriter getWriter(final String rootName, final String type) {

		switch (type) {
			case "json":
				return new JsonRecordWriter(rootName, "json");
			case "xml":
				return new XmlRecordWriter(rootName, "xml");
			case "yaml":
				return new YamlRecordWriter(rootName, "yaml");
			case "sql":
				return new SqliteSqlWriter(rootName, "sql");
			default:
				throw new UnsupportedOperationException(type + " writer is not implemented.");
		}

	}
}

