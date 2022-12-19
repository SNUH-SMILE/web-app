package kr.co.hconnect.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

public class HttpUtil {
    // 헤더
    private HttpHeaders headers;
    // 요청 바디 : <Key, Value> 쌍
    private MultiValueMap<String, String> body;
    // 타임아웃
    private HttpComponentsClientHttpRequestFactory factory;
    // 요청 URL
    private StringBuilder urlBuilder;
    private boolean queryStringToken;
    private String url;
    // 요청 방식
    private String method;


    public HttpUtil() {
        this.headers = new HttpHeaders();
        this.factory = new HttpComponentsClientHttpRequestFactory();
        this.factory.setConnectTimeout(5000);
        this.factory.setReadTimeout(5000);
        this.body = new LinkedMultiValueMap<String, String>();
        this.queryStringToken= true;
    }

    /**
     * content-type 설정 : new MediaType 설정 값
     *
     * @param type
     * @param subType
     * @param charSet
     * @return
     */
    public HttpUtil contentType(String type, String subType, String charSet){
        this.headers.setContentType(new MediaType(type, subType, Charset.forName(charSet)));
        return this;
    }

    /**
     * connect-timeout 설정<br>
     * default : 5초
     *
     * @param time
     * @return
     */
    public HttpUtil connectTimeout(int time){
        this.factory.setConnectTimeout(time);
        return this;
    }

    /**
     * read-timeout 설정<br>
     * default : 5초
     *
     * @param time
     * @return
     */
    public HttpUtil readTimeout(int time){
        this.factory.setReadTimeout(time);
        return this;
    }

    /**
     * 요청 URL 설정
     *
     * @param url
     * @return
     */
    public HttpUtil url(String url) {
        this.urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        return this;
    }

    /**
     * 쿼리스트링 설정
     *
     * @param name
     * @param value
     * @return
     */
    public HttpUtil queryString(String name, String value) {
        Assert.notNull(urlBuilder, "url 미입력");

        if(queryStringToken) {
            urlBuilder.append("?")
                .append(name)
                .append("=")
                .append(value);
            queryStringToken = false;
        } else {
            urlBuilder.append("&")
                .append(name)
                .append("=")
                .append(value);
        }

        return this;
    }

    /**
     * 요청 방식 설정(get, post)
     *
     * @param method
     * @return
     */
    public HttpUtil method(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    /**
     * 요청 헤더 설정
     *
     * @param name
     * @param value
     * @return
     */
    public HttpUtil header(String name, String value){
        headers.set(name, value);
        return this;
    }

    /**
     * body 요청 파라미터 설정 : key, value
     *
     * @param key
     * @param value
     * @return
     */
    public HttpUtil body(String key, String value){
        this.body.add(key, value);
        return this;
    }

    /**
     * body 요청 파라미터 설정 : map
     *
     * @param params
     * @return
     */
    public HttpUtil body(HashMap<String, Object> params){
        Iterator<String> itr = params.keySet().iterator();
        while(itr.hasNext()){
            String key = itr.next();
            body.add(key, (String)params.get(key));
        }
        return this;
    }

    /**
     * HTTP 요청 후 결과 반환(status, header, body)
     *
     * @return
     */
    public HashMap<String, Object> build(){
        HashMap<String, Object> result = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate(factory);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        url = urlBuilder.toString();

        ResponseEntity<String> response = null;
        if ("GET".equals(method)){
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } else if("POST".equals(method)) {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }

        result.put("status", response.getStatusCode());
        result.put("header", response.getHeaders());
        result.put("body", response.getBody());

        return result;
    }

}
