package nl.wisdelft.cf.weblayer;

import com.google.common.collect.Lists;
import nl.wisdelft.cf.exception.CrowdFlowerException;
import nl.wisdelft.cf.exception.MalformedCrowdURLException;
import nl.wisdelft.cf.unit.UnitControllerImpl;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public class WebCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnitControllerImpl.class);

    @Web
    public static String get(String url)
    {
        HttpClient httpClient = new DefaultHttpClient();
        String output = "";

        HttpGet get = new HttpGet(url);

        try
        {
            LOGGER.info("HTTP GET @ url - {}",
                        url);

            output = WebUtil.readResponse(httpClient.execute(get));
            WebUtil.trapException(output);

        }
        catch (ClientProtocolException e)
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

        return output;
    }

    public static String create(
            String url,
            List<NameValuePair> attributes)
    {
        HttpClient httpClient = new DefaultHttpClient();

        String output = "";

        try
        {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(attributes));

            LOGGER.info("HTTP POST @ url - {}",
                        url);

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

        return output;
    }

    public static void update(
            String url,
            Collection<NameValuePair> attributes)
    {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(url);
            put.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(attributes)));

            LOGGER.info("HTTP PUT @ url - " + url);

            WebUtil.readResponse(httpClient.execute(put));

        }
        catch (ClientProtocolException e)
        {
            LOGGER.error(e.toString());
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }

    }

    public static void put(String url)
    {
        HttpClient httpClient = new DefaultHttpClient();

        try
        {

            HttpPut put = new HttpPut(url);

            LOGGER.info("HTTP PUT @ url - " + url);

            System.out.println(httpClient.execute(put));

        }
        catch (ClientProtocolException e)
        {
            LOGGER.error(e.toString());
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString());
        }

    }

    public static JSONObject getMeta(String url) throws MalformedCrowdURLException
    {
        URL crowdFlower;

        try
        {
            crowdFlower = new URL(url);

            LOGGER.info("Reading url - " + url);

            return new JSONObject(WebUtil.urlReader(crowdFlower));
        }
        catch (MalformedURLException e1)
        {

            throw new MalformedCrowdURLException(url);
        }
        catch (JSONException e)
        {
            LOGGER.error(e.toString());

        }
        return null;

    }

    public static void delete(String url)
    {
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            String output;
            HttpDelete delete = new HttpDelete(url);

            LOGGER.info("HTTP DELETE @ url - " + url);


            HttpResponse response = httpClient.execute(delete);
            output = WebUtil.readResponse(response);
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
}
