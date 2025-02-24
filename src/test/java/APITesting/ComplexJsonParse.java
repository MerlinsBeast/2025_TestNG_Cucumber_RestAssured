package APITesting;

import TestData.MockedComplexJson;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath jsonPath= new JsonPath(MockedComplexJson.MockedUpJsonResponse());
        int coursesCount= jsonPath.get("courses.size()");
        System.out.println(coursesCount);
        int totalPurchaseAmount= jsonPath.get("dashboard.purchaseAmount");
        System.out.println("Purchase Amount is :  "+totalPurchaseAmount);
        int actTotal=0;
        System.out.println("before purchaing the amount spent is : "+actTotal);
        String rpaCopiesSold="";
        for(int i=0;i<coursesCount;i++){
            String eachCourse= jsonPath.getString("courses["+i+"].title");
            String eachCoursePrice= jsonPath.getString("courses["+i+"].price");
            String eachCourseCopies= jsonPath.getString("courses["+i+"].copies");
            System.out.println("printing each course name  :   "+eachCourse+" and its price is  :  "+eachCoursePrice);
            actTotal=actTotal+Integer.parseInt(eachCourseCopies)*Integer.parseInt(eachCoursePrice);
            if(eachCourse.equalsIgnoreCase("RPA")){
                rpaCopiesSold=rpaCopiesSold+eachCourseCopies;
            }
        }
        System.out.println("after purchaing the amount spent is : "+actTotal);
        Assert.assertTrue(totalPurchaseAmount==actTotal);
        System.out.println("The RPA copies sold are  : "+rpaCopiesSold );
    }
}
