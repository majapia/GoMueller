package com.example.rcos.gomueller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NewExerciseActivity extends ActionBarActivity {

    EditText exercise_name;
    EditText exercise_weight;
    EditText exercise_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        exercise_name = (EditText)findViewById(R.id.nameEditText);
        exercise_weight = (EditText)findViewById(R.id.weightEditText);
        exercise_number = (EditText)findViewById(R.id.numberEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_exercise, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void okButtonOnClick(View view) {
        ExerciseCRUD crud = new ExerciseCRUD(this);
        Exercise ex = new Exercise();


        ex.activityName = exercise_name.getText().toString();
        ex.weight = Integer.parseInt(exercise_weight.getText().toString());
        ex.number = Integer.parseInt(exercise_number.getText().toString());

        crud.insert(ex);

        finish();

    }

}
