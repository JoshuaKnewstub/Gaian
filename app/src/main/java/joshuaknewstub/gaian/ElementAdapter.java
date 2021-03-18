package joshuaknewstub.gaian;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

class ElementAdapter implements ListAdapter {
    ArrayList<Element> elementArrayList;
    Context context;

    public ElementAdapter(Context context) {
        this.elementArrayList = new ArrayList<>(Arrays.asList(Element.elements));
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return elementArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Element element = elementArrayList.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, element.name, Toast.LENGTH_LONG).show();
                }
            });
            TextView name = convertView.findViewById(R.id.element_name);
            ImageView image = convertView.findViewById(R.id.element_image);

            name.setText(element.name);
            image.setImageResource(element.imageResourceId);
            if (!element.unlocked){
                //TODO find a way hide elements
                convertView.setVisibility(View.GONE);
                convertView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
            }
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return elementArrayList.size();
    }


    @Override
    public boolean isEmpty() {
        return false;
    }
}
