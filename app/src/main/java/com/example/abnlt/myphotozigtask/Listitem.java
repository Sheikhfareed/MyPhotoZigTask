package com.example.abnlt.myphotozigtask;

/**
 * Created by ABN LT on 12/3/2017.
 */
public class Listitem {
    private String name;
    private String bg;
    private String imageUrl;
    private String sg;

    public Listitem(String name,String bg, String imageUrl, String sg) {
        this.name = name;
        this.bg = bg;
        this.imageUrl = imageUrl;
        this.sg = sg;
    }

    public String getName() {
        return name;
    }

    public String getBg() {
        return bg;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSg() {
        return sg;
    }
}
