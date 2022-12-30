package com.openai.openaimaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class OpenAiMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAiMavenApplication.class, args);
    }

}
