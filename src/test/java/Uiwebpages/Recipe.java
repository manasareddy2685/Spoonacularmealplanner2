package Uiwebpages;

public class Recipe {
    private int id;
    private int servings;
    private String title;
    private String imageType;
    private String breakfast;
    private String dinner;

    private String lunch;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setBreakFast(String breakFast) {this.breakfast = breakFast;}

    public String getBreakFast(){return breakfast;}

    public void setDinner(String dinner) { this.dinner = dinner;}

    public String getDinner() {return dinner;}

    public void setLunch(String Lunch) { this.lunch = lunch ;}

    public  String getLunch(){return lunch;}


    }









