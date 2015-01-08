package com.suricate.utils;

import java.util.ArrayList;
import java.util.List;

import com.suricate.pojo.Acces;

public class ApplicationValues {
	
	private static ApplicationValues _instance = new ApplicationValues();
	
	public List<Acces> listOfBadges;
	public List<Acces> listOfDigicodes;

	private ApplicationValues() {
		listOfBadges = new ArrayList<Acces>();
		listOfDigicodes = new ArrayList<Acces>();
	}

	public static ApplicationValues getInstance() {
		return _instance;
	}

}
