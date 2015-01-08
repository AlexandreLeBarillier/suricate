package com.suricate.utils;

import java.util.ArrayList;
import java.util.List;

import com.suricate.pojo.Acces;
import com.suricate.ws.WSMethod;

public class ApplicationValues {

	private static ApplicationValues _instance = new ApplicationValues();

	public static ApplicationValues getInstance() {
		return _instance;
	}

	public List<Acces> listOfBadges;
	public List<Acces> listOfDigicodes;

	private ApplicationValues() {
		listOfBadges = new ArrayList<Acces>();
		listOfDigicodes = new ArrayList<Acces>();
	}

}
