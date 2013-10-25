//package nl.wisdelft.cf.acceptance.test.job;
//
//import nl.wisdelft.cf.*;
//import nl.wisdelft.cf.exception.*;
//import nl.wisdelft.cf.job.*;
//import org.junit.*;
//
//import java.io.*;
//
//public class TestUpdateJob {
//
//    @Test
//    public void test() throws IOException
//    {
//
//        CrowdFlower myCrowdFlower = new CrowdFlowerImpl();
//
//        JobController myJobController = myCrowdFlower.getJobController("214509");
//
//        myJobController.addProperty("instructions",
//                        "Select the best candidate");
//
//        // Note while updating the custom form needs a required field
//
//        myJobController.addProperty(
//                "cml",
//                "<p>Company Name - {{company}}</p><p> Name -{{name}}</p><cml:checkbox validates='required' label='Text' class='' />");
//        try
//        {
//            myJobController.update();
//
//        }
//        catch (NullAPIKeyException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//}
