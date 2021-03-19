package joshuaknewstub.gaian;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    //Layouts
    FrameLayout pallet;
    ListView elementList;

    private int _xDelta;
    private int _yDelta;
    Map<Integer, Element> viewIdElementMap = new HashMap<>();
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
                    FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    view.bringToFront();
                    break;

                case MotionEvent.ACTION_MOVE:
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view
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


        int numOfViews = pallet.getChildCount();
        for (int i = 0; i < numOfViews; i++) {
            View tempView = pallet.getChildAt(i);
            if (tempView.getId() != draggedView.getId()) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tempView
                        .getLayoutParams();

                if (X > layoutParams.leftMargin &
                        X < (layoutParams.leftMargin + 250) &
                        Y > layoutParams.topMargin &
                        Y < (layoutParams.topMargin + 250)) {
                checkIfCombo(draggedView, tempView);
                    break;
                }
            }
        }
    }

    public void checkIfCombo(View draggedView, View tempView) {
        Element element1 = viewIdElementMap.get(draggedView.getId());
        Element element2 = viewIdElementMap.get(tempView.getId());
        for (int i = 0; i < Element.elements.length; i++) {
            if (Element.elements[i].unlockedBy != null) {
                String element1Name = element1.name;
                String element2Name = element2.name;
                if (Arrays.asList(Element.elements[i].unlockedBy).contains(element1Name) &
                        Arrays.asList(Element.elements[i].unlockedBy).contains(element2Name)){
                    if (!Element.elements[i].unlocked) {
                        Element.elements[i].unlocked = true;
                        resetAdapter();
                        Log.e("Unlocked?", "" + Element.elements[i].unlocked);
                        Toast.makeText(GameActivity.this, Element.elements[i].name + " unlocked!", Toast.LENGTH_SHORT).show();
                    }
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
        FrameLayout.LayoutParams size = new FrameLayout.LayoutParams(200, 200);
        imageView.setLayoutParams(size);
        imageView.setOnTouchListener(new touchListener());
        imageView.setId(View.generateViewId());
        viewIdElementMap.put(imageView.getId(), element);
        pallet.addView(imageView);
    }

    private void resetAdapter(){
        elementAdapter = new ElementAdapter(GameActivity.this);
        elementList.setAdapter(elementAdapter);
        elementList.setOnItemClickListener(new itemClickedListener());
    }

    public void removeAll(View view) {
        pallet.removeAllViews();
    }

}
