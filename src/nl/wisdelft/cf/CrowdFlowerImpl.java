package nl.wisdelft.cf;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import nl.wisdelft.cf.datamodel.Job;
import nl.wisdelft.cf.job.JobController;
import nl.wisdelft.cf.job.JobControllerImpl;
import nl.wisdelft.cf.unit.UnitController;
import nl.wisdelft.cf.unit.UnitControllerImpl;
import nl.wisdelft.cf.weblayer.WebJobsCall;
import nl.wisdelft.cf.weblayer.WebUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class CrowdFlowerImpl implements CrowdFlower {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrowdFlowerImpl.class);
    private final static String URL = "https://api.crowdflower.com/v1/";

    /**
     * BuilderJobs fetches the list of Jobs in -Your Jobs-
     */

    private final String API_KEY;

    /**
     * This constructor consumes a Path to the properties file
     * and picks up corresponding API key
     *
     * @param aPropertiesPath
     */

    public CrowdFlowerImpl(Properties aPropertiesPath)
    {
        API_KEY = aPropertiesPath.getProperty("apiKey");
    }

    /**
     *
     * This constructor takes API String and assigns it to the Field,
     * Does not require any path to properties file
     *
     * @param aAPIKey
     */
    public CrowdFlowerImpl(String aAPIKey)
    {
        API_KEY = aAPIKey;
    }

    /**
     * This methods returns list of jobs that you have submitted so far. Since
     * it is a list, we can iterate and fetch individual jobs
     */
    @Override
    public List<Job> getJobs()
    {
        try
        {
            JSONObject json;
            List<Job> myJobs = Lists.newArrayList();

            // Augment the URL
            String augUrl = WebUtil.urlTransform(URL,"jobs.json?key=" + API_KEY);

            LOGGER.debug("Web call @ URL - " + augUrl);

            // Fetch the array of all the jobs
            JSONArray jsonArray = WebJobsCall.getJobs(augUrl);

            LOGGER.info("Creating list of Jobs");


            for (int i = 0; i < jsonArray.length(); i++)
            {
                json = jsonArray.getJSONObject(i);

                myJobs.add(new Job(json));

                LOGGER.info("{} Job added ",i);
            }

            return myJobs;

        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }
        return null;
    }

    @Override
    public String getApiKey()
    {
        return API_KEY;
    }

    @Override
    public JobController getJobController()
    {
        return new JobControllerImpl(API_KEY);
    }

    @Override
    public UnitController getUnitController()
    {
        return new UnitControllerImpl(API_KEY);
    }

    @Override
    public Account getAccount()
    {
        Account myAccount = new Account(API_KEY, Maps.<String, String>newHashMap());
        myAccount.refresh();
        return myAccount;
    }
}
