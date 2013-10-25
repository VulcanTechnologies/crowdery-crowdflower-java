package test.examples;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.job.*;
import nl.wisdelft.cf.unit.*;
import org.junit.*;
import org.junit.Test;
import test.*;

import java.io.*;

import static org.fest.assertions.api.Assertions.assertThat;

public class FullScaleJob {

    private static final String INSTRUCTION = CrowdFlowerTestDataFactory.createInstruction();
    private CrowdFlower theCrowdFlower;
    private static final String CML = CrowdFlowerTestDataFactory.createCML();

    @Before
    public void setUp() throws IOException
    {
        theCrowdFlower = new CrowdFlowerImpl();
    }

    @Test
    public void endToEndCrowdFlower()
    {
        /* Creates an empty job */

        try
        {
            JobController myJobController = theCrowdFlower.getJobController();

			/* Updates the empty myJobController */
            // Note it is important to clear the properties

            Job myJob = CrowdFlowerTestDataFactory.createJob();
            Job myJobAfterCreation = myJobController.create(myJob);

            assertThat(myJobAfterCreation.getAttribute(JobAttribute.INSTRUCTIONS)).isEqualTo(INSTRUCTION);
            assertThat(myJobAfterCreation.getAttribute(JobAttribute.CML)).isEqualTo(CML);

            Thread.sleep(1500);

            assertThat(myJobController.getUnitsStatus(myJobAfterCreation.getId())).isNotNull();

			/* Creating a myUnitController */
            UnitController myUnitController = theCrowdFlower.getUnitController();
            Unit myUnit = new Unit();

            myUnit.addProperty("job_id",myJobAfterCreation.getId());
            myUnit.addProperty("data", CrowdFlowerTestDataFactory.createUnitData());
            myUnitController.create(myUnit);

            assertThat(myJobController.getJobUnits(myJobAfterCreation.getId()).size()).isEqualTo(1);
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
