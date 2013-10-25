package nl.wisdelft.cf.acceptance.test.examples;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.datamodel.*;
import nl.wisdelft.cf.exception.*;
import nl.wisdelft.cf.job.*;
import org.junit.*;
import nl.wisdelft.cf.acceptance.test.*;

import java.io.*;

public class TestDataUpload {

    private static final String PATH = CrowdFlowerTestDataFactory.createPathToData();
    private static final String APPLICATION_TYPE = CrowdFlowerTestDataFactory.createApplicationType();
    private JobController theJobController;

    @Before
    public void setUp() throws IOException
    {
        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
        theJobController = myCrowdFlower.getJobController();
    }

    @Ignore
    @Test
    public void shouldUploadData()
    {
        Job myJob = CrowdFlowerTestDataFactory.createSampleJob();
        theJobController.create(myJob);
        try
        {
            theJobController.upload(myJob, PATH,
                          APPLICATION_TYPE);
        }
        catch (NullAPIKeyException e)
        {
            e.printStackTrace();
        }
    }

}
