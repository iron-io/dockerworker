import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.channels.FileChannel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class Worker101 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        System.out.println("Running worker");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String urlstr = "http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&rvsection=0&format=json&titles=";
        StringBuffer buff = new StringBuffer();

        urlstr += parse_payload(args);

        URL url = new URL(urlstr);
        BufferedReader br = new BufferedReader(
                                    new InputStreamReader(
                                    url.openConnection().getInputStream()));
        int c;
        while((c=br.read())!=-1)
        {
            buff.append((char)c);
        }
        br.close();

        JSONObject js = new JSONObject(buff.toString());
        JSONObject wikiRes = js.getJSONObject("query").getJSONObject("pages");

        System.out.println(wikiRes.toString()+"\n");
        writeFile("output.txt", wikiRes.toString());
    }

        private static String parse_payload(String[] args) {
            //obtain the filename from the passed arguments
            int payloadPos = -1;
            for(int i=0; i < args.length; i++) {
                    if(args[i].equals("-payload")) {
                            payloadPos = i + 1;
                            break;
                    }
            }
            if(payloadPos >= args.length) {
                    System.err.println("Invalid payload argument.");
                    System.exit(1);
            }
            if(payloadPos == -1) {
                    System.err.println("No payload argument.");
                    System.exit(1);
            }

            //read the contents of the file to a string
            String payload = "";

            try {
                    payload = readFile(args[payloadPos]);
            } catch (IOException e) {
                    System.err.println("IOException");
                    System.exit(1);
            }
            String query = "iron.io";
            try {
                //parse the string as JSON
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject passed_args = parser.parse(payload).getAsJsonObject();
                query =  gson.fromJson(passed_args.get("query"), String.class);
                System.out.println("Query from payload: " + query);
             } catch (IllegalStateException e) {
               System.err.println("Payload is empty");
             }
             return query;
        }

        private static String readFile(String path) throws IOException {
            FileInputStream stream = new FileInputStream(new File(path));
            try {
                    FileChannel chan = stream.getChannel();
                    MappedByteBuffer buf = chan.map(FileChannel.MapMode.READ_ONLY, 0, chan.size());
                    return Charset.defaultCharset().decode(buf).toString();
            }
            finally {
                    stream.close();
            }
        }

        private static void writeFile(String path,String content) throws IOException {
                    Writer out = new OutputStreamWriter(new FileOutputStream(path));
                    try {
                      out.write(content);
                    }
                    finally {
                      out.close();
                    }

                }
}