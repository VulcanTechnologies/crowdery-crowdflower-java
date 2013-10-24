package nl.wisdelft.cf.datamodel;

import com.google.common.collect.*;
import nl.wisdelft.cf.judgment.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.json.*;
import org.slf4j.*;

import java.util.*;

public class Judgment {

    private String id = "";
    private String theJobId = "";
    private List<NameValuePair> attributes;
    private static final Logger LOGGER = LoggerFactory.getLogger(Judgment.class);

    public Judgment(final JSONObject aRawJudgment)
    {
        id = "";
        theJobId = "";
        attributes = Lists.newArrayList();
        jsonIterate(aRawJudgment);
    }

    public Judgment(String aId, String aJobId, Set<NameValuePair> aAttributes)
    {
        id = aId;
        theJobId = aJobId;
        attributes = Lists.newArrayList();
    }

    public void addProperty(
            String name,
            String value)
    {
        if (name.equals("id"))
        {
            id = value;
        }

        if (name.equals("job_id"))
        {
            theJobId = value;
        }

        attributes.add(new BasicNameValuePair("judgment[" + name + "]",
                                              value));
    }

    public void addProperty(
            JudgAttribute name,
            String value)
    {
        addProperty(name.toString(),
                    value);
    }

    public String getProperty(String name)
    {
        for (NameValuePair nameValue : attributes)
        {
            if (ifPropertyMatched(name, nameValue))
            {
                return nameValue.getValue();
            }
        }

        return null;
    }

    public String getProperty(JudgAttribute name)
    {
        return getProperty(name.toString());
    }

    public String getJudgmentId()
    {
        return id;
    }

    public String getJobId()
    {
        return theJobId;
    }

    public List<NameValuePair> getAttributes()
    {
        return attributes;
    }

    private void jsonIterate(JSONObject json)
    {

        Iterator iterate;
        try
        {

            iterate = json.keys();

            while (iterate.hasNext())
            {
                extractPropertyAndAddAsAttribute(json, iterate);

            }
        }
        catch (JSONException e)
        {
            LOGGER.error("Cannot parse the incoming job details : ",
                         e);
        }
    }

    private void extractPropertyAndAddAsAttribute(final JSONObject json, final Iterator aIterate) throws JSONException
    {
        String key = (String) aIterate.next();
        addProperty(key,
                    json.get(key).toString());
    }

    private boolean ifPropertyMatched(final String name, final NameValuePair nameValue)
    {
        return nameValue.getName().equals("judgment[" + name + "]");
    }

}
