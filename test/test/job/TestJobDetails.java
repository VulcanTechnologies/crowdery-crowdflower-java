//package nl.wisdelft.cf.acceptance.test.job;
//
//import nl.wisdelft.cf.*;
//import nl.wisdelft.cf.exception.*;
//import nl.wisdelft.cf.job.*;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.io.*;
//
//public class TestJobDetails {
//
//    @Test
//    public void test() throws IOException
//    {
//
//
//        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
//
//        JobController myJobController = myCrowdFlower.getJobController("206783");
//        try
//        {
//            Assert.assertNotNull(myJobController.getJob());
//        }
//        catch (NullAPIKeyException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//}
