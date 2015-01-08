package com.suricate.activities;

import com.suricate.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShowBadgeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_badge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_badge, menu);
        return true;
    }
}
