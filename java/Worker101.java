import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.channels.FileChannel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import io.iron.ironworker.client.helpers.WorkerHelper;

public class Worker101 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Running worker");
        WorkerHelper helper = WorkerHelper.fromArgs(new String[0]);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        StringBuffer buff = new StringBuffer();

        // First way
        // Edit PayloadData.java file according to structure of your payload
        PayloadData payload = helper.getPayload(PayloadData.class);
        String urlstr = String.format("http://httpbin.org/get?query=%s", payload.getQuery());
        System.out.println(urlstr);

        // Second way
        // For more info about JsonObject look at
        //     http://google-gson.googlecode.com/svn/tags/1.2.3/docs/javadocs/com/google/gson/JsonObject.html
        // PayloadData payload = helper.getPayloadJson();
        // urlstr += payload.get("query_string").getAsString();

        // Third way:
        // String rawPayload = helper.getPayload();
        // Parse, print or do anything you want with this string

        URL url = new URL(urlstr);
        URLConnection urlConn = url.openConnection();
        InputStream response = urlConn.getInputStream();
        InputStreamReader isr = new InputStreamReader(response);
        BufferedReader br = new BufferedReader(isr);
        int c;
        while ((c = br.read()) != -1) {
          char cc = (char) c;
          buff.append(cc);
        }
        br.close();

        JSONObject js = new JSONObject(buff.toString());
        JSONObject res = js.getJSONObject("args");

        System.out.println(res.toString());
        writeFile("output.txt", res.toString());
    }

    private static void writeFile(String path, String content) throws IOException {
        Writer out = new OutputStreamWriter(new FileOutputStream(path));
        try {
            out.write(content);
        } finally {
            out.close();
        }

    }
}
