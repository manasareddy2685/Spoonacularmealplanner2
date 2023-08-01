package Uiwebpages;

import java.util.List;

public class MealPlanTemplate {
    private String name;
    private List<MealItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MealItem> getItems() {
        return items;
    }

    public void setItems(List<MealItem> items) {
        this.items = items;
    }
}
