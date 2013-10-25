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
//public class TestJobAttributeSet {
//
//    @Test
//    public void test() throws IOException
//    {
//
//        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
//
//        JobController myJobController = myCrowdFlower.getJobController("207321");
//        try
//        {
//
//            System.out.println(myJobController.getAttribute(JobAttribute.CML));
//
//            myJobController.clear();
//            myJobController.addProperty("units_per_assignment",
//                            "1");
//
//            myJobController.update();
//            myJobController.refresh();
//            Assert.assertNotNull(myJobController
//                                         .getAttribute(JobAttribute.UNITS_PER_ASSIGNMENT));
//
//        }
//        catch (MalformedCrowdURLException e)
//        {
//            e.printStackTrace();
//        }
//        catch (NullAPIKeyException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//}
