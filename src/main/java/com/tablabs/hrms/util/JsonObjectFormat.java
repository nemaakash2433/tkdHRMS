package com.tablabs.hrms.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonObjectFormat {
	
	private String message;
	private boolean isSuccess;
	private Object  data;
}
