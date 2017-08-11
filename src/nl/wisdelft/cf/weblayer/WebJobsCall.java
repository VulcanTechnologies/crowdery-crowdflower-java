package nl.wisdelft.cf.weblayer;


import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebJobsCall extends WebCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebJobsCall.class);

    public static JSONArray getJobs(String URL)
    {
        JSONArray json = null;
        URL crowdFlower;

        try
        {
            crowdFlower = new URL(URL);
            URLConnection yc = crowdFlower.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String jsonInput = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                jsonInput = jsonInput + inputLine;
            }
            in.close();

            json = new JSONArray(jsonInput);

        }
        catch (MalformedURLException e)
        {
            LOGGER.error(e.toString());
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }

        return json;

    }

}
