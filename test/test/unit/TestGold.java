package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.job.*;
import nl.wisdelft.cf.unit.*;
import org.json.*;

import java.io.*;

public class TestGold {

    public static void main(String[] args) throws IOException, JSONException
    {
        JobController myJobController = CrowdFlowerFactory.getJobController();
        UnitController myUnitController = CrowdFlowerFactory.getUnitController();

        Unit myUnit = myJobController.getUnit("214524", "307457390");

        myUnitController.addGold(myUnit, "contains",
                                 "true",
                                 "asdsafafas");


    }
}
