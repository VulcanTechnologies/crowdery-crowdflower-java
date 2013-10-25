package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.unit.*;
import org.json.*;

public class TestUnitUpdate {
    public static void main(String[] args) throws JSONException
    {
        UnitController myUnitController = CrowdFlowerFactory.getUnitController();

        Unit myUnit = new Unit();

        JSONObject json = new JSONObject();

        try
        {

            json.put("phone",
                     "12321");
            json.put("name",
                     "debarshi");
            json.put("company",
                     "asda");
            json.put("email",
                     "asdasd@asda.com");

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        myUnit.addProperty(UnitAttribute.DATA,
                         json.toString());
        myUnit.addProperty(UnitAttribute.JOB_ID, "279897");
        myUnit.addProperty(UnitAttribute.ID, "337282470" );

        myUnitController.update(myUnit);
    }

}
