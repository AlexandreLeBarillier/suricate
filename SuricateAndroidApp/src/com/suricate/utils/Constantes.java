package com.suricate.utils;

public final class Constantes {
	public static final String WEBSERVICES_URL_DEV = "http://192.168.180.1:8080/suricateserver/rest/";
	public static final String WEBSERVICES_URL_PROD = "http://serveur-apprentissage.ensicaen.fr:8080/suricateserver/rest/";
	
	/*
	 * URL NFC BADGE
	 */
	public static final String URL_LIST_NFC_BADGE = "nfcaccess/getlist";
	public static final String URL_ADD_NFC_BADGE = "nfcaccess/createnfcaccess";
	
	/*
	 * URL DIGICODE
	 */
	public static final String URL_LIST_DIGICODE = "pinaccess/getlist";
	public static final String URL_ADD_DIGICODE = "pinaccess/createpinaccess";
	
	/*
	 * URL HISTORY 
	 */
	public static final String URL_LIST_HISTORY = "accesslog";
	
}
