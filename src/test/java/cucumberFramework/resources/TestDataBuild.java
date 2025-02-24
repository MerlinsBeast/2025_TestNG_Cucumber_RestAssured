package cucumberFramework.resources;

import APITesting.POJOClasses.AddPlaces;
import APITesting.POJOClasses.Location;

import java.util.Arrays;

public class TestDataBuild {

    public AddPlaces addPlacePayload(String name, String language, String address){
        APITesting.POJOClasses.AddPlaces places= new AddPlaces();
        places.setAccuracy("50");
        places.setAddress(address);
        places.setLanguage(language);
        places.setPhone_number("8689946239");
        places.setName(name);
        APITesting.POJOClasses.Location loc= new Location();
        loc.setLat("13.13");
        loc.setLng("14.14");
        places.setLocation(loc);
        places.setWebsites("vijay@gmail.com");
        places.setTypes(Arrays.asList("Vijay","Shrikrishna","Yadav"));

        return places;
    }

    public String deletePlacePayload(String place_id){
        return "{\n" +
                "    \"place_id\":\""+place_id+"\"\n" +
                "}\n";
    }
}
