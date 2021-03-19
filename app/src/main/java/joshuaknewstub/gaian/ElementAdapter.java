package joshuaknewstub.gaian;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

class ElementAdapter implements ListAdapter {
    ArrayList<Element> elementArrayList = new ArrayList<>();
    Context context;

    public ElementAdapter(Context context) {

        for (int i = 0; i < Element.elements.length; i++){
            if (Element.elements[i].unlocked){
                this.elementArrayList.add(Element.elements[i]);
            }
        }
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
    public Element getItem(int position) {
        return elementArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getName(int position){
        return elementArrayList.get(position).name;
    }

    public int getImageResourceId(int position){
        return elementArrayList.get(position).imageResourceId;
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

//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(context, element.name, Toast.LENGTH_SHORT).show();
//                }
//            });

            TextView name = convertView.findViewById(R.id.element_name);
            ImageView image = convertView.findViewById(R.id.element_image);

            name.setText(element.name);
            image.setImageResource(element.imageResourceId);

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
