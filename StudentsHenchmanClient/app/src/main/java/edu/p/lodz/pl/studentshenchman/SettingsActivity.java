package edu.p.lodz.pl.studentshenchman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import edu.p.lodz.pl.studentshenchman.abstract_ui.StudentShenchmanMainActivity;

public class SettingsActivity extends StudentShenchmanMainActivity {
    private static final String TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner facultySelectBox = (Spinner) findViewById(R.id.facultySelectBox);
        final Spinner typeSelectBox = (Spinner) findViewById(R.id.typeSelectBox);
        final Spinner subjectSelectBox = (Spinner) findViewById(R.id.subjectSelectBox);

        final TableRow typeTableRow = (TableRow) findViewById(R.id.typeTableRow);
        final TableRow subjectTableRow = (TableRow) findViewById(R.id.subjectTableRow);
        final TableRow majorTableRow = (TableRow) findViewById(R.id.majorTableRow);
        final TableRow readButtonTableRow = (TableRow) findViewById(R.id.readButtonTableRow);

        typeTableRow.setVisibility(View.GONE);
        subjectTableRow.setVisibility(View.GONE);
        majorTableRow.setVisibility(View.GONE);
        readButtonTableRow.setVisibility(View.GONE);

        //String provider = spinner1.getSelectedItem().toString();

        facultySelectBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                typeTableRow.setVisibility(View.VISIBLE);
                typeSelectBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        subjectTableRow.setVisibility(View.VISIBLE);
                        subjectSelectBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                majorTableRow.setVisibility(View.VISIBLE);

                                // WYBOR SPECJALIZACJI

                                readButtonTableRow.setVisibility(View.GONE);

                                // JAK CHOCIAZ JEDNO WYBIORE
                                // readButtonTableRow.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                majorTableRow.setVisibility(View.GONE);
                                readButtonTableRow.setVisibility(View.GONE);
                            }

                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        subjectTableRow.setVisibility(View.GONE);
                        majorTableRow.setVisibility(View.GONE);
                        readButtonTableRow.setVisibility(View.GONE);
                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                typeTableRow.setVisibility(View.GONE);
                subjectTableRow.setVisibility(View.GONE);
                majorTableRow.setVisibility(View.GONE);
                readButtonTableRow.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onBackPressed() {
        goToDashBoard();
    }

    private void goToDashBoard() {
        Intent previousActivity = new Intent(SettingsActivity.this, MainActivity.class);
        finish();
        startActivity(previousActivity);
    }

}
