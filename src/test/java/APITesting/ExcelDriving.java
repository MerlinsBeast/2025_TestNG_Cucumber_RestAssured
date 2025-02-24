package APITesting;

import TestData.LibraryFunctions;
import Utilities.CommonUtilities;
import Utilities.dataDriven;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ExcelDriving {

    @Test
    public void addBookExcelImplementation() throws IOException {
        HashMap<String, Object> details = new HashMap<>();
        details.put("name","Appium");
        details.put("isbn","241");
        details.put("aisle","aisele");
        details.put("author","Vijay Yadav");

        dataDriven data=new dataDriven();
        ArrayList excel=data.getData("RestAddbook","Add Profile");
        System.out.println("excel details"+excel);
        HashMap<String, Object> excelDetails = new HashMap<>();
        excelDetails.put("name",excel.get(1));
        excelDetails.put("isbn",excel.get(2));
        excelDetails.put("aisle",excel.get(3));
        excelDetails.put("author",excel.get(4));


        RestAssured.baseURI="http://216.10.245.166";
        String addBookResponse=given().
                header("Content-Type","application/json")
                .body(excelDetails)
                .when()
                .log().all()
                .post("/Library/Addbook.php")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();
        String valueOfID= CommonUtilities.getRawToJson(addBookResponse,"ID");
        System.out.println("Id value is : "+valueOfID);
    }
}
