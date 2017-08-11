package nl.wisdelft.cf.datamodel;

import nl.wisdelft.cf.job.JobAttribute;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

    private String id = "";
    private Map<String,String> theAttributes = new HashMap<>();

    public Job(
            final String aId,
            final Map<String,String> aAttributes)
    {
        id = aId;
        theAttributes = aAttributes;
    }

    public Job()
    {
    }

    public Job(final JSONObject aJson)
    {
        jsonIterate(aJson);
    }

    public String getId()
    {
        return id;
    }

    public Map<String,String> getAttributes()
    {
        return theAttributes;
    }

    /*
    To be replaced with jackson later
    */
    @SuppressWarnings("rawtypes")
    private void jsonIterate(JSONObject json)
    {

        Iterator iterate;
        try
        {

            iterate = json.keys();

            while (iterate.hasNext())
            {
                addAttribute(json, iterate);
            }
        }
        catch (JSONException e)
        {
            LOGGER.error("Cannot parse the incoming job details : ",
                         e);
        }
    }

    private void addAttribute(final JSONObject json, final Iterator aIterate) throws JSONException
    {
        String key = (String) aIterate.next();
        addProperty(key,
                    json.get(key).toString());
    }

    /*
    To be replaced with a builder pattern or with jackson
     */
    public void addProperty(
            String aProperty,
            String aValue)
    {

        if (aProperty.equals("id"))
        {
            id = aValue;
        }

        LOGGER.info("Adding aProperty" + aProperty + " to job with id  - {}", id);

        theAttributes.put("job[" + aProperty + "]", aValue);

    }

    public String getAttribute(String propertyName)
    {
       return theAttributes.get("job[" + propertyName + "]");
    }

    public void addProperty(
            JobAttribute property,
            String value)
    {
        addProperty(property.toString(), value);
    }

    public String getAttribute(JobAttribute propertyName)
    {
        return getAttribute(propertyName.toString());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id,
                            theAttributes);
    }

    @Override
    public boolean equals(final Object o)
    {
        return Objects.equals(getClass(),
                o.getClass()) &&
               Objects.equals(id,
                              ((Job) o).getId()) &&
               Objects.equals(theAttributes,
                              ((Job) o).getAttributes());
    }
}
