package joshuaknewstub.gaian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    Boolean sfx;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        sfx = sharedPreferences.getBoolean(getString(R.string.sfx), true);

        ToggleButton sfxToggle = (ToggleButton) findViewById(R.id.sfxToggle);
        sfxToggle.setOnCheckedChangeListener((buttonView, isChecked) -> sfx = isChecked);
        sfxToggle.setChecked(sfx);


    }

    public void saveClicked(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.sfx), sfx);
        editor.apply();

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);


    }
}