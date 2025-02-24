package TestData;

public class LibraryFunctions {

    public static String AddBook(String aisle, String isbn){
        String addBookPayload="{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";
        return addBookPayload;
    }

    public static String DeleteBook(String aisle, String isbn){
        String deleteBookPayload="\"ID\" : \""+(isbn+aisle)+"\"";
        return deleteBookPayload;
    }
}
