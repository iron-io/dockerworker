import com.google.gson.annotations.SerializedName

class PayloadData {
  @SerializedName(value = "query_string")
  var query = ""

  def PayloadData() {}

  def getQuery(): String = this.query
  def setQuery(query: String) {
    this.query = query
  }
}
