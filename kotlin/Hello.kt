import java.io.*
import java.nio.MappedByteBuffer
import java.nio.charset.Charset
import java.nio.channels.FileChannel

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.google.gson.JsonParser

import java.net.URL
import java.net.URLConnection

import org.json.JSONArray
import org.json.JSONObject
import io.iron.ironworker.client.helpers.WorkerHelper

object Hello {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("Running worker")

        val helper = WorkerHelper.fromArgs(arrayOfNulls<String>(0))
        val `in` = BufferedReader(
                InputStreamReader(System.`in`))
        val buff = StringBuffer()

        // Get payload data with the IronWorker helper
        val payload = helper.getPayload(PayloadData::class.java)

        println("Hello " + payload.name)
    }

    @Throws(IOException::class)
    private fun writeFile(path: String, content: String) {
        val out = OutputStreamWriter(FileOutputStream(path))
        try {
            out.write(content)
        } finally {
            out.close()
        }

    }
}
