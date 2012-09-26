package de.greenrobot.eventperf;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import de.greenrobot.event.ThreadMode;
import de.greenrobot.eventperf.testsubject.TestEventBus;

public class TestSetupActivity extends Activity {

    @SuppressWarnings("rawtypes")
    static final Class[] TEST_CLASSES_EVENTBUS = { TestEventBus.Post.class, TestEventBus.Register.class };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuptests);

        Spinner spinnerRun = (Spinner) findViewById(R.id.spinnerTestToRun);
        spinnerRun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View v, int pos, long lng) {
                boolean hasEventCount = pos == 0;
                findViewById(R.id.relativeLayoutForEvents).setVisibility(hasEventCount ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void checkEventBus(View v) {
        Spinner spinnerThread = (Spinner) findViewById(R.id.spinnerThread);
        CheckBox checkBoxEventBus = (CheckBox) findViewById(R.id.checkBoxEventBus);
        int visibility = checkBoxEventBus.isChecked() ? View.VISIBLE : View.GONE;
        spinnerThread.setVisibility(visibility);
    }

    public void startClick(View v) {
        TestParams params = new TestParams();
        Spinner spinnerThread = (Spinner) findViewById(R.id.spinnerThread);
        String threadModeStr = spinnerThread.getSelectedItem().toString();
        ThreadMode threadMode = ThreadMode.valueOf(threadModeStr);
        params.setThreadMode(threadMode);

        EditText editTextEvent = (EditText) findViewById(R.id.editTextEvent);
        params.setEventCount(Integer.parseInt(editTextEvent.getText().toString()));

        EditText editTextSubscriber = (EditText) findViewById(R.id.editTextSubscribe);
        params.setSubscriberCount(Integer.parseInt(editTextSubscriber.getText().toString()));

        Spinner spinnerTestToRun = (Spinner) findViewById(R.id.spinnerTestToRun);
        int testPos = spinnerTestToRun.getSelectedItemPosition();
        params.setTestNumber(testPos + 1);
        ArrayList<Class<? extends Test>> testClasses = initTestClasses(testPos);
        params.setTestClasses(testClasses);

        Intent intent = new Intent();
        intent.setClass(this, TestRunnerActivity.class);
        intent.putExtra("params", params);
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Class<? extends Test>> initTestClasses(int testPos) {
        ArrayList<Class<? extends Test>> testClasses = new ArrayList<Class<? extends Test>>();
        // the attributes are putted in the intent (eventbus, otto, broadcast, local broadcast)
        final CheckBox checkBoxEventBus = (CheckBox) findViewById(R.id.checkBoxEventBus);
        final CheckBox checkBoxOtto = (CheckBox) findViewById(R.id.checkBoxOtto);
        final CheckBox checkBoxBroadcast = (CheckBox) findViewById(R.id.checkBoxBroadcast);
        final CheckBox checkBoxLocalBroadcast = (CheckBox) findViewById(R.id.checkBoxLocalBroadcast);
        if (checkBoxEventBus.isChecked()) {
            testClasses.add(TEST_CLASSES_EVENTBUS[testPos]);
        }
        if (checkBoxOtto.isChecked()) {
        }
        if (checkBoxBroadcast.isChecked()) {
        }
        if (checkBoxLocalBroadcast.isChecked()) {
        }

        return testClasses;
    }
}