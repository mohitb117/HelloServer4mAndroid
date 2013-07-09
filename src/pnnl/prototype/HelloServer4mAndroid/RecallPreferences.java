package pnnl.prototype.HelloServer4mAndroid;

/**
 * Created by bhal810 on 7/8/13.
 */



public class RecallPreferences
{
    enum DietTypes
    {
        VEG("V"), VEGAN("G"), CELIAC("C"), LACTOSE("L");

        private String DietType;

        private DietTypes(String s)
        {
            DietType = s;
        }

        public String getDietTypes()
        {
            return DietType;
        }

    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    private String locationName;

    public DietTypes getD1()
    {
        return d1;
    }

    public void setD1(DietTypes d1)
    {
        this.d1 = d1;
    }

    private DietTypes d1;

}
