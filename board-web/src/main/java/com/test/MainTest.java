package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		System.out.println(format.format(date));
		
	}

}
