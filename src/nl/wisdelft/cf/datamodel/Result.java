package nl.wisdelft.cf.datamodel;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {
    private static final Logger LOGGER = LoggerFactory.getLogger(Result.class);

    private String judgments = "";
    private String agg = "";
    private String confidence = "";

    public Result(String result)
    {
        try
        {
            JSONObject json = new JSONObject(result);
            JSONObject text = new JSONObject(json.get("text").toString());
            judgments = json.get("judgments").toString();
            agg = text.get("agg").toString();
            confidence = text.get("confidence").toString();
        }
        catch (JSONException e)
        {
            LOGGER.error("Cannot parse the incoming result details : ",
                    e);
        }
    }

    public String getAggregation()
    {
        return agg;
    }

    public String getConfidence()
    {
        return confidence;
    }

    public String getJudgments()
    {
        return judgments;
    }
}
