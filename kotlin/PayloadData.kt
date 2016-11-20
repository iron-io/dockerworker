import com.google.gson.annotations.SerializedName

class PayloadData {
    @SerializedName("query_string")
    var query: String = ""

    @SerializedName("name")
    var name: String = ""

}
