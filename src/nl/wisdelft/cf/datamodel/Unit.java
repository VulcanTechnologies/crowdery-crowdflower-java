package nl.wisdelft.cf.datamodel;

import nl.wisdelft.cf.unit.UnitAttribute;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Unit {
    private static final Logger LOGGER = LoggerFactory.getLogger(Unit.class);

    private String theUnitId = "";
    private String theJobId = "";
    private Map<String,String> theAttributes = new HashMap<>();

    public Unit() {
    }

    public Unit(final JSONObject aMeta) {
        parse(aMeta);
    }

    private void parse(final JSONObject aMeta)
    {
        Iterator iterate = aMeta.keys();

        while (iterate.hasNext())
        {
            addAttributes(aMeta, iterate);
        }
    }

    public void addProperty(
            String property,
            String value)
    {
        if (property.equals("id"))
        {
            theUnitId = value;
        }

        if (property.equals("job_id"))
        {
            theJobId = value;
        }

        theAttributes.put("unit[" + property + "]", value);
    }

    public void addProperty(
            UnitAttribute property,
            String value)
    {
        addProperty(property.toString(),
                    value);
    }

    public String getAttribute(String property)
    {
        return theAttributes.get("unit[" + property + "]");
    }

    public String getAttribute(UnitAttribute property)
    {
        return getAttribute(property.toString());
    }

    public String getUnitId()
    {
        return theUnitId;
    }

    public String getJobId()
    {
        return theJobId;
    }

    public Map<String,String> getAttributes()
    {
        return theAttributes;
    }

    private void addAttributes(final JSONObject aMeta, final Iterator aIterate)
    {
        String key = (String) aIterate.next();
        try
        {
            addProperty(key,
                        aMeta.get(key).toString());
        }
        catch (JSONException e)
        {
            LOGGER.error("Cannot parse the incoming unit details : ",
                    e);
        }
    }
}
