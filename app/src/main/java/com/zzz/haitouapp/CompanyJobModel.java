package com.zzz.haitouapp;

import java.util.List;

public class CompanyJobModel {
    private String dataKey;
    private String title;
    private String time;
    private String detailUrl;

    public String getDataKey() {
        return dataKey;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public List<String> getCareerList() {
        return careerList;
    }

    public List<String> getCityList() {
        return cityList;
    }

    private List<String> careerList;
    private List<String> cityList;

    public CompanyJobModel(
            String dataKey,
            String title,
            String time,
            String detailUrl,
            List<String> careerList,
            List<String> cityList
    ) {
        this.dataKey = dataKey;
        this.title = title;
        this.time = time;
        this.detailUrl = detailUrl;
        this.careerList = careerList;
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "dataKey： " + dataKey + "\n" +
                "title： " + title + "\n" +
                "time： " + time + "\n" +
                "detailUrl： " + detailUrl + "\n" +
                "careerList： " + careerList + "\n" +
                "cityList： " + cityList + "\n";
    }
}
