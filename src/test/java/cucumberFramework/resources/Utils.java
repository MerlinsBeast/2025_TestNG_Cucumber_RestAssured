package cucumberFramework.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    static RequestSpecification requestSpecBuilder;
    public RequestSpecification requestSpecifications() throws IOException {
        if(requestSpecBuilder==null){
            PrintStream log= new PrintStream(new FileOutputStream("logging.txt"));
            requestSpecBuilder = new RequestSpecBuilder().
                    setBaseUri(getGlobalValue("baseURI"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType("application/json")
                    .addQueryParam("key",getGlobalValue("key")).build();
            return requestSpecBuilder;
        }
        return requestSpecBuilder;
    }

    public String getGlobalValue(String key) throws IOException {
        Properties prop= new Properties();
        FileInputStream inputFile= new FileInputStream("src/test/java/cucumberFramework/resources/global.properties");
        prop.load(inputFile);
        String propValue=prop.getProperty(key);
        return propValue;
    }
}
