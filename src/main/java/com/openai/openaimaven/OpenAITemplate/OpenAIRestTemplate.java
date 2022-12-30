package com.openai.openaimaven.OpenAITemplate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;



@Slf4j
@NoArgsConstructor
@Service
@Getter
public class OpenAIRestTemplate {

    @Value("${open-ai-token}")
    private String openAIToken;

    @Value("${base-url}")
    private String baseURL;

    private String url ;

    private HttpHeaders headers;

    private Object body;

    private HttpMethod httpMethod;

    public void setUrl(String url) {
        this.url = baseURL+url;
    }
    public void setHeaders(HttpHeaders headers) { this.headers = headers; }
    public void setBody(Object body)
    {
        this.body = body;
    }
    public void setHttpMethod(HttpMethod httpMethod)
    {
        this.httpMethod = httpMethod;
    }

    public ResponseEntity getOpenAIRestTemplate() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(this.url);
        if(this.headers==null)
        {
            this.headers = new HttpHeaders();
        }
        this.headers.set("Authorization","Bearer "+openAIToken);
        HttpEntity httpEntity = new HttpEntity(this.body,this.headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, this.httpMethod,httpEntity,String.class);
        return responseEntity;
    }

    @SneakyThrows
    public String makeRequest(String url,Object body,HttpHeaders headers,HttpMethod httpMethod)
    {
        this.url=baseURL+url;
        this.body=body;
        this.httpMethod=httpMethod;
        this.headers=headers;
        try {
            ResponseEntity<String> responseEntity = getOpenAIRestTemplate();
            return responseEntity.getBody();
        }catch (URISyntaxException e)
        {
            throw new URISyntaxException("404","Unable To Process Request Right Now");
        }
    }
}
