package Tests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
public class AlternativeDishRecommendationTest extends TestBase {

    @Test
    public void testAlternativesAvailable() throws IOException {
        Properties properties = loadProperties("config.properties");
        String apiKey = properties.getProperty("api.key");
        String apiUrl = properties.getProperty("api.url");
        String browserPath = properties.getProperty("Browser");

        // Set the Chrome driver path
        System.setProperty("webdriver.chrome.driver", browserPath);

       String excelFilePath = properties.getProperty("AbsolutePath") + "/mealTemplate.xlsx";
        FileInputStream inputStream = new FileInputStream(excelFilePath);

        String[] foodItems = {"egg", "bourbon", "pecans"}; // Add more food items

            for (String foodItem : foodItems) {
                Response response = sendPostRequest(apiUrl, foodItem, apiKey);
                assertEquals(response.getStatusCode(), 200);

                List<Object> alternatives = response.getBody().jsonPath().getList("alternatives");
                if (alternatives == null || alternatives.isEmpty()) {
                    System.out.println("No alternatives found for food item: " + foodItem);
                    System.out.println("Response JSON: " + response.getBody().asString());
                } else {
                    System.out.println("Alternatives found for food item: " + foodItem);
                }
                assertTrue(alternatives != null && alternatives.size() > 0,
                        "No alternative dishes were suggested for food item: " + foodItem);
            }
        }
        private Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("The " + fileName + " file does not exist in the specified location.");
            }
            properties.load(inputStream);
        }
        return properties;
    }

    private Response sendPostRequest(String url, String foodItem, String apiKey) {
        String requestBody = "{ \"food\": \"" + foodItem + "\" }"; // Assuming JSON request body structure
        Response response = given()
                .queryParam("apiKey", apiKey)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();
        return response;
    }
}


