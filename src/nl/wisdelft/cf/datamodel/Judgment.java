package nl.wisdelft.cf.datamodel;

import nl.wisdelft.cf.judgment.JudgAttribute;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Judgment {

    private String id = "";
    private String theJobId = "";
    private Map<String,String> attributes = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Judgment.class);

    public Judgment() {
        this("", "");
    }

    public Judgment(final JSONObject aRawJudgment)
    {
        jsonIterate(aRawJudgment);
    }

    public Judgment(String aId, String aJobId)
    {
        id = aId;
        theJobId = aJobId;
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

        attributes.put("judgment[" + name + "]", value);
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
        return attributes.get("judgment[" + name + "]");
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

    public Map<String,String> getAttributes()
    {
        return attributes;
    }

    private void jsonIterate(JSONObject json)
    {
        try
        {
            Iterator iterate = json.keys();

            while (iterate.hasNext())
            {
                String key = (String) iterate.next();
                addProperty(key, json.get(key).toString());
            }
        }
        catch (JSONException e)
        {
            LOGGER.error("Cannot parse the incoming job details : ",
                         e);
        }
    }

}
