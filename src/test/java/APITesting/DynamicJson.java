package APITesting;

import TestData.LibraryFunctions;
import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.StringJoiner;

import static io.restassured.RestAssured.*;

public class DynamicJson {
    @Test(dataProvider = "getData")
    public void addBook(String aisle,String isbn){
//        String aisle="Aisle_12390";
//        String isbn="Aisle_12390";
        RestAssured.baseURI="http://216.10.245.166";
        String addBookResponse=given().
                header("Content-Type","application/json")
                .body(LibraryFunctions.AddBook(aisle,isbn))
                .when()
                .log().all()
                .post("/Library/Addbook.php")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();
        String valueOfID=CommonUtilities.getRawToJson(addBookResponse,"ID");
        System.out.println("Id value is : "+valueOfID);
    }

    @Test(dependsOnMethods = "addBook",dataProvider = "getData")
    public void deleteBook(String aisle, String isbn){
        RestAssured.baseURI="http://216.10.245.166";
        String deleteBookResponse=given().
                header("Content-Type","application/json")
                .body(LibraryFunctions.DeleteBook(aisle,isbn))
                .when()
                .log().all()
                .delete("/Library/DeleteBook.php")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();
        System.out.println(deleteBookResponse);

//        String valueOfMessage=CommonUtilities.getRawToJson(deleteBookResponse,"msg");
//        System.out.println("Id value is : "+valueOfMessage);
//        Assert.assertTrue(valueOfMessage.equalsIgnoreCase("book is successfully deleted"));
    }

    @DataProvider
    public Object[][] getData(){
        Object[][] data=new Object[][]{{"ail123","ail123"},{"ail113","ail113"},{"ail114","ail114"}};
        return data;
    }
}
