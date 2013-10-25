//package nl.wisdelft.cf.acceptance.test.job;
//
//import nl.wisdelft.cf.*;
//import nl.wisdelft.cf.acceptance.test.*;
//import nl.wisdelft.cf.exception.*;
//import nl.wisdelft.cf.job.*;
//import org.junit.*;
//
//import java.io.*;
//
//public class TestJobAsyncUpload {
//
//    private JobController theJobController;
//
//    @Before
//    public void setUp() throws IOException
//    {
//        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
//        theJobController = myCrowdFlower.createJob();
//
//    }
//
//    @Test
//    public void shouldUploadDataAsThreaded() throws IOException
//    {
//        try
//        {
//            theJobController.upload(CrowdFlowerTestDataFactory.createPathToData(),
//                          CrowdFlowerTestDataFactory.createApplicationType(),
//                          true);
//        }
//        catch (NullAPIKeyException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//}
