package ilyeon.ameokja.model;

public class foods {
    Integer serial;
    String foodname;
    String context;
    Integer like;




    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getSerial() {
        return serial;
    }

    public String getContext() {
        return context;
    }

    public String getFoodname() {
        return foodname;
    }
}
