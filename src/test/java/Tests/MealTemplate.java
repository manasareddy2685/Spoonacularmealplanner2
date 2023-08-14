package Tests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class MealTemplate extends TestBase {

    private String excelFilePath;

    @Test
    public void newMealItem() throws IOException {
        Properties properties = loadProperties("config.properties");
        String apiKey = properties.getProperty("api.key");
        String apiUrl = properties.getProperty("api.url");
        String browserPath = properties.getProperty("Browser");

        // Set the Chrome driver path
        System.setProperty("webdriver.chrome.driver", browserPath);

        excelFilePath = properties.getProperty("AbsolutePath") + "/mealTemplate.xlsx";
        FileInputStream inputStream = new FileInputStream(excelFilePath);

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = firstSheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                System.out.print(cell.toString() + " - ");
            }
            System.out.println();
        }

        workbook.close();
        inputStream.close();
    }

    private List<HashMap<String, Object>> calculateNutrition(List<HashMap<String, Object>> mealItems) {
        for (HashMap<String, Object> mealItem : mealItems) {
            double protein = parseNutritionValue(mealItem.getOrDefault("Protein", "").toString());
            double carbohydrates = parseNutritionValue(mealItem.getOrDefault("Carbohydrates", "").toString());
            double fat = parseNutritionValue(mealItem.getOrDefault("Fat", "").toString());
            double totalNutrition = protein + carbohydrates + fat;
            mealItem.put("Total Nutrition", totalNutrition);
        }
        return mealItems;
    }

    private double parseNutritionValue(String value) {
        String numericValue = value.replace("gms", "").trim();
        return Double.parseDouble(numericValue);
    }


    public void PostRequest(String apiUrl, List<HashMap<String, Object>> json, String apiKey) {
        for (HashMap<String, Object> newMealItem : json) {
            String requestObject = GsontoJSON.convertToJSON(newMealItem);
            Response response = postRequest(apiUrl, requestObject);
            System.out.println("Response Body: " + response.getBody().asString());
            assertEquals(response.statusCode(), 200, "POST request should succeed.");

        }
    }

    public static Response postRequest(String url, String requestBody) {
        Response response = given()
                .queryParam("apiKey", "your-api-key")
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
