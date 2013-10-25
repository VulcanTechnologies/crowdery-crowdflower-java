package test.job;

import nl.wisdelft.cf.*;
import nl.wisdelft.cf.job.*;

import java.io.*;

public class TestLegend {

    public static void main(String[] args) throws IOException
    {
        CrowdFlower crowd = new CrowdFlowerImpl();
        JobController myJobController = crowd.getJobController();
        System.out.println(myJobController.legend("214567"));
    }

}
