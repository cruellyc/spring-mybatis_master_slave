package com.cache.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonYmdhmsSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gr, SerializerProvider pr)
			throws IOException, JsonProcessingException {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		gr.writeString(dtf.format(date));
	}

}
