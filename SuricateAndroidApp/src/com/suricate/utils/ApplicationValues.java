package com.suricate.utils;

import java.util.ArrayList;
import java.util.List;

import com.suricate.pojo.Acces;

public class ApplicationValues {

	private static ApplicationValues _instance = new ApplicationValues();
	public Acces _selectedBadge;
	public String WS_URL;

	public static ApplicationValues getInstance() {
		return _instance;
	}

	public List<Acces> listOfBadges;
	public List<Acces> listOfDigicodes;
	public List<Acces> listOfHistory;

	private ApplicationValues() {
		listOfBadges = new ArrayList<Acces>();
		listOfDigicodes = new ArrayList<Acces>();
		listOfHistory = new ArrayList<Acces>();
		WS_URL = Constantes.WEBSERVICES_URL_DEV;
	}

}
