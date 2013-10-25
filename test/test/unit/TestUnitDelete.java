package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.unit.*;
import org.json.*;

public class TestUnitDelete {

    public static void main(String[] args) throws JSONException
    {
        UnitController myUnitController = CrowdFlowerFactory.getUnitController();
        myUnitController.delete("207545","300144656" );
    }

}
