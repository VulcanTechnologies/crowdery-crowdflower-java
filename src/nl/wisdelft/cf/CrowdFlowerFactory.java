package nl.wisdelft.cf;

import com.google.common.collect.Lists;
import nl.wisdelft.cf.job.JobController;
import nl.wisdelft.cf.unit.UnitController;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class CrowdFlowerFactory {

    private static CrowdFlower theCrowdFlower;

    public static CrowdFlower initialize(String apiKey) {
        theCrowdFlower = new CrowdFlowerImpl(apiKey);
        return theCrowdFlower;
    }

    public static void setProperties(String aPath)
    {
        Properties myProperties = new Properties();
        try
        {
            myProperties.load(new FileInputStream(aPath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        theCrowdFlower = new CrowdFlowerImpl(myProperties.getProperty("apiKey"));
    }

    public static UnitController getUnitController()
    {
        return theCrowdFlower.getUnitController();
    }

    public static JobController getJobController()
    {
        return theCrowdFlower.getJobController();
    }

    public List<NameValuePair> createAttributes()
    {
        return Lists.newArrayList();
    }

    public JSONObject createValues()
    {
        return new JSONObject();
    }
}
