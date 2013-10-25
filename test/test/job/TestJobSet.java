//package nl.wisdelft.cf.acceptance.test.job;
//
//import nl.wisdelft.cf.*;
//import nl.wisdelft.cf.exception.*;
//import nl.wisdelft.cf.job.*;
//
//import java.io.*;
//
//public class TestJobSet {
//    public static void main(String[] args) throws IOException
//    {
//
//        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
//
//        JobController myJobController = myCrowdFlower.getJobController("206783");
//        try
//        {
//
//            myJobController.refresh();
//
//            System.out.println(myJobController.getAttribute(JobAttribute.CML));
//
//            myJobController.clear();
//
//            myJobController.addProperty("instructions",
//                            "hello world4");
//
//            myJobController.update();
//            myJobController.refresh();
//
//
//            System.out.println(myJobController.getAttribute(JobAttribute.INSTRUCTIONS));
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
