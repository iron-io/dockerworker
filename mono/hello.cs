using System;
using System.IO;
using System.Linq;
using System.Collections.Generic;
using System.Web.Script.Serialization;

public class HelloWorld
{
    static public void Main(string[] args)
    {
        string payloadFilePath = Environment.GetEnvironmentVariable("PAYLOAD_FILE");
        string payload = File.ReadAllText(payloadFilePath);
        JavaScriptSerializer serializer = new JavaScriptSerializer();
        IDictionary<string,string> json = serializer.Deserialize <Dictionary<string, string>>(payload);
        foreach (string key in json.Keys)
        {
            Console.WriteLine( key + " = " + json[key] );
        }
    }

}
