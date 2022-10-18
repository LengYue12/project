package commons;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Map;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/10 16:55
 * @Description HttpClient的封装工具类
 */
public class HttpClientUtil {

    public static String doGet(String url) {
        return doGet(url,null);
    }


    /**
     * get请求，支持request请求方式，不支持RestFull方式
     * @param url   请求地址
     * @param params    参数
     * @return      响应字符串
     */
    public static String doGet(String url, Map<String,String> params) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        // 创建URL
        try {
            URIBuilder builder = new URIBuilder(url);
            if(params != null){
                // 在URL后面拼接请求参数
                for(String key : params.keySet()){
                    builder.addParameter(key,params.get(key));
                }
            }
            URI uri = builder.build();

            // 创建HttpGet请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 从响应对象中获取状态码（成功或失败的状态）
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("响应的状态=" + statusCode);
            // 200表示响应成功
            if (statusCode == 200) {
                // 响应的内容字符串
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 释放资源
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return resultString;
    }
}
