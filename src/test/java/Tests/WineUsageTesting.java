package Tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class WineUsageTesting extends TestBase {

    @Test
    public void testWineUsageForWeekendPartyMeal() throws IOException {
        Properties properties = loadProperties("config.properties");
        String apiKey = properties.getProperty("api.key");
        String apiUrl = properties.getProperty("api.url");
        String browserPath = properties.getProperty("Browser");

        // Set the Chrome driver path
        System.setProperty("webdriver.chrome.driver", browserPath);

        String excelFilePath = properties.getProperty("AbsolutePath") + "/mealTemplate.xlsx";
        FileInputStream inputStream = new FileInputStream(excelFilePath);

        String day = "Saturday";
        String mealType = "Non vegan";
        List<String> ingredients = new ArrayList<>();
        ingredients.add("red food coloring");
        ingredients.add("Chicken");
        ingredients.add("Sterling Vineyards Merlot");

        boolean canUseWine = canUseWine(day, mealType, ingredients);

        if (canUseWine) {
            System.out.println("Wine cannot be used with the weekend party meal.");
        } else {
            System.out.println("Wine can be used with the weekend party meal.");
        }
    }

    public static boolean canUseWine(String day, String mealType, List<String> ingredients) {
        boolean isWeekend = day.equalsIgnoreCase("Sunday") ;

        boolean wineCompatible = (mealType.equalsIgnoreCase("Menu Item") || mealType.equalsIgnoreCase("Non vegan"))
                && containsWineIngredients(ingredients);

        return isWeekend && wineCompatible;
    }

    public static boolean containsWineIngredients(List<String> ingredients) {
        for (String ingredient : ingredients) {
            if (ingredient.toLowerCase().contains("wine") || ingredient.toLowerCase().contains("vineyards")) {
                return true;
            }
        }
        return false;
    }

    public static Response postRequest(String url, String requestBody, String apiKey) {
        Response response = given()
                .queryParam("apiKey", apiKey)
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();
        return response;
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
}
