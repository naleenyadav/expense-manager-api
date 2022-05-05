package com.naleen.expensetrackerapi.exception;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ErrorObject {

	private int statusCode;
	private String message;
	private Timestamp timeStamp;

}
