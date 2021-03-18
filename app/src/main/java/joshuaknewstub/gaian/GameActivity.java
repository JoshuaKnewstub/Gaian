package joshuaknewstub.gaian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ListView listView = findViewById(R.id.element_list);
        ElementAdapter elementAdapter = new ElementAdapter(this);
        listView.setAdapter(elementAdapter);

    }
}