package com.suricate.nfcmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

/**
 * The kind of services a NfcManager must provide.
 * @author JRD
 */
public interface NfcManager {
	/**
	 * Get and store the nfc device adapter if present.
	 * @param activity the context we need
	 * @return true if a nfc device is present, else false
	 * @see NfcAdapter#getDefaultAdapter(android.content.Context)
	 */
	boolean initNfc(Activity activity);
	/**
	 * This is here that we enable the foreground nfc dispatch using the stored adapter.<br/>
	 * We can check here if nfc is enabled or not.<br/>
	 * This is generally done in the onResume (Activity or Fragment).
	 * @return true if nfc enabled and dispatched, else false
	 * @see #initNfc(Activity)
	 * @see NfcAdapter#enableForegroundDispatch(Activity, android.app.PendingIntent, android.content.IntentFilter[], String[][])
	 * @see #disableNfc()
	 */
	public boolean enableNfc();
	/**
	 * We disable here the foreground dispatch.<br/>
	 * This is generally done in the onPause (Activity or Fragment).
	 * @see NfcAdapter#disableForegroundDispatch(Activity)
	 * @see #enableNfc()
	 */
	void disableNfc();
	/**
	 * Verify if the current activity intent is one of the nfc actions.
	 * @return true if intent is nfc, else false
	 * @see NfcAdapter
	 * @see Intent#getAction()
	 */
	boolean isNfcIntent();
	/**
	 * This is here that we manage an nfc event (nfc callback method).<br/>
	 * @param intent the intent that contains the event informations.
	 * @see Activity#onNewIntent(Intent)
	 */
	void onNfcIntent(Intent intent);
	/**
	 * We must call this method if an external NFC event call our application.
	 * @see IntentFilter
	 */
	void onExternalNfcIntent();
}
