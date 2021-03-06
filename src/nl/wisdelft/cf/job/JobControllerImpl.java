package nl.wisdelft.cf.job;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import nl.wisdelft.cf.datamodel.Job;
import nl.wisdelft.cf.datamodel.Judgment;
import nl.wisdelft.cf.datamodel.Order;
import nl.wisdelft.cf.datamodel.Unit;
import nl.wisdelft.cf.exception.MalformedCrowdURLException;
import nl.wisdelft.cf.exception.NullAPIKeyException;
import nl.wisdelft.cf.judgment.JudgmentController;
import nl.wisdelft.cf.judgment.JudgmentControllerImpl;
import nl.wisdelft.cf.unit.UnitController;
import nl.wisdelft.cf.unit.UnitControllerImpl;
import nl.wisdelft.cf.weblayer.WebCall;
import nl.wisdelft.cf.weblayer.WebJobCall;
import nl.wisdelft.cf.weblayer.WebUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static nl.wisdelft.cf.weblayer.WebUtil.convertAttributesToNameValuePair;

public class JobControllerImpl implements JobController {

    /**
     * This class is translates CrowdFlower JobController Builder web APIs to
     * corresponding Java APIs
     * <p/>
     * Note the JobController needs a validates='required' field, the exception thrown by
     * crowdFlower is not much descriptive
     * <p/>
     * This class is translates CrowdFlower JobController Builder web APIs to
     * corresponding Java APIs
     */

    private static final String URL = "https://api.crowdflower.com/v1/jobs";
    private String apiKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    /**
     * This constructor takes only the apiKey
     *
     * @param apiKey
     */
    public JobControllerImpl(String apiKey)
    {
        this.apiKey = apiKey;
    }

    /**
     * Set API key
     */

    public void setApiKey(String aApiKey)
    {
        apiKey = aApiKey;
    }

    /**
     * Creates the job via web api call On creation, web apis return the json
     * object which is used to set the attributes
     */

    @Override
    public Job create(Job aJob)
    {
        JSONObject json = null;
        try
        {
            LOGGER.info("Create the job with id  - {} ",
                        aJob.getId());

            String augURL = WebUtil.urlTransform(URL,
                                                    ".json?key=" + apiKey);
            json = new JSONObject(WebJobCall.createJob(augURL, convertAttributesToNameValuePair(aJob.getAttributes())));
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }

        return new Job(json);
    }

    /**
     * This method returns a json object with all job metadata parameters
     */

    @Override
    public Job getJob(String aJobId) throws NullAPIKeyException
    {

        try
        {
            LOGGER.info("Reading job with id  - {}",
                        aJobId);
            String augURL = WebUtil.urlTransform(URL,
                                                    "/" + aJobId + ".json?key="
                                                    + apiKey);


            if (!(aJobId.isEmpty() || apiKey.isEmpty()))
            {
                if (!augURL.isEmpty())
                {
                    return new Job(WebJobCall.getJob(augURL));
                }
            }
            else
            {
                throw new NullAPIKeyException();
            }
        }
        catch (MalformedCrowdURLException e)
        {
            LOGGER.error(e.toString());
        }

        return null;
    }


    /**
     * Upload the data as units in CrowdFlower
     */

    // force upload - check
    @Override
    public void upload(
            Job aJob,
            String absolutePath,
            String contentType)
            throws NullAPIKeyException
    {

        if (!apiKey.isEmpty())
        {

            String augURL = WebUtil.urlTransform(URL,
                                                    "/" + aJob.getId()
                                                    + "/upload.json?key=" + apiKey);

            LOGGER.info("Uploading data in job with id  - {} from path {}",
                        aJob.getId(),
                        absolutePath);

            WebJobCall.upload(absolutePath,
                                 augURL,
                                 contentType);
        }
        else
        {
            throw new NullAPIKeyException();
        }

    }

    /**
     * Update recalls the web apis with new attributes (if any)
     */
    @Override
    public void update(Job aJob) throws NullAPIKeyException
    {

        if (!apiKey.isEmpty())
        {

            String augURL = WebUtil.urlTransform(URL,
                                                    "/" + aJob.getId() + ".json?key="
                                                    + apiKey);

            LOGGER.info("Updating the job with id  - {}",
                        aJob.getId());

            WebCall.update(augURL,
                                 convertAttributesToNameValuePair(aJob.getAttributes()));

        }
        else
        {
            throw new NullAPIKeyException();
        }
    }

    /**
     * This method is same as getJobDetails() but the methods sets the JobController
     * attributes and enables end user to fetch any attribute using the
     * get(String) method.
     */


    @SuppressWarnings("rawtypes")
    @Override
    public List<Unit> getJobUnits(String aJobId, Integer page)
    {

        page = MoreObjects.firstNonNull(page, 1);

        // Fetch all the units on the given page
        List<Unit> units = Lists.newArrayList();
        String augURL = WebUtil.urlTransform(WebUtil.urlTransform(URL,
                                                "/" + aJobId
                                                + "/units.json?key=" + apiKey),
                ImmutableMap.of("page", page.toString()));

        LOGGER.info("Obtaining units for job with id  - {} ",
                    aJobId);

        JSONObject json = WebJobCall.getUnits(augURL);

        Iterator iterate = json.keys();

        while (iterate.hasNext())
        {
            String key = (String) iterate.next();
            UnitController unit = new UnitControllerImpl(apiKey);
            units.add(unit.getUnit(aJobId,key));

        }

        return units;

    }

    /**
     * For loading data from RSS Feed, JSON feed etc.
     */

    @Override
    public void upload(
            Job aJob,
            String url)
    {

        aJob.addProperty("uri",
                         url);
        String augURL = WebUtil.urlTransform(URL,
                                                "/" + aJob.getId()
                                                + "/upload.json?key=" + apiKey);

        LOGGER.info("Uploading data from url {} the job with id  - {}",
                    url,
                    aJob.getId());

        WebJobCall.uploadURI(augURL,convertAttributesToNameValuePair(aJob.getAttributes()));
    }

    @Override
    public JSONObject getUnitsStatus(String aJobId)
    {

        LOGGER.info("Obtaining the status of the job with id  - {}",
                    aJobId);

        return WebJobCall.getUnits(WebUtil.urlTransform(URL,
                                                              "/" + aJobId
                                                              + "/units/ping.json?key=" + apiKey));
    }

    @Override public void order(final Order aOrder)
    {
        LOGGER.info("Ordering job with id - {}", aOrder.getJobId());
        String url = URL + aOrder.getJobId() + "/orders.json?key="+apiKey;
        String myOrder = WebCall.create(url,
                convertAttributesToNameValuePair(aOrder.getAttributes()));
    }

    @Override
    public void pause(String aJobId)
    {
        LOGGER.info("Pausing job with id  - {}",
                    aJobId);
        WebCall.get(WebUtil.urlTransform(URL,
                                                  "/" + aJobId + "/pause.json?key="
                                                  + apiKey));
    }

    @Override
    public void resume(String aJobId)
    {
        LOGGER.info("Resuming job with id  - " + aJobId);
        WebCall.get(WebUtil.urlTransform(URL,
                                                  "/" + aJobId + "/resume.json?key="
                                                  + apiKey));
    }

    @Override
    public void cancel(String aJobId)
    {

        LOGGER.info("Cancelling job with id  - " + aJobId);
        WebCall.get(WebUtil.urlTransform(URL,
                                                  "/" + aJobId + "/cancel.json?key="
                                                  + apiKey));
    }

    @Override
    public String status(String aJobId)
    {

        LOGGER.info("Status of job with id  - " + aJobId);

        return WebCall.get(WebUtil.urlTransform(URL,
                                                         "/" + aJobId
                                                         + "/ping.json?key=" + apiKey));
    }

    @Override
    public void delete(String aJobId)
    {

        LOGGER.info("Deleting job with id  - " + aJobId);

        WebCall.delete(WebUtil.urlTransform(URL,
                                                     "/" + aJobId + ".json?key="
                                                     + apiKey));

    }

    @Override
    public List<Judgment> getJudgments(String aJobId, Integer page)
    {
        page = MoreObjects.firstNonNull(page, 1);

        try
        {

            String augURL = WebUtil.urlTransform(WebUtil.urlTransform(URL,
                                                    "/" + aJobId
                                                    + "/judgments.json?key=" + apiKey),
                    ImmutableMap.of("page", page.toString()));

            LOGGER.info("Getting judgments for job id  - {}",
                        aJobId);

            JSONObject json = new JSONObject(WebCall.get(augURL));

            List<Judgment> judgments = Lists.newArrayList();

            Iterator iterate = json.keys();

            while (iterate.hasNext())
            {
                String key = (String) iterate.next();

                JSONObject judgJson = new JSONObject(json.get(key).toString());

                //Very important part

                List<String> judglist = getListOfJudgments(judgJson);

                for (String jId : judglist)
                {
                    String url = WebUtil.urlTransform(URL,
                                                         "/" + aJobId
                                                         + "/judgments/" + jId + ".json?key=" + apiKey);

                    JSONObject myRawJudgment = new JSONObject(WebCall.get(url));

                    judgments.add(new Judgment(myRawJudgment));
                }
            }

            return judgments;

        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }

        return null;
    }



    @Override
    public void bulkSplit(
            String aJobId,
            String on,
            String with)
    {

        LOGGER.info("Bulk splitting job id  - " + aJobId);

        System.out.println("Bulk Splitting..");
        String augURL = WebUtil.urlTransform(URL,
                                                "/" + aJobId
                                                + "/units/split?key=" + apiKey + "&on=" + on + "&with=" + with);
        WebCall.put(augURL);
        System.out.println("Done");

    }

    @Override
    public Channel getChannels(String aJobId)
    {

        Channel channel = new Channel(aJobId);
        String augURL = WebUtil.urlTransform(URL,
                                                "/" + aJobId + "/channels?key="
                                                + apiKey);
        List<String> available;
        List<String> enabled;

        LOGGER.info("Getting channel for job id  - " + aJobId);

        JSONObject json;
        try
        {
            json = new JSONObject(WebCall.get(augURL));

            enabled = addEnabledChannel(json);

            available = addAvailableChannels(json);

            channel.setChannels(available,
                                enabled);
            return channel;
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }

        return null;
    }

    private List<String> addEnabledChannel(JSONObject json)
            throws JSONException
    {
        List<String> enabled = new ArrayList<String>();

        if (!json.get("enabled_channels").toString().isEmpty())
        {

            JSONArray arr = new JSONArray(json.get("enabled_channels")
                                                  .toString());

            for (int i = 0; i < arr.length(); i++)
            {
                enabled.add(arr.getString(i));
            }
        }
        return enabled;
    }

    private List<String> addAvailableChannels(JSONObject json)
            throws JSONException
    {
        List<String> available = new ArrayList<String>();

        if (!json.get("available_channels").toString().isEmpty())
        {
            JSONArray arrAv = new JSONArray(json.get("available_channels")
                                                    .toString());

            for (int i = 0; i < arrAv.length(); i++)
            {
                available.add(arrAv.getString(i));
            }
        }

        return available;
    }

    @Override
    public void setChannels(
            String aJobId,
            List<String> channels)
    {

        System.out.println("Setting channel");
        System.out.println(channels);
        String url = WebUtil.urlTransform(URL,
                                             "/" + aJobId + "/channels?key="
                                             + apiKey);

        // Locally declaring a new attribute for channels

        List<NameValuePair> attributes = new ArrayList<NameValuePair>();
        for (String channel : channels)
        {

            attributes.add(new BasicNameValuePair("channels[]",
                                                  channel));
        }

        WebCall.update(url,
                             attributes);

    }

    @Override
    public Unit getUnit(
            String aJobId,
            String aUnitId) throws JSONException
    {

        UnitController unit = new UnitControllerImpl(apiKey);

        return unit.getUnit(aJobId,
                            aUnitId);
    }

    /*
     * See later (non-Javadoc)
     *
     * @see nl.wisdelft.cf.job.JobController#getJudgment(java.lang.String)
     */
    @Override
    public Judgment getJudgment(String aJobId, String aJudgementId)
    {

        JudgmentController judge = new JudgmentControllerImpl(apiKey);
        return judge.getJudgment(aJobId, aJudgementId);
    }

    @Override
    public void upload(
            String aJobId,
            String absolutePath,
            String contentType,
            Map<String, String> param)
    {

        LOGGER.info("Upload with parameters for job id  - " + aJobId);

        String augURL = WebUtil.urlTransform(
                WebUtil.urlTransform(URL,
                                        "/" + aJobId + "/upload.json?key="
                                        + apiKey),
                param);
        WebJobCall.upload(absolutePath,
                             augURL,
                             contentType);
    }


    @Override
    public JSONObject legend(String aJobId)
    {

        LOGGER.info("Fetching Legends of job id  - " + aJobId);

        String url = WebUtil.urlTransform(URL,
                                             "/" + aJobId + "/legend.json?key=" + apiKey);

        try
        {
            return new JSONObject(WebCall.get(url));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setPayPerAssignment(
            Job aJob,
            String pay)
    {

        LOGGER.info("Adding for job id  - " + aJob.getId());

        aJob.addProperty("payment_cent",
                         pay);
        String augURL = WebUtil.urlTransform(URL,
                                                "/" + aJob.getId() + ".json?key="
                                                + apiKey);
        WebCall.update(augURL,
                             convertAttributesToNameValuePair(aJob.getAttributes()));

    }

    /**
     * [ web call ] Asynchronous upload for jobs;
     */

    @Override
    public void upload(
            Job aJob,
            String absolutePath,
            String contentType,
            boolean async)
            throws NullAPIKeyException
    {

        if (async)
        {
            ExecutorService myExecutorService = Executors.newSingleThreadExecutor();
            myExecutorService.submit(new AsynchronousUploadTask(this,
                                                                aJob,
                                                                absolutePath,
                                                                contentType));
        }
        else
        {

            upload(aJob,
                   absolutePath,
                   contentType);

        }

    }

    @Override
    public void addRemoveGold(
            String aJobId,
            Map<String, String> param)
    {


        String url = WebUtil.urlTransform(WebUtil.urlTransform(URL, "/" + aJobId + "/gold?key=" + apiKey),
                                             param);

        WebCall.put(url);
    }

    private List<String> getListOfJudgments(final JSONObject aJudgJson) throws JSONException
    {
        String ids = aJudgJson.get("_ids").toString();
        return new ArrayList<String>(Arrays.asList(ids.substring(1, ids.length() - 1)
                                                           .split(",\\s*")));
    }

}
