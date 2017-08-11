package nl.wisdelft.cf.judgment;


import com.google.common.annotations.VisibleForTesting;
import nl.wisdelft.cf.datamodel.Judgment;
import nl.wisdelft.cf.job.JobController;
import nl.wisdelft.cf.weblayer.WebCall;
import nl.wisdelft.cf.weblayer.WebUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static nl.wisdelft.cf.weblayer.WebUtil.convertAttributesToNameValuePair;

public class JudgmentControllerImpl implements JudgmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    private final String URL = "https://api.crowdflower.com/v1/jobs";
    private final String apiKey;

    @VisibleForTesting
    public JudgmentControllerImpl(String apiKey)
    {
        this.apiKey = apiKey;
    }

    @Override
    public void create(Judgment aJudgment)
    {

        String augURL = WebUtil.urlTransform(URL,
                                              "/" + aJudgment.getJobId()
                                              + "/judgments.json?key=" + apiKey);
        WebCall.create(augURL,convertAttributesToNameValuePair(aJudgment.getAttributes()));
    }

    @Override
    public Judgment getJudgment(String aJobId,String aJudgmentId)
    {
        String augURL = WebUtil.urlTransform(URL,
                                              "/" + aJobId
                                              + "/judgments/" + aJudgmentId + ".json?key=" + apiKey);
        try
        {
            return new Judgment(new JSONObject(WebCall.get(augURL)));
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }
        return null;
    }

    @Override
    public void update(Judgment aJudgment)
    {
        String myMorph = String.format("/%s/judgments/%s.json?key=%s", aJudgment.getJobId(),
                                       aJudgment.getJudgmentId(),
                                       apiKey);

        String augURL = WebUtil.urlTransform(URL,
                                                myMorph);
        WebCall.update(augURL, convertAttributesToNameValuePair(aJudgment.getAttributes()));
    }

    @Override
    public void delete(String aJobId, String aJudgmentId)
    {
        String myMorph = "/" + aJobId
                         + "/judgments/" + aJudgmentId + ".json?key=" + apiKey;

        String augURL = WebUtil.urlTransform(URL,
                                                myMorph);
        WebCall.delete(augURL);
    }

    @Override
    public String read(Map<String, String> param)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
