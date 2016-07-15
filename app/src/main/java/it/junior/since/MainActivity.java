package it.junior.since;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    LinearLayout llOutput;

    DB db;

//    private int flagDateType = 0;
    private long timeFromBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        readValues();
    }

    private void initUI() {

        llOutput = (LinearLayout) findViewById(R.id.llOutput);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            return true;
            Toast.makeText(this, "Settings menu click", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addEvent(View view) {
        Intent intentAddEvent = new Intent(this, AddEvent.class);
        startActivity(intentAddEvent);
    } //TODO поменять на ОнКлик

    private String getTimeDiff(Long secBig) {
        Long diff = System.currentTimeMillis()/1000 - secBig;
        Long days = diff / (60 * 60 * 24);
        Long hours = (diff - (days * (60 * 60 * 24))) / (60 * 60);
        Long minutes = (diff - (days * (60 * 60 * 24)) - hours * (60 * 60)) / 60;
        Long seconds = diff - (days * (60 * 60 * 24)) - hours * (60 * 60) - minutes * (60);
        return days + "д " + hours + " ч " + minutes + "мин " + seconds + "сек ";
    }
//    private String getTimeDiffInSec(Long secBig) {
//        Long seconds = System.currentTimeMillis()/1000 - secBig;
//        return seconds + "сек ";
//    }
//    private String getTimeDiffInMin(Long secBig) {
//        Long diff = System.currentTimeMillis()/1000 - secBig;
//        Long minutes = diff / 60;
//        return minutes + "мин ";
//    }
//    private String getTimeDiffInHour(Long secBig) {
//        Long diff = System.currentTimeMillis()/1000 - secBig;
//        Long hours = diff  / (60 * 60);
//        return hours + " ч ";
//    }
//    private String getTimeDiffInDays(Long secBig) {
//        Long diff = System.currentTimeMillis()/1000 - secBig;
//        Long days = diff / (60 * 60 * 24);
//        return days + "д ";
//    }

    private void readValues() {
        db = new DB(this);
        db.open();

        llOutput.removeAllViews();
        Cursor c = db.getAllData();
        if (c.moveToFirst()) {
            int dateColId = c.getColumnIndex(db.COLUMN_ID);
            int dateColName = c.getColumnIndex(db.COLUMN_NAME);
            int dateColDate = c.getColumnIndex(db.COLUMN_DATA);
//            int colorCol = c.getColumnIndex(db.COLUMN_COLOR);
//            int colorCol = 111111;

            LayoutInflater ltInflater = getLayoutInflater();

            do {
                final View item = ltInflater.inflate(R.layout.item, llOutput, false);
                RelativeLayout itemRelativeLayout = (RelativeLayout) item.findViewById(R.id.itemRelativeLayout);
                TextView tvId = (TextView) item.findViewById(R.id.tvId);
                TextView tvName = (TextView) item.findViewById(R.id.tvName);
                ImageView ivEvent = (ImageView) item.findViewById(R.id.ivEvent);
                final TextView tvDate = (TextView) item.findViewById(R.id.tvDate);

                timeFromBD = c.getLong(dateColDate);
                String eventNameFromBD = getString(R.string.event) + c.getString(dateColName);

                ivEvent.setImageResource(R.drawable.ic_assignment_black_24dp);
                tvDate.setText(getTimeDiff(timeFromBD));
                tvName.setText(eventNameFromBD);
                tvId.setText(c.getString(dateColId));
//                itemRelativeLayout.setBackgroundColor(Color.CYAN);
                item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

//                tvDate.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        switch (flagDateType) {
//                            case 0:
//                                tvDate.setText(getTimeDiff(timeFromBD));
//                                flagDateType++;
//                                break;
//                            case 1:
//                                tvDate.setText(getTimeDiffInSec(timeFromBD));
//                                flagDateType++;
//                                break;
//                            case 2:
//                                tvDate.setText(getTimeDiffInMin(timeFromBD));
//                                flagDateType++;
//                                break;
//                            case 3:
//                                tvDate.setText(getTimeDiffInHour(timeFromBD));
//                                flagDateType = 0;
//                                break;
//                        }
//                    }
//                });

                llOutput.addView(item, 0);

            } while (c.moveToNext());
        } else
            c.close();

        db.close();
    }
}
