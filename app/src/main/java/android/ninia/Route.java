package android.ninia;

public class Route {

    public static String startLocation;
    public static String endLocation;
    public static String duration;
    public static String distance;
    public static String steps;

    Route()
    {

    }

    Route(String a_startLocation, String a_endLocation, String a_duration, String a_distance)
    {
        startLocation = a_startLocation;
        endLocation = a_endLocation;
        duration = a_duration;
        distance = a_distance;
        steps = String.valueOf(Integer.parseInt(Route.distance.replaceAll("\\D+","")) * 5280 / 2.3);
    }
}
