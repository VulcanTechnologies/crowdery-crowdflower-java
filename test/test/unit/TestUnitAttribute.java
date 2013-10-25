package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.unit.*;
import org.json.*;

public class TestUnitAttribute {
    public static void main(String[] args) throws JSONException
    {
        UnitController myUnitController = CrowdFlowerFactory.getUnitController();
        Unit myUnit = myUnitController.getUnit("206783", "299087977");
        System.out.println(myUnit.getAttribute(UnitAttribute.JUDGMENTS_COUNT));
    }
}
