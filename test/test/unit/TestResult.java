package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.unit.*;
import org.json.*;

import java.io.*;

public class TestResult {

    public static void main(String[] args) throws JSONException, IOException
    {
        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
        UnitController myUnitController = myCrowdFlower.getUnitController();

        Unit myUnit = myUnitController.getUnit("206783",
                                               "299087977");
        Result result = new Result(myUnit.getAttribute(UnitAttribute.RESULTS));
        System.out.println(result.getJudgments());
    }

}
