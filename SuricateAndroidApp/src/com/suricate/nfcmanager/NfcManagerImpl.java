package com.suricate.nfcmanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;

/**
 * A partial implementation of the NfcManager interface.
 * @author JRD
 *
 */
public abstract class NfcManagerImpl implements NfcManager {

	protected NfcAdapter mAdapter;
	protected Activity mActivity;
	private int iNfcCount=0;

	@Override
	public boolean initNfc(Activity activity) {
		this.mActivity = activity;
		//////////////////////////////////////////////
		//////////A COMPLETER
		mAdapter = NfcAdapter.getDefaultAdapter(this.mActivity);
//		mAdapter = NfcAdapter.getDefaultAdapter(mActivity.getApplicationContext());

		//////////////////////////////////////////////
		return mAdapter != null;
	}

	@Override
	public boolean enableNfc() {
		iNfcCount=iNfcCount+1;
		if (iNfcCount==20) return false;

		//////////////////////////////////////////////
		//////////A COMPLETER
		if(mAdapter == null)
			return false;

		if(mAdapter.isEnabled()){
			Intent intent = new Intent(mActivity, mActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(mActivity.getApplicationContext(), 0, intent, 0);
			mAdapter.enableForegroundDispatch(mActivity, pendingIntent, null, null);
		}
		else{
			return false;
		}

		//////////////////////////////////////////////
		return true;
	}

	@Override
	public void disableNfc() {
		//////////////////////////////////////////////
		//////////A COMPLETER

		if( mAdapter != null){
			if(mAdapter.isEnabled()){
				mAdapter.disableForegroundDispatch(mActivity);
			}
		}


		//////////////////////////////////////////////
	}

	@Override
	public boolean isNfcIntent() {
		//////////////////////////////////////////////
		//////////A COMPLETER
		Intent intent = mActivity.getIntent();

		String action = intent.getAction();
		if(action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)
				|| action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)
				|| action.equals(NfcAdapter.ACTION_TECH_DISCOVERED)){
			return true;

		}

		return false;
	}

	@Override
	public void onExternalNfcIntent() {

		Intent intent = mActivity.getIntent();
		if(isNfcIntent())
			onNfcIntent(intent);

	}

	@Override
	public abstract void onNfcIntent(Intent intent);
}
