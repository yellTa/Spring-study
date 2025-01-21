package org.example.controlleradvice.dataBinding;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CustomDate {

	private Date date;
	private String formattedDate;

	@Override
	public String toString() {
		return "CustomDateObject{date=" + date + ", formattedDate='" + formattedDate + "'}";
	}

}
