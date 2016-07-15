package it.junior.since;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class AddEvent extends Activity {

    EditText etName, etDate;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);
        initUI();
    }

    private void initUI() {
        etName = (EditText) findViewById(R.id.etEvent);
//        etDate = (EditText) findViewById(R.id.etDate);
    }

    public void svBtn(View view) {
        db = new DB(this);
        db.open();
        db.addRec(new Date().getTime()/1000, String.valueOf(etName.getText()), 0);
        Intent svBtnIntent = new Intent(this, MainActivity.class);

        startActivity(svBtnIntent);
    }

}
