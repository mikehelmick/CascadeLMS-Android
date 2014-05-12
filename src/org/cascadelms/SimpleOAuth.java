package org.cascadelms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SimpleOAuth
{
    public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";

    private String consumerKey;
    private String secretKey;
    private String requestTokenUrl;
    private String accessTokenUrl;
    private String authorizeUrl;

    private String oauthToken;
    private String oauthTokenSecret;

    public SimpleOAuth(String consumerKey, String secretKey,
                       String requestTokenUrl,
                       String accessTokenUrl,
                       String autorizeUrl)
    {
        this.consumerKey = consumerKey;
        this.secretKey = secretKey;
        this.requestTokenUrl = requestTokenUrl;
        this.accessTokenUrl = accessTokenUrl;
        this.authorizeUrl = autorizeUrl;
    }

    public SimpleOAuth(String oauthToken)
    {
        this.oauthToken = oauthToken;
    }

    public void getRequestToken() throws IOException
    {
        URL url = new URL(requestTokenUrl + "?" + OAUTH_CONSUMER_KEY + "=" + consumerKey);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        if (request.getResponseCode() != HttpURLConnection.HTTP_OK)
        {
            throw new IOException("Error code: " + request.getResponseCode() + " : "
                    + request.getResponseMessage());
        } else
        {
            Map<String, List<String>> fields = request.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : fields.entrySet())
            {
                System.out.print(entry.getKey() + " : ");
                for (String val : entry.getValue())
                {
                    System.out.print(val + ", ");
                }
                System.out.println("");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = reader.readLine();
            while (line != null)
            {
                if (line.contains("oauth_token"))
                {
                    String keys[] = line.split("&");
                    for (String key : keys)
                    {
                        System.out.println(key);
                        if (key.startsWith(OAUTH_TOKEN + "="))
                        {
                            oauthToken = key.split("=")[1];
                        } else if (key.startsWith(OAUTH_TOKEN_SECRET + "="))
                        {
                            oauthTokenSecret = key.split("=")[1];
                        }
                    }
                }
                line = reader.readLine();
            }
        }
    }

    /**
     * Exchanges the request token for the access token.
     */
    public void exchangeToken() throws IOException
    {
        URL url = new URL(accessTokenUrl + "?" + OAUTH_TOKEN + "=" + oauthToken);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        if (request.getResponseCode() != HttpURLConnection.HTTP_OK)
        {
            throw new IOException("Error code: " + request.getResponseCode() + " : "
                    + request.getResponseMessage());
        } else
        {
            Map<String, List<String>> fields = request.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : fields.entrySet())
            {
                System.out.print(entry.getKey() + " : ");
                for (String val : entry.getValue())
                {
                    System.out.print(val + ", ");
                }
                System.out.println("");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = reader.readLine();
            while (line != null)
            {
                if (line.contains("oauth_token"))
                {
                    String keys[] = line.split("&");
                    for (String key : keys)
                    {
                        System.out.println(key);
                        if (key.startsWith(OAUTH_TOKEN + "="))
                        {
                            oauthToken = key.split("=")[1];
                        } else if (key.startsWith(OAUTH_TOKEN_SECRET + "="))
                        {
                            oauthTokenSecret = key.split("=")[1];
                        }
                    }
                }
                line = reader.readLine();
            }
        }
    }

    public String getOAuthToken()
    {
        return oauthToken;
    }

    public String getAuthorizeUrl()
    {
        StringBuilder s = new StringBuilder();
        s.append(authorizeUrl);
        s.append("?");
        s.append(OAUTH_TOKEN);
        s.append("=");
        s.append(oauthToken);
        s.append("&");
        s.append(OAUTH_TOKEN_SECRET);
        s.append("=");
        s.append(oauthTokenSecret);
        return s.toString();
    }

    public void addOauthToken(HttpURLConnection connection)
    {
        connection.setRequestProperty(OAUTH_TOKEN, oauthToken);
    }
}
