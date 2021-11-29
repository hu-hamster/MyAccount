package ltd.hujing.myaccount;

import java.util.Calendar;

public class AccountsItem {
    private int image_id;
    private String name;
    private double price;
    private String description;
    private Calendar calendar;

    public AccountsItem(int image_id, String name, double price, String descriptions, Calendar calendar)
    {
        this.image_id = image_id;
        this.name = name;
        this.price = price;
        this.description = descriptions;
        this.calendar = calendar;
    }

    public String getName(String name) {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getImageId() {
        return image_id;
    }

    public void setImageId(int image_id) {
        this.image_id = image_id;
    }
}
