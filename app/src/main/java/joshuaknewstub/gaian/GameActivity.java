package joshuaknewstub.gaian;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    RelativeLayout pallet;
    ListView elementList;
    String msg;
    android.widget.RelativeLayout.LayoutParams layoutParams;
    ElementAdapter elementAdapter = new ElementAdapter(this);
    private int _xDelta;
    private int _yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        pallet = findViewById(R.id.pallet);
        elementList = findViewById(R.id.element_list);
        elementList.setAdapter(elementAdapter);
        elementList.setOnItemClickListener(new itemClickedListener());


    }

    private final class itemClickedListener implements AdapterView.OnItemClickListener {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ImageView imageView = new ImageView(GameActivity.this);
            imageView.setImageResource(elementAdapter.getImageResourceId(position));
            RelativeLayout.LayoutParams size = new RelativeLayout.LayoutParams(200, 200);
            imageView.setLayoutParams(size);
            imageView.setOnTouchListener(new touchListener());
            pallet.addView(imageView);
        }
    }


    private final class touchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    view.bringToFront();
                    break;

                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;

                case MotionEvent.ACTION_UP:
                    Toast.makeText(GameActivity.this, "Item Dropped", Toast.LENGTH_SHORT).show();
                    break;
            }
            pallet.invalidate();
            return true;
        }
    }

}
