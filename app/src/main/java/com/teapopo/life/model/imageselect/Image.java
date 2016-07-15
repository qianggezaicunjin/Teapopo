package com.teapopo.life.model.imageselect;



import com.teapopo.life.model.BaseEntity;

/**
 * Image bean
 * Created by Yancy on 2015/12/2.
 */
public class Image extends BaseEntity{

    public String path;
    public String name;
    public long time;
    public boolean isSelected = false;
    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}