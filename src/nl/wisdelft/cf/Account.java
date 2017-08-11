package nl.wisdelft.cf;

import nl.wisdelft.cf.weblayer.WebUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class Account {
    private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);
    private static final String URL = "https://api.crowdflower.com/v1/account.json";

    private Map<String, String> theAccountDetails;
    private String theApiKey;

    public Account(String aApiKey,
                   Map<String,String> aAccountDetails)
    {
        theApiKey = aApiKey;
        theAccountDetails = aAccountDetails;
    }

    public void refresh()
    {
        String wGet = "";
        JSONObject json;
        Iterator iterate;
        try
        {
            wGet = WebUtil.urlReader(new URL(URL + "?key=" + theApiKey));
            json = new JSONObject(wGet);
            iterate = json.keys();

            LOGGER.info("Adding account attributes");

            while (iterate.hasNext())
            {
                String key = (String) iterate.next();
                theAccountDetails.put(key,
                                   json.get(key).toString());
            }

        }
        catch (MalformedURLException e)
        {
            LOGGER.error(e.toString());
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }

    }

    public String get(String property)
    {
        return theAccountDetails.get(property);

    }

}
