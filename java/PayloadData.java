import com.google.gson.annotations.SerializedName;

class PayloadData {
    @SerializedName("query_string")
    String query;

    public PayloadData() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
