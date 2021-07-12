package httpTransfer;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HttpTransfer {

    private static final Logger logger = LoggerFactory.getLogger(HttpTransfer.class);

    /**
     * 选配代理form-data类型
     * @param url   请求地址
     * @param jsonParam 参数
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return
     */
    public static String formHttpRequest(String url,String jsonParam,String proxyHost,String proxyPort){
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;
        logger.info("=============:\t" + jsonParam);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create("multipart/form-data", Consts.UTF_8);
        builder.addTextBody("params", jsonParam, contentType);

        HttpEntity multipart = builder.build();
        HttpResponse resp = null;
        CloseableHttpClient client2 = null;
        if(!StringUtils.isEmpty(proxyHost) && !StringUtils.isEmpty(proxyPort)){
            HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort),"http");
            RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
            client2 = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

        }else {
            RequestConfig defaultRequestConfig = RequestConfig.custom().build();
            client2 = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        }
        try {
            httpPost.setEntity(multipart);
            resp = client2.execute(httpPost);

            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "UTF-8");
            logger.info("=========================:\t" + respContent);
            logger.info("=========================:\t" + resp.getStatusLine().getStatusCode());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return respContent;

    }

    /**
     * 选配代理raw方式请求获取接口内容
     * @param url   地址
     * @param voJson    参数
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return
     */
    public static String postJsonByRaw(String url,String voJson,String proxyHost,String proxyPort ){
        CloseableHttpClient client = null;
        RequestConfig defaultRequestConfig =null;
        if(!StringUtils.isEmpty(proxyHost) && !StringUtils.isEmpty(proxyPort)){
            HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort),"http");
            defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
        }else {
            defaultRequestConfig = RequestConfig.custom().build();
        }
        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        String content="";
        try {
            HttpPost post = new HttpPost(url);
            // json传递
            StringEntity postingString = new StringEntity(voJson,"UTF-8");
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json;charset=UTF-8");
            HttpResponse response = client.execute(post);
            content = EntityUtils.toString(response.getEntity());
            logger.info("======"+content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
