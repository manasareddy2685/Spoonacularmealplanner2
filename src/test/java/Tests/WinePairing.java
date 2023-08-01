package Tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class WinePairing extends TestBase {

    @Test
    public void testWineRecommendationForFood() {


        String jsonData = "{\"pairings\":[\"stew\",\"steak\",\"chili\",\"burger\"],\"text\":\"" +
                "Malbec is a dry red wine which is bold and full bodied. It goes especially well with round steak, " +
                "tri tip steak, steak, boneless pork chops, and pizza burger.\"}";

        String API_KEY = "806069c53fe041df99739b623fde7789"; // Replace with your actual API key

        Response response = GetRequest(API_KEY, jsonData);

        assertEquals(response.statusCode(), 200);

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Check if wine pairing is available for the meal
        if (response.getBody().asString().contains("pairings")) {
            System.out.println("You can use wine with the meal being prepared for the party during the weekend!");
        } else {
            System.out.println("Wine pairing is not available for the meal being prepared for the party.");
        }
    }

    public Response GetRequest(String apiKey, String requestBody) {
        String url = "https://api.spoonacular.com/food/wine/recommendation";

        // Make the POST request to the Spoonacular API to get wine pairings
        Response response = given()
                .queryParam("apiKey", "806069c53fe041df99739b623fde7789")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://api.spoonacular.com/food/wine/recommendation")
                .then()
                .extract().response();

        return response;
    }
}
