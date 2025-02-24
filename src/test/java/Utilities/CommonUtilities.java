package Utilities;

import io.restassured.path.json.JsonPath;

public class CommonUtilities {

    public static String getRawToJson(String response, String nodeLocation){

        JsonPath jsonPath= new JsonPath(response);
        String valueOfNode=jsonPath.get(nodeLocation);
    return valueOfNode;
    }
}
