package Tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Tests.GsontoJSON.convertToJSON;
import static Tests.shoppingcarttest.makePostRequest;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class MealTemplate extends TestBase {

    private String excelFilePath;

    @Test

    public void newMealItem() throws Exception {

        String excelPath = "./mealTemplate.xlsx";
        String sheetName = "sheet1";

        List<HashMap<String, Object>> mealItems = new ArrayList<>();

// Define the table data (Day, Slot, Position, Type, ID, Servings, BreakFast, Lunch, Dinner, Img)

        Object[][] tabularData = {
                {1, 1, 0, "Recipe", 716426, 3, "Cauliflower", "BrownRice", "Vegetable Fried Rice", "https://spoonacular.com/recipeImages/716426-312x231.jpg"},
                {2, 1, 0, "Product", 715415, 3, "Red Lentil Soup", "Chicken", "Turnips", "https://spoonacular.com/recipeImages/715415-312x231.jpg"},
                {3, 1, 0, "Menu Item", 716406, 3, "Asparagus", "Pea Soup", "Real Convenience Food", "https://spoonacular.com/recipeImages/716406-312x231.jpg"},
                {4, 1, 0, "Custom Food", 782585, 3, "Cannellini Bean", "Asparagus Salad", "Mushrooms", "https://spoonacular.com/recipeImages/782585-312x231.jpg"},
                {5, 1, 0, "Ingredients", 716429, 3, "Pasta with Garlic", "Scallions", "Cauliflower & Breadcrumbs", "https://spoonacular.com/recipeImages/716429-312x231.jpg"},
                {6, 1, 0, "Non vegan", 795751, 3, "Chicken Fajita", "Stuffed Bell", "Pepper", "https://spoonacular.com/recipeImages/795751-312x231.jpg"},
                {7, 1, 0, "Non vegan", 428278, 3, "Real Convenience Food", "Chicken", "Sterling Vineyards Merlot", "https://spoonacular.com/productImages/428278-312x231.jpg"},
        };

// Populate the mealItems list with data from the table
        {
            for (Object[] rowData : tabularData) {
                HashMap<String, Object> mealItem = new HashMap<>();
                mealItem.put("Day", rowData[0]);
                mealItem.put("Slot", rowData[1]);
                mealItem.put("Position", rowData[2]);
                mealItem.put("Type", rowData[3]);
                mealItem.put("ID", rowData[4]);
                mealItem.put("Servings", rowData[5]);
                mealItem.put("Breakfast", rowData[6]);
                mealItem.put("Lunch", rowData[7]);
                mealItem.put("Dinner", rowData[8]);
                mealItem.put("Img", rowData[9]);
                mealItems.add(mealItem);
            }

            System.out.println(mealItems.toString());

            // Make the POST request with the JSON data
            String apiEndpoint = "YOUR_API_ENDPOINT"; // Replace with the actual API endpoint URL
            makePostRequest(apiEndpoint, mealItems.toString());
        }
    }

        public void PostRequests (List < HashMap < String, Object >> json){
            for (HashMap<String, Object> newMealItem : json) {
                String requestObject = convertToJSON(newMealItem);
                Response response = postRequest("https://api.spoonacular.com/users/connect", requestObject);
                System.out.println("json-body post request is " + requestObject);
                assertEquals(response.statusCode(), 200);
            }
        }

        public static Response postRequest (String url, String requestBody){
            String apikey = "d29bb4f76a5b4ce4bcdf46b52be7e810";
            Response response = given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "806069c53fe041df99739b623fde7789")
                    .queryParam("timeframe", "perDay")
                    .and()
                    .body(requestBody)
                    .when()
                    .post(url)
                    .then()
                    .extract().response();
            return response;
        }
    }






