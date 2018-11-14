package com.cleveroad.slidingtutorial.sample.supportsample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cleveroad.slidingtutorial.sample.R;

public class SampleSupportActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_ROLLBACK = "key_rollback";

    private boolean noRollback;

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean noRollback) {
        Intent intent = new Intent(context, SampleSupportActivity.class);
        intent.putExtra(KEY_ROLLBACK, noRollback);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bRetry).setOnClickListener(this);

        noRollback = getIntent().getBooleanExtra(KEY_ROLLBACK, false);

        if (savedInstanceState == null) {
            replaceTutorialFragment(noRollback);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRetry:
                replaceTutorialFragment(noRollback);
                break;
        }
    }

    public void replaceTutorialFragment(boolean noRollback) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CustomTutorialSupportFragment.newInstance(noRollback))
                .commit();
    }

}
