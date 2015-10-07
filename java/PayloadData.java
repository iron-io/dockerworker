import com.google.gson.annotations.SerializedName;

class PayloadData {
    @SerializedName("query_string")
    String query;

    @SerializedName("name")
    String name;

    public PayloadData() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
