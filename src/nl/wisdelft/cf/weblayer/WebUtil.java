package nl.wisdelft.cf.weblayer;

import com.google.common.collect.Lists;
import nl.wisdelft.cf.exception.CrowdFlowerException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class WebUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebJobsCall.class);

    public static String urlReader(URL crowdFlower)
    {
        String jsonInput = "";

        try
        {
            URLConnection yc = crowdFlower.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                jsonInput = jsonInput + inputLine;
            }

            in.close();

            trapException(jsonInput);

        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }
        catch (CrowdFlowerException e)
        {
            LOGGER.error(e.toString());
        }

        return jsonInput;
    }

    public static void trapException(String output) throws CrowdFlowerException
    {

        try
        {
            JSONObject error = new JSONObject(output);

            if (error.has("error"))
            {
                throw new CrowdFlowerException(error.get("error").toString());
            }

        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }
    }

    public static void trapException(int output) throws CrowdFlowerException
    {
        if ((output != 200) && (output != 202))
        {
            throw new CrowdFlowerException(String.format("Did not receive correct response code. Received %s", output));
        }
    }

    public static String urlTransform(
            String baseurl,
            Map<String, String> param)
    {
        String url = baseurl;

        for (String key : param.keySet())
        {
            url = url + "&" + key + "=" + param.get(key);
        }

        return url;
    }

    public static String urlTransform(
            String baseurl,
            String morph)
    {
        return String.format("%s%s", baseurl, morph);
    }

    public static String readResponse(HttpResponse response)
    {
        String output = "";

        HttpEntity entity = response.getEntity();

        try
        {
            trapException(response.getStatusLine().getStatusCode());
        }
        catch (CrowdFlowerException e1)
        {
            e1.printStackTrace();
        }

        InputStream instream;
        try
        {
            instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    instream));

            // do something useful with the response
            output = output + reader.readLine();
            instream.close();
        }
        catch (IllegalStateException e)
        {
            LOGGER.error(e.toString());
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }

        return output;
    }


    public static List<NameValuePair> convertAttributesToNameValuePair(Map<String, String> aAttributes)
    {
        List<NameValuePair> myNameValuePairs = Lists.newArrayList();

        for (Map.Entry<String, String> myEntry : aAttributes.entrySet())
        {
            myNameValuePairs.add(new BasicNameValuePair(myEntry.getKey(), myEntry.getValue()));
        }
        return myNameValuePairs;
    }


}
