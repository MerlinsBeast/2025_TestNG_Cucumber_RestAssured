package APITesting;

import TestData.JIRA_Payloads;
import TestData.LibraryFunctions;
import Utilities.CommonUtilities;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Jira_CreateBug {

    public String bugId="";
    @Test()
    public void createIssue(){
        RestAssured.baseURI="https://merlintomb.atlassian.net";

//        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
//        authScheme.setUserName("merlintomb@gmail.com.com");
//        authScheme.setPassword("9YX12345sXZ02927");
//        RestAssured.authentication = authScheme;

        String createIssueDetail=given().
                header("Content-Type","application/json")
//                .header("Authorization","Basic ATATT3xFfGF0Q3R1SMDdH9KZ4rJN2kDTwfVqkmbQWwpO-JtEN4UEr2TsNiEMJRuwoEWAgPAJpSJI1lnzi4PR7UDCyiNXreGFS7o_6jG2duDYMwhKLee9G9kW5kQC0HCXPotZGgM9NiUjMjaIZ7xFp7qxM_-EugTMRYxjidkjsWPsxEnGQvDw6QU=8FBFE890")
                .auth()
                .basic("merlintomb@gmail.com","9YX12345sXZ02927-JtEN4UEr2TsNiEMJRuwoEWAgPAJpSJI1lnzi4PR7UDCyiNXreGFS7o_6jG2duDYMwhKLee9G9kW5kQC0HCXPotZGgM9NiUjMjaIZ7xFp7qxM_-EugTMRYxjidkjsWPsxEnGQvDw6QU=8FBFE890")
                .body(JIRA_Payloads.CreateIssuePayload())
                .when()
                .log().all()
                .post("/rest/api/3/issue")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .extract().asString();
        System.out.println(createIssueDetail);
        bugId=bugId+CommonUtilities.getRawToJson(createIssueDetail,"id");
        System.out.println(" The bug ID  received is : "+ bugId);
    }

    @Test(dependsOnMethods = "c")
    public void addAttachmentToBugCreated(){
        RestAssured.baseURI="https://merlintomb.atlassian.net";



        String multiPartAttachments=given().
                header("Content-Type","application/json")
                .header("X-Atlassian-Token","no-check")
                .pathParams("key",bugId)
                .multiPart("file", new File("C:\\Users\\Vijay_Yadav\\Downloads\\Feb14_Leave.png"))
                .when()
                .log().all()
                .post("rest/api/3/issue/{key}/attachments")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().asString();
    }
}
