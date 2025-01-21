package org.example.controlleradvice.dataBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GlobalDataBinder implements Converter<String, CustomDate> {

	private final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

	@Override
	public CustomDate convert(String source) {
		try {
			Date date = inputFormat.parse(source);
			CustomDate customDate = new CustomDate();
			customDate.setDate(date);
			customDate.setFormattedDate(outputFormat.format(date));
			return customDate;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
		}
	}

}