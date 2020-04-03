package com.zzz.haitouapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvCompany;
    SwipeRefreshLayout swipeRefreshLayout;
    CompanyRecyclerAdapter companyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvCompany = findViewById(R.id.rv_company);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        setUpRefreshLayout();
        setUpRecyclerView();
        //先打开刷新动画
        swipeRefreshLayout.setRefreshing(true);

/*
        //测试通知
        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtil.sendNotification(MainActivity.this,"一大波新鲜的职位来袭～");
            }
        });
*/

      //初始化webSocket服务
        //******注意先开启服务端
        setUpWebSocket();
    }

    //配置webSocket
    private void setUpWebSocket() {
        URI uri = URI.create("ws://10.147.20.56:8080/wsdemo");
        JWebSocketClient client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("JWebSClientService Received:", message);
                Gson gson = new Gson();
                final List<CompanyJobModel> list = gson.fromJson(message, new TypeToken<List<CompanyJobModel>>() {
                }.getType());
                System.out.print(list.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NotificationUtil.sendNotification(
                                MainActivity.this,
                                "一大批岗位正在等你来应聘哦~~"//主人，新鲜出炉的公司招聘信息！请签收
                        );
                        ((CompanyRecyclerAdapter) rvCompany.getAdapter()).replaceData(list);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        };
        try {
            client.connectBlocking();
            client.send("!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~~~~~~~");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //设置列表
    private void setUpRecyclerView() {
        List<String> careerList = new ArrayList<>();
        careerList.add("销售岗");
        careerList.add("服务岗");
        List<String> cityList = new ArrayList<>();
        cityList.add("成都");
        cityList.add("杭州");
        final CompanyJobModel companyJobModel = new CompanyJobModel(
                "1702404",
                "名业发展(福建)有限公司",
                "2020-03-28",
                "https://xyzp.haitou.cc/article/1702404.html",
                careerList,
                cityList);
        List<CompanyJobModel> list = new ArrayList<CompanyJobModel>();
        list.add(companyJobModel);
        list.add(companyJobModel);
        list.add(companyJobModel);
        list.add(companyJobModel);
        list.add(companyJobModel);

        companyRecyclerAdapter = new CompanyRecyclerAdapter(list, new Function<CompanyJobModel, Void>() {
            //点击事件
            @Override
            public Void apply(CompanyJobModel input) {
                startActivity(new Intent(MainActivity.this, WebActivity.class).putExtra("detailUrl", input.getDetailUrl()));
                return null;
            }
        });
        rvCompany.setAdapter(companyRecyclerAdapter);
    }

    //设置刷新监听
    private void setUpRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchCompanyList();
            }
        });
    }

    //客户端扒数据
    private void fetchCompanyList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<CompanyJobModel> companyJobModelList = new ArrayList<>();
                //一次性扒5页的数据
                for (int pageNum = 1; pageNum <= 5; pageNum++) {
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
                //将列表数据替换为扒取的数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CompanyRecyclerAdapter) rvCompany.getAdapter()).replaceData(companyJobModelList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();

    }


}
