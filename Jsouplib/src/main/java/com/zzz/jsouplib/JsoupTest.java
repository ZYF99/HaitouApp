package com.zzz.jsouplib;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JsoupTest {
    public static void main(String[] args) {
        add();
        /*
        List<CompanyJobModel> companyJobModelList = new ArrayList<>();
        //一次性扒20页的数据
        for (int pageNum = 1; pageNum <= 20; pageNum++) {
            try {
                Document doc = Jsoup.connect("https://xyzp.haitou.cc/page-" + pageNum)
                        .data("query", "Java")
                        .userAgent("Mozilla")
                        .get();
                Element bodyElement = doc.getElementsByTag("body").get(0);
                Element scopeElement = bodyElement.getElementsByTag("div").get(0);
                Element pageWrapper = scopeElement.getElementById("page-wrapper");
                Element pageMain = pageWrapper.getElementsByClass("page-main").get(0);
                Element pagePanel = pageMain.getElementsByClass("panel panel-default").get(0);
                Element pagePanel2 = pagePanel.getElementsByClass("panel-body remove padding top bottom").get(0);
                Element gridView = pagePanel2.getElementById("w0");
                Element table = gridView.getElementsByClass("table cxxt-table").get(0);
                Element tbody = table.getElementsByTag("tbody").get(0);
                Elements datas = tbody.getElementsByTag("tr");

                for (Element element : datas
                ) {
                    //该公司的data-key
                    String dataKey = element.attr("data-key");
                    String title = element
                            .getElementsByClass("cxxt-title").get(0)
                            .getElementsByTag("a").get(0)
                            .getElementsByTag("div").get(0)
                            .text();
                    //发布时间
                    String time = element.getElementsByClass("cxxt-time").text();
                    String detailUrl = "https://xyzp.haitou.cc/article/" + dataKey + ".html";

                    //职位列表
                    final List<String> careerList = new ArrayList<>();

                    //职位的a标签的列表
                    Elements careerTagList = element.getElementsByClass("text-ellipsis cxxt-position")
                            .get(0)
                            .getElementsByTag("a");

                    //遍历职位的a标签的列表
                    careerTagList.forEach(new Consumer<Element>() {
                        @Override
                        public void accept(Element element) {
                            String career = element.getElementsByTag("span").get(0).text();
                            careerList.add(career);
                        }
                    });

                    //涉及城市列表
                    final List<String> cityList = new ArrayList<>();
                    //涉及城市的span标签的列表
                    Element citySpanTag = element.getElementsByClass("text-ellipsis")
                            .get(1)
                            .getElementsByTag("span")
                            .get(0);
                    if (citySpanTag.text().equals("不限")) {//当城市的span tag值为不限
                        cityList.add("不限");
                    } else {
                        //城市的span tag 没有值 继续遍历它的子tag
                        //涉及城市的a标签的列表
                        Elements citySpanElements = citySpanTag.getElementsByTag("span");
                        for (int i = 1; i <= citySpanElements.size() - 1; i++) {
                            //排除掉第一项 只要子tag
                            String city = citySpanElements.get(i).text();
                            cityList.add(city);
                        }
                    }

                    //将扒取的信息构建成model
                    CompanyJobModel companyJobModel = new CompanyJobModel(
                            dataKey,
                            title,
                            time,
                            detailUrl,
                            careerList,
                            cityList
                    );
                    //将model添加到扒取列表
                    companyJobModelList.add(companyJobModel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //查看扒到的所有信息
        System.out.print("list" + companyJobModelList);
        */

    }

    static void add(){
        Double a = Double.valueOf(1.23)-Double.valueOf(1.25);
        System.out.println(a);;

    }

}
