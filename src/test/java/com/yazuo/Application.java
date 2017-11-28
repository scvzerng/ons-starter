package com.yazuo;

import com.yazuo.intelligent.ons.annotation.EnableOns;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableOns
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }
}
