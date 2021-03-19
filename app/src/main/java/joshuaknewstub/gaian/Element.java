package joshuaknewstub.gaian;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Element {
    public String name;
    public int imageResourceId;
    boolean unlocked;
    String[] unlockedBy;


    public static final Map<String, Element> map = new HashMap<String, Element>();


    public static final Element[] elements = {
            new Element("Air", R.raw.air, true, null),
            new Element("Fire", R.raw.fire, true, null),
            new Element("Water", R.raw.water, true, null),
            new Element("Earth", R.raw.earth, true, null),
            new Element("Rain", R.raw.rain, false, new String[]{"Air", "Water"})
    };

    private Element(String name, int imageResourceId, boolean unlocked, String[] unlockedBy){
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.unlocked = unlocked;
        this.unlockedBy = unlockedBy;



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
