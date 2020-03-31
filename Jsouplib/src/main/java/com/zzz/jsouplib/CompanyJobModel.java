package com.zzz.jsouplib;

import java.util.List;

public class CompanyJobModel {
    private String dataKey;
    private String title;
    private String time;
    private String detailUrl;
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
