package de.qytera.testrail.utils;

import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.openqa.selenium.InvalidArgumentException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
public class APIClient
{
    private String m_user;
    private String m_password;
    private String m_url;

    public APIClient(String base_url)
    {
        if (base_url == null) {
            throw new InvalidArgumentException("Testrail Base URL is null, please set the value in your configuration file");
        }
        if (!base_url.endsWith("/"))
        {
            base_url += "/";
        }

        this.m_url = base_url + "index.php?/api/v2/";
    }

    /**
     * Issues a GET request (write) against the API and returns the result (as Object, see below).
     * @param uri       The API method to call including parameters
     * @param data      The data to submit as part of the request (e.g., a map) If adding an attachment, must be the path to the file
     * @return Returns the parsed JSON response as standard object which can either be an instance of JSONObject or JSONArray
     * (depending on the API method). In most cases, this returns a JSONObject instance which is basically the same as java.util.Map.
     * @throws IOException
     * @throws APIException
     */
    public Object sendGet(String uri, String data)
            throws IOException, APIException
    {
        return this.sendRequest("GET", uri, data);
    }

    public Object sendGet(String uri)
            throws IOException, APIException
    {
        return this.sendRequest("GET", uri, null);
    }

    /**
     * Issues a POST request (write) against the API and returns the result (as Object, see below).
     * @param uri       The API method to call including parameters
     * @param data      The data to submit as part of the request (e.g., a map) If adding an attachment, must be the path to the file
     * @return Returns the parsed JSON response as standard object which can either be an instance of JSONObject or JSONArray
     * (depending on the API method). In most cases, this returns a JSONObject instance which is basically the same as java.util.Map.
     * @throws IOException
     * @throws APIException
     */
    public Object sendPost(String uri, Object data)
            throws IOException, APIException
    {
        return this.sendRequest("POST", uri, data);
    }

    private Object sendRequest(String method, String uri, Object data)
            throws IOException, APIException
    {
        URL url = new URL(this.m_url + uri);
        // Create the connection object and set the required HTTP method
        // (GET/POST) and headers (content type and basic auth).
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        addAuthorizationHeader(conn);

        if (method.equals("POST"))
        {
            sendPostRequest(uri, data, conn);
        }
        else	// GET request
        {
            sendGetRequest(conn);
        }

        // Execute the actual web request (if it wasn't already initiated
        // by getOutputStream above) and record any occurred errors (we use
        // the error stream in this case).
        int status = conn.getResponseCode();

        InputStream stream;
        if (status != 200)
        {
            stream = conn.getErrorStream();
            if (stream == null)
            {
                throw new APIException(
                        "TestRail API return HTTP " + status +
                                " (No additional error message received)"
                );
            }
        }
        else
        {
            stream = conn.getInputStream();
        }

        // If 'get_attachment' (not 'get_attachments') returned valid status code, save the file
        if ((stream != null)
                && (uri.startsWith("get_attachment/")))
        {
            assert data != null;
            try (FileOutputStream outputStream = new FileOutputStream((String) data)) {
                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                while ((bytesRead = stream.read(buffer)) > 0)
                {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            stream.close();
            return data;
        }

        // Not an attachment received
        // Read the response body, if any, and deserialize it from JSON.
        String text = "";
        if (stream != null)
        {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            stream,
                            StandardCharsets.UTF_8
                    )
            );

            String line;
            while ((line = reader.readLine()) != null)
            {
                text += line;
                text += System.getProperty("line.separator");
            }

            reader.close();
        }

        Object result;
        if (!text.equals(""))
        {
            result = JSONValue.parse(text);
        }
        else
        {
            result = new JSONObject();
        }

        // Check for any occurred errors and add additional details to
        // the exception message, if any (e.g. the error message returned
        // by TestRail).
        if (status != 200)
        {
            handleStatusNot200(status, result);
        }

        return result;
    }

    private static void handleStatusNot200(int status, Object result) throws APIException {
        String error = "No additional error message received";
        if (result != null && result instanceof JSONObject)
        {
            JSONObject obj = (JSONObject) result;
            if (obj.containsKey("error"))
            {
                error = '"' + (String) obj.get("error") + '"';
            }
        }

        throw new APIException(
                "TestRail API returned HTTP " + status +
                        "(" + error + ")"
        );
    }

    private void addAuthorizationHeader(HttpURLConnection conn) {
        String auth = getAuthorization(this.m_user, this.m_password);
        conn.addRequestProperty("Authorization", "Basic " + auth);
    }

    private static void sendGetRequest(HttpURLConnection conn) {
        conn.addRequestProperty("Content-Type", "application/json");
    }

    private static void sendPostRequest(String uri, Object data, HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("POST");
        // Add the POST arguments, if any. We just serialize the passed
        // data object (i.e. a dictionary) and then add it to the
        // request body.
        if (data != null)
        {
            if (uri.startsWith("add_attachment"))   // add_attachment API requests
            {
                sendAttachment((String) data, conn);
            }
            else	// Not an attachment
            {
                sendJson(data, conn);
            }
        }
    }

    private static void sendAttachment(String data, HttpURLConnection conn) throws IOException {
        String boundary = "TestRailAPIAttachmentBoundary"; //Can be any random string
        File uploadFile = new File(data);

        conn.setDoOutput(true);
        conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream ostreamBody = conn.getOutputStream()) {
            try (BufferedWriter bodyWriter = new BufferedWriter(new OutputStreamWriter(ostreamBody))) {
                bodyWriter.write("\n\n--" + boundary + "\r\n");
                bodyWriter.write("Content-Disposition: form-data; name=\"attachment\"; filename=\""
                        + uploadFile.getName() + "\"");
                bodyWriter.write("\r\n\r\n");
                bodyWriter.flush();

                //Read file into request
                try (InputStream istreamFile = new FileInputStream(uploadFile)) {
                    int bytesRead;
                    byte[] dataBuffer = new byte[1024];
                    while ((bytesRead = istreamFile.read(dataBuffer)) != -1) {
                        ostreamBody.write(dataBuffer, 0, bytesRead);
                    }

                    ostreamBody.flush();

                    //end of attachment, add boundary
                    bodyWriter.write("\r\n--" + boundary + "--\r\n");
                }

            }
        }
    }

    private static void sendJson(Object data, HttpURLConnection conn) throws IOException {
        sendGetRequest(conn);
        byte[] block = JSONValue.toJSONString(data).
                getBytes("UTF-8");

        conn.setDoOutput(true);
        OutputStream ostream = conn.getOutputStream();
        ostream.write(block);
        ostream.close();
    }

    private static String getAuthorization(String user, String password)
    {
        try
        {
            return new String(Base64.getEncoder().encode((user + ":" + password).getBytes()));
        }
        catch (IllegalArgumentException e)
        {
            // Not thrown
            // Not thrown
        }

        return "";
    }
}

