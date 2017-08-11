package nl.wisdelft.cf.weblayer;

import nl.wisdelft.cf.exception.CrowdFlowerException;
import nl.wisdelft.cf.exception.MalformedCrowdURLException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

/**
 * This layer translates the cURL call of CrowdFlower to HttpClient 4.x
 * calls, And provides Object Relationship Mapping between JSON object and
 * JCrowdFlower's Java object
 *
 * @author debarshi
 */
public class WebJobCall extends WebCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebJobCall.class);

    /**
     * Opens an inputstream and reads the url GET
     *
     * @param URL
     * @return
     * @throws MalformedCrowdURLException
     */

    public static JSONObject getJob(String URL) throws MalformedCrowdURLException
    {
        JSONObject json = null;
        URL crowdFlower;

        try
        {
            crowdFlower = new URL(URL);

            json = new JSONObject(WebUtil.urlReader(crowdFlower));

            return json;

        }
        catch (MalformedURLException e1)
        {
            throw new MalformedCrowdURLException(URL);
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }

        return json;

    }

    /**
     * POST operation Creates the job
     *
     * @param URL
     * @param attributes
     * @return
     */

    public static String createJob(
            String URL,
            List<NameValuePair> attributes)
    {

        return create(URL,
                            attributes);
    }

    /**
     * Uploads data
     *
     * @param absolutePath
     * @param URL
     * @param type
     */
    @SuppressWarnings("deprecation")
    public static void upload(
            String absolutePath,
            String URL,
            String type)
    {
        HttpClient httpClient = new DefaultHttpClient();

        try
        {
           HttpPost post = new HttpPost(URL);

            post.setEntity(new FileEntity(new File(absolutePath),
                                          type));

            LOGGER.info("HTTP POST @ url - " + URL);

            HttpResponse response = httpClient.execute(post);

			/*
			 * There are two ways to do it
			 * Asynchronously and synchronously
			 * 
			 * This implementation is synchronous as
			 * it waits for the response for the outcome 
			 * of upload from the server
			 * 
			 */
			LOGGER.info("Response: {}", response.getEntity());
        }
        catch (MalformedURLException e)
        {
            LOGGER.error(e.toString());
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }

    }

    /**
     * Upload the uri specified in job[uri] property
     *
     * @param URL
     * @param attributes
     */
    public static void uploadURI(
            String URL,
            List<NameValuePair> attributes)
    {
        HttpClient httpClient = new DefaultHttpClient();

        try
        {
            String output = "";

            HttpPost post = new HttpPost(URL);
            post.setEntity(new UrlEncodedFormEntity(attributes));

            LOGGER.info("HTTP POST @ url - " + URL);

            output = WebUtil.readResponse(httpClient.execute(post));

            WebUtil.trapException(output);

        }
        catch (MalformedURLException e)
        {
            LOGGER.error(e.toString());
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }
        catch (CrowdFlowerException e)
        {
            LOGGER.error(e.toString());
        }

    }


    // TODO Read the HttpResponse and throw appropriate exception

    public static JSONObject getUnits(String url)
    {
        JSONObject json = null;
        URL crowdFlower;
        try
        {
            crowdFlower = new URL(url);

            json = new JSONObject(WebUtil.urlReader(crowdFlower));
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());
        }
        catch (MalformedURLException e)
        {
            LOGGER.error(e.toString());
        }

        return json;
    }
}
