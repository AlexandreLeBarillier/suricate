package com.suricate.activities;

import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.Toast;

import com.suricate.R;
import com.suricate.nfcmanager.NfcManager;
import com.suricate.nfcmanager.NfcManagerImpl;
import com.suricate.utils.ApplicationValues;

public class CopyBadgeActivity extends Activity implements OnKeyListener, NfcManager {
        private NfcManager nfcm;
        private Vibrator vibe;
        private ProgressDialog prd = null;
        private boolean waitForWrite = false;
        private Button button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_copy_badge);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                // Show the Up button in the action bar.

                setupActionBar();

                // Nfc fragment insertion and initialisation
                nfcm = new NfcManagerImpl() {
                    @Override
                    public void onNfcIntent(Intent intent) {

                            String action = intent.getAction();
                            Log.d(this.getClass().getSimpleName(), "write tag action="+action);
                            if (waitForWrite) {
                                    if (isNfcIntent()) {
                                            if (prd != null) prd.dismiss();
                                            writeTag(intent);
                                    }
                            }
                    }
                };

                if (nfcm.initNfc(this) == false) {
                        Toast.makeText(this, getString(R.string.device_not_nfc), Toast.LENGTH_LONG).show();
                        return;
                }

                button = (Button) findViewById(R.id.writetag);
                button.setEnabled(false);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickButton(v);
                    }
                });
        }

        private boolean writeTag(Intent intent) {
            String idString = "#" + ApplicationValues.getInstance()._selectedBadge.getCode()
                    + "#";
            byte[] id = idString.getBytes();
            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, new byte[0],id , new byte[0]);
            NdefMessage nDefMessage = new NdefMessage(record);
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Ndef ndef = Ndef.get(tag);
            if(ndef != null && ndef.isWritable()){
                    try {
                            ndef.connect();
                            ndef.writeNdefMessage(nDefMessage);
                    } catch (FormatException e) {
                            e.printStackTrace();
                    } catch (IOException e) {
                            e.printStackTrace();
                    }finally{
                            try {
                                    ndef.close();
                            } catch (IOException e) {
                                    e.printStackTrace();
                            }
                    }
            }

            Toast.makeText(this, "Votre badge a été crée avec succès", Toast.LENGTH_LONG).show();
            return true;
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.activity_copy_badge, menu);
                return true;
        }


        /**
         * Set up the {@link android.app.ActionBar}, if the API is available.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void setupActionBar() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        getActionBar().setDisplayHomeAsUpEnabled(true);
                }
        }

        /**
         * On resume we enable the NFC adapter.<br/>
         * Plus the activity will not be launched if it is already running at the top of the history stack.
         */
        @Override
        public void onResume()
        {
                super.onResume();

                if (nfcm.enableNfc() == false) {
                        Toast.makeText(this, getString(R.string.nfc_disabled), Toast.LENGTH_SHORT).show();
                        finish();
                }
        }

        /**
         * On pause we disable the nfc adapter.
         */
        @Override
        protected void onPause() {
                super.onPause();
                nfcm.disableNfc();
        }

        /**
         * On tag tap we try to write the tag if writing is on hold.
         */
        @Override
        public void onNewIntent(Intent intent)
        {
                setIntent(intent);
                vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(50);

                nfcm.onNfcIntent(intent);
                button.setEnabled(true);
        }

        public void onClickButton(View v) {
                if (v == button) {
                        Intent intent =  getIntent();

                        String action = intent.getAction();
                        Log.d(this.getClass().getSimpleName(), "write click action="+action);

                        if (isNfcIntent()) {
                                writeTag(intent);
                                waitForWrite = false;

                        } else {
                                waitForWrite = true;
                                prd = ProgressDialog.show(this, getString(R.string.taptag_title), getString(R.string.taptag_message));
                                prd.setCancelable(true);
                        }
                }

        }


        /**
         * Detection of the keyboard Ok event
         */
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        return true;
                }
                return false;
        }

        @Override
        public boolean initNfc(Activity activity) {
                return nfcm.initNfc(activity);
        }

        @Override
        public boolean enableNfc() {
                return nfcm.enableNfc();
        }

        @Override
        public void disableNfc() {
                nfcm.disableNfc();
        }

        @Override
        public void onExternalNfcIntent() {
                nfcm.onExternalNfcIntent();
        }

        @Override
        public void onNfcIntent(Intent intent) {
                nfcm.onNfcIntent(intent);
        }

        @Override
        public boolean isNfcIntent() {
                return nfcm.isNfcIntent();
        }
}