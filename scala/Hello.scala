import java.io._
import java.nio.MappedByteBuffer
import java.nio.charset.Charset
import java.nio.channels.FileChannel

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.google.gson.JsonParser

import java.net.URL

import org.json.JSONArray
import org.json.JSONObject
import io.iron.ironworker.client.helpers.WorkerHelper

object Hello {

    /**
     * @param args the command line arguments
     */
    def main(args: Array[String]) {
        println("Running worker")
        val helper = WorkerHelper.fromArgs(args);
        val in = new BufferedReader(new InputStreamReader(System.in))
        val buff = new StringBuffer()

        // First way
        // Edit PayloadData.java file according to structure of your payload
        val payload = helper.getPayload(classOf[PayloadData]);
        val urlstr = s"http://httpbin.org/get?query=${payload.getQuery()}"
        println(urlstr)

        // Second way
        // For more info about JsonObject look at
        //     http://google-gson.googlecode.com/svn/tags/1.2.3/docs/javadocs/com/google/gson/JsonObject.html
        // PayloadData payload = helper.getPayloadJson();
        // urlstr += payload.get("query_string").getAsString();

        // Third way:
        // String rawPayload = helper.getPayload();
        // Parse, print or do anything you want with this string

        val url = new URL(urlstr);
        val br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))
        var continue = true
        while (continue) {
          val c = br.read()
          if(c == -1) {
            continue = false
          } else {
            buff.append(c.toChar)
          }
        }
        br.close()

        val js = new JSONObject(buff.toString())
        val wikiRes = js.getJSONObject("args")

        println(wikiRes.toString())
        writeFile("output.txt", wikiRes.toString());
    }

    private def writeFile(path: String, content: String) {
      val out = new OutputStreamWriter(new FileOutputStream(path))
      try {
          out.write(content);
      } finally {
          out.close();
      }
    }
}
