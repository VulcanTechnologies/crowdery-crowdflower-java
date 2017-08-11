package nl.wisdelft.cf.unit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import nl.wisdelft.cf.datamodel.Unit;
import nl.wisdelft.cf.exception.MalformedCrowdURLException;
import nl.wisdelft.cf.weblayer.WebCall;
import nl.wisdelft.cf.weblayer.WebUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

import static nl.wisdelft.cf.weblayer.WebUtil.convertAttributesToNameValuePair;

public class UnitControllerImpl implements UnitController {


    private final static String URL = "https://api.crowdflower.com/v1/jobs";
    private final String theApiKey;

    private final Logger logger = LoggerFactory.getLogger(UnitControllerImpl.class);

    @VisibleForTesting
    public UnitControllerImpl(String apiKey)
    {
        theApiKey = apiKey;
    }

    /**
     * Do a web call
     */
    @Override
    public Unit getUnit(String aJobId, String aUnitId)
    {
        try
        {
            logger.info("Refreshing the units for job with id  - {}", aJobId);

            String url = WebUtil.urlTransform(URL,
                                                 "/" + aJobId + "/units/"
                                                 + aUnitId + ".json?key=" + theApiKey);

            return new Unit(WebCall.getMeta(url));
        }
        catch (MalformedCrowdURLException e)
        {
            logger.error(e.toString());
        }
        return null;
    }

    @Override
    public Unit create(Unit aUnit)
    {

        String augURL = URL + "/" + aUnit.getJobId() + "/" + "units.json?key=" + theApiKey;
        try
        {
            if (!theApiKey.isEmpty())
            {
                logger.info("Creating units for job with id  - " + aUnit.getJobId());
                return new Unit(new JSONObject(WebCall.create(augURL,
                                                                 convertAttributesToNameValuePair(aUnit.getAttributes()))));
            }
        }
        catch (JSONException e)
        {
            logger.error(e.toString());
        }

        return null;
    }

    @Override
    public void update(Unit aUnit)
    {
        List<NameValuePair> myAttributes = convertAttributesToNameValuePair(aUnit.getAttributes());
        if (!(myAttributes.size() == 0))
        {
            String augURL = URL + "/" + aUnit.getJobId() + "/" + "units/" + aUnit.getUnitId() + ".json?key=" + theApiKey;
            logger.info("Updating unit - " + aUnit.getUnitId());
            WebCall.update(augURL, myAttributes);
        }
    }

    @Override
    public void delete(String aJobId, String aUnitId)
    {
        logger.info("Deleting unit - " + aUnitId);
        String myURL = WebUtil.urlTransform(URL, "/" + aJobId + "/" + aUnitId + ".json?key=" + theApiKey);
        WebCall.delete(myURL);
    }

    @Override
    public void addGold(
            Unit aUnit,
            String legend,
            String value,
            String reason)
    {
        String url = WebUtil.urlTransform(URL,
                                             "/" + aUnit.getJobId() + "/units/" + aUnit.getUnitId()
                                             + ".json?key=" + theApiKey);
        try
        {
            String data = aUnit.getAttribute(UnitAttribute.DATA);
            JSONObject json = new JSONObject(data);
            Iterator iterate = json.keys();
            List<NameValuePair> myAttributes = Lists.newArrayList();

            while (iterate.hasNext())
            {
                String key = iterate.next().toString();
                myAttributes.add(new BasicNameValuePair(
                        "unit[data][" + key + "]",
                        json.get(key).toString()));
            }

            myAttributes.add(new BasicNameValuePair("unit[golden]",
                                                    "true"));
            myAttributes.add(new BasicNameValuePair("unit[data][" + legend
                                                    + "_gold][]",
                                                    value));
            myAttributes.add(new BasicNameValuePair("unit[data][" + legend
                                                    + "_gold_reason][]",
                                                    reason));
            WebCall.update(url,
                              myAttributes);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void removeGold(String aJobId, String aUnitId)
    {
        String url = WebUtil.urlTransform(URL,
                                             "/" + aJobId + "/units/" + aUnitId
                                             + ".json?key=" + theApiKey);
        List<NameValuePair> myAttributes = Lists.newArrayList();

        myAttributes.add(new BasicNameValuePair("unit[golden]",
                                                "false"));
        WebCall.update(url,
                          myAttributes);
    }

}
