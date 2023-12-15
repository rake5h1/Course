package pojo;

import java.util.List;

public class PostrequestBody {
    private String name;
    private String job;
    private List<String> languages;
    private List<DataPojo> data;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setJob(String j) {
        this.job = j;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> l) {
        this.languages = l;
    }

    public List<DataPojo> getData() {
        return data;
    }

    public void setData(List<DataPojo> d) {
        this.data = d;
    }

}
