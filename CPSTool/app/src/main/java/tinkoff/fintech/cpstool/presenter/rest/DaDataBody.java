package tinkoff.fintech.cpstool.presenter.rest;

public class DaDataBody {
    private String query;
    private int count;

    public DaDataBody(String query, int count) {
        this.query = query;
        this.count = count;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}