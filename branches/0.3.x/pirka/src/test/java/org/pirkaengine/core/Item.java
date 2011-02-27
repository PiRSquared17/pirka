package org.pirkaengine.core;

import java.text.DecimalFormat;
import java.util.Date;

public class Item {

    public int id;
    public String name;
    public Category category;
    public int price;
    public String description;
    public boolean sale;
    private String privateValue;
    public Date date;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
    
    public String labeledPrice() {
        return new DecimalFormat("###,##0").format(this.price);
    }

    @SuppressWarnings("unused")
	private String getPrivateValue() {
        return privateValue;
    }
    
    public Object getNullValue() {
        return null;
    }
   
    public Object methodNull() {
        return null;
    }
}
