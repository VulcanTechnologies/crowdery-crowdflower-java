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
//public class TestJobCreation {
//
//    @Test
//    public void test() throws IOException
//    {
//
//        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
//
//        JobController myJobController = myCrowdFlower.getJobController();
//
//
//        myJobController.addProperty("title",
//                        "WIS test run " + System.currentTimeMillis());
//        myJobController.addProperty("instructions",
//                        "This instruction is from Java");
//        myJobController.addProperty(
//                "cml",
//                "<p>Company{{company}}</p>{{company}}<cml:text validates='required' label='Text' class='' />");
//
//        myJobController.create();
//
//        System.out.println(myJobController.getAttribute(JobAttribute.ID));
//        try
//        {
//
//            myJobController.upload("log/crowd.csv",
//                       "text/csv");
//
//            myJobController.refresh();
//
//        }
//        catch (NullAPIKeyException e)
//        {
//            e.printStackTrace();
//        }
//        catch (MalformedCrowdURLException e)
//        {
//            e.printStackTrace();
//        }
//
//		/*
//		 * Tried the upload without sleep
//		 * Looks like the upload happens asynchronously
//		 * It might probably be because they use nginx server
//		 * So sleep for sometime before you check for the new unit
//		 *
//		 */
//        try
//        {
//            Thread.sleep(1000);
//        }
//        catch (InterruptedException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        Assert.assertTrue(myJobController.getJobUnits().size() > 0);
//    }
//
//}
