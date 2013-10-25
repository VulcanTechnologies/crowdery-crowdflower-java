package test.unit;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.exception.*;
import nl.wisdelft.cf.job.*;
import nl.wisdelft.cf.unit.*;

public class TestUnits {

    public static void main(String[] args) throws NullAPIKeyException
    {
        JobController myJobController = CrowdFlowerFactory.getJobController();

        Job myJob = myJobController.getJob("207321");

        Unit myUnit = myJobController.getJobUnits(myJob.getId()).get(0);

        System.out.println(myUnit.getAttribute(UnitAttribute.DATA));

    }

}
