package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.unit.*;
import org.json.*;

public class TestUnitCreate {

    /**
     * This example create a UnitController in given JobController
     *
     * @param args
     */

    public static void main(String[] args) throws JSONException
    {
        UnitController myUnitController = CrowdFlowerFactory.getUnitController();

        JSONObject json = new JSONObject();

        try
        {

            json.put("phone",
                     "911");
            json.put("name",
                     "debarshi-basak");
            json.put("company",
                     "tudelft");
            json.put("email",
                     "asdasd@asda.com123");

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Unit myUnit = new Unit();
        myUnit.addProperty(UnitAttribute.JOB_ID, "206783");
        myUnit.addProperty("data",
                         json.toString());
        Unit myUnitAfterCreation = myUnitController.create(myUnit);
        System.out.println(myUnitAfterCreation);

    }
}
