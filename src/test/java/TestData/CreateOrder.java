package TestData;

public class CreateOrder {

    public static String loginDetails(String productId){
String payload ="{\"orders\":[{\"country\":\"India\",\"productOrderedId\":\""+productId+"\"}]}";
        return payload;
    }
}
