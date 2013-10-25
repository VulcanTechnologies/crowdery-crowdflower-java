//package nl.wisdelft.cf.acceptance.test.job;
//
//
//import nl.wisdelft.cf.*;
//import nl.wisdelft.cf.exception.*;
//import nl.wisdelft.cf.job.*;
//import nl.wisdelft.cf.order.*;
//import org.junit.*;
//
//import java.io.*;
//
//public class TestJobOrder {
//
//    @Test
//    public void test() throws IOException
//    {
//        String cml = "<img id=\"\" src=\"{{images}}\"><cml:checkbox validates=\"required\" label=\"Contains human\" class=\"\"/>";
//
//        CrowdFlower crowd = new CrowdFlowerImpl();
//        JobController myJobController = crowd.createJob();
//
//
//        myJobController.clear();
//
//        try
//        {
//            myJobController.upload("log/crowd.csv",
//                       "text/csv");
//        }
//        catch (NullAPIKeyException e1)
//        {
//            e1.printStackTrace();
//        }
//
//
//        myJobController.addProperty(JobAttribute.TITLE,
//                        "Detect a human in the picture");
//
//        myJobController.addProperty(
//                JobAttribute.INSTRUCTIONS,
//                "In the given picture find a human figure, If it has a human then click on contains a human");
//
//        myJobController.addProperty(JobAttribute.CML,
//                        cml);
//
//
//        myJobController.addProperty(JobAttribute.PAGES_PER_ASSIGNMENT,
//                        "1");
//        myJobController.addProperty(JobAttribute.UNITS_PER_ASSIGNMENT,
//                        "1");
//
//        try
//        {
//            myJobController.update();
//        }
//        catch (NullAPIKeyException e)
//        {
//            e.printStackTrace();
//        }
//
//        try
//        {
//            Thread.sleep(5000);
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//
//        Order order = myJobController.order();
//
//        order.setDebitUnitCount("3");
//        order.addChannel("amt");
//
///*
// *
// * Costs money
// *
// */
//        //	order.create();
//
//
//    }
//
//}
