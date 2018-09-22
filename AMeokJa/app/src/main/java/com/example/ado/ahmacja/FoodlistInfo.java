package com.example.ado.ahmacja;

public class FoodlistInfo {
    public String foodimage;
    public String foodname;
    public String ex;
    public String likecnt;
    public FoodlistInfo() {

    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setEx(String context) {
        this.ex = context;
    }

    public void setLikecnt(String likecnt) {
        this.likecnt = likecnt;
    }

    public String getFoodname() {
        return foodname;
    }

    public String getLikecnt() {
        return likecnt;
    }

    public String getEx() {return ex;}

    public String getFoodimage() {return foodimage; }
}
