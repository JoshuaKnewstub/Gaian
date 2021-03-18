package joshuaknewstub.gaian;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Element {
    public String name;
    public int imageResourceId;
    boolean unlocked;


    public static final Element[] elements = {
            new Element("Air", R.raw.air, true),
            new Element("Fire", R.raw.fire, true),
            new Element("Water", R.raw.water, true),
            new Element("Rain", R.raw.rain, false),
            new Element("Earth", R.raw.earth, true)
    };

    private Element(String name, int imageResourceId, boolean unlocked){
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.unlocked = unlocked;
    }

    @NonNull @Override
    public String toString(){
        return name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }

    public boolean getUnlocked(){
        return unlocked;
    }
}
