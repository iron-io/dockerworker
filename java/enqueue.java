import io.iron.ironworker.client.Client;
import io.iron.ironworker.client.entities.TaskEntity;
import io.iron.ironworker.client.builders.Params;
import io.iron.ironworker.client.builders.TaskOptions;
import io.iron.ironworker.client.APIException;

public class Enqueue {
        public static void main(String[] args) throws APIException{
                Client client = new Client("INSERT TOKEN HERE", "INSERT PROJECT ID HRE");
                TaskEntity t = client.createTask("JavaWorker101", Params.add("query", "iron"));
                System.out.println(t.getId());
        }
}