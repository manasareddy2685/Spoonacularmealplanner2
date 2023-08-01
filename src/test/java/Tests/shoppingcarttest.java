package Tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class shoppingcarttest extends TestBase {
    @Test
    public void newShopping() throws Exception {
    String excelPath = "./shoppingcart.xlsx";
    String sheetName = "sheet1";


    Object[][] tabularData = {
            {73420, "Apple Or Peach Strudel", "https://spoonacular.com/recipeImages/73420-312x231.jpg", 0, 1.5, 150},
            {632660, "Apricot Glazed Apple Tart", "https://spoonacular.com/recipeImages/632660-312x231.jpg", 3, 2, 200},
            {8120,"Cereal","https://spoonacular.com/cdn/ingredients_100x100/rolled-oats.jpg",0, 3, 300},
            {1089003,	"grannysmithapples","https://spoonacular.com/cdn/ingredients_100x100/grannysmith-apple.png", 11, 5, 500},
    };

    JSONArray jsonArray = new JSONArray();
    for (Object[] row : tabularData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", row[0]);
        jsonObject.put("Title", row[1]);
        jsonObject.put("Image", row[2]);
        jsonObject.put("Likes", row[3]);
        jsonObject.put("cups",row[4]);
        jsonObject.put("gramsPerCup",row[5]);
        jsonArray.put(jsonObject);
    }


    System.out.println(jsonArray.toString());


    String apiEndpoint = "https://api.spoonacular.com/users/connect"; // Replace with the actual API endpoint URL
    makePostRequest(apiEndpoint, jsonArray.toString());


}
           static Response makePostRequest(String apiEndpoint, String jsonData) {
         

            Response response = RestAssured.given()
                    .queryParam("x-apikey","806069c53fe041df99739b623fde7789")
                    .contentType(ContentType.JSON)
                    .and()
                    .when()
                    .post( "https://api.spoonacular.com/users/connect")
                    .then()
                    .extract().response();
            return response;

        }
    }