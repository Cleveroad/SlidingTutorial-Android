package com.cleveroad.slidingtutorial.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bRetry).setOnClickListener(this);

        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRetry:
                replaceTutorialFragment();
                break;
        }
    }

    public void replaceTutorialFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new CustomTutorialFragment())
                .commit();
    }

}
