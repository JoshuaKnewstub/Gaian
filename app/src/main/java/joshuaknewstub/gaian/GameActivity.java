package joshuaknewstub.gaian;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    RelativeLayout pallet;
    ListView elementList;

    private int _xDelta;
    private int _yDelta;
    Map<Integer, Element> map = new HashMap<Integer, Element>();
    ElementAdapter elementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        pallet = findViewById(R.id.pallet);
        elementList = findViewById(R.id.element_list);
        resetAdapter();
        elementList.setOnItemClickListener(new itemClickedListener());
    }

    private final class itemClickedListener implements AdapterView.OnItemClickListener {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Element element = elementAdapter.getItem(position);
            addView(element);
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
                    getOnTop(view);
//                    Toast.makeText(GameActivity.this, "Item Dropped", Toast.LENGTH_SHORT).show();
                    break;
            }
            pallet.invalidate();
            return true;
        }
    }

    public void getOnTop(View draggedView) {
        int X = (int) (draggedView.getX() + 125);
        int Y = (int) draggedView.getY() + 125;
//        Log.e("co-ordinates", "X:" + X + " Y:" + Y);

        int numOfViews = pallet.getChildCount();
        for (int i = 0; i < numOfViews; i++) {
            View tempView = pallet.getChildAt(i);
            if (tempView.getId() != draggedView.getId()) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tempView
                        .getLayoutParams();

//                Log.e("co-ordinates", "X:" + X + " Y:" + Y);
//                Log.e("Left Parames", "" + layoutParams.leftMargin);
//                Log.e("Left Parames", "" + (layoutParams.leftMargin - layoutParams.rightMargin));
//                Log.e("Left Parames", "" + layoutParams.topMargin);
//                Log.e("Left Parames", "" + (layoutParams.topMargin - layoutParams.bottomMargin));


                if (X > layoutParams.leftMargin &
                        X < (layoutParams.leftMargin + 250) &
                        Y > layoutParams.topMargin &
                        Y < (layoutParams.topMargin + 250)) {
                checkIfCombo(draggedView, tempView);
//                    Toast.makeText(GameActivity.this, "Ontop of", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }

    public void checkIfCombo(View draggedView, View tempView) {
        Element element1 = map.get(draggedView.getId());
        Element element2 = map.get(tempView.getId());
        for (int i = 0; i < Element.elements.length; i++) {
            if (Element.elements[i].unlockedBy != null) {
                String element1Name = element1.name;
                String element2Name = element2.name;
                if (Arrays.asList(Element.elements[i].unlockedBy).contains(element1Name) &
                        Arrays.asList(Element.elements[i].unlockedBy).contains(element2Name)){
                    Element.elements[i].unlocked = true;
                    resetAdapter();
                    Log.e("Unlocked?", "" + Element.elements[i].unlocked);
                    Toast.makeText(GameActivity.this, Element.elements[i].name + " unlocked!", Toast.LENGTH_SHORT).show();
                    pallet.removeView(draggedView);
                    pallet.removeView(tempView);
                    addView(Element.elements[i]);

                }
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void addView(Element element){
        ImageView imageView = new ImageView(GameActivity.this);
        imageView.setImageResource(element.getImageResourceId());
        RelativeLayout.LayoutParams size = new RelativeLayout.LayoutParams(200, 200);
        imageView.setLayoutParams(size);
        imageView.setOnTouchListener(new touchListener());
        imageView.setId(View.generateViewId());
        map.put(imageView.getId(), element);
        pallet.addView(imageView);
    }

    private void resetAdapter(){
        elementAdapter = new ElementAdapter(GameActivity.this);
        elementList.setAdapter(elementAdapter);
        elementList.setOnItemClickListener(new itemClickedListener());
    }

}
