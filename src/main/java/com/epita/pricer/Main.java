package com.epita.pricer;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
 
 
//@Import(JpaConfiguration.class)
@ImportResource("classpath:application-context.xml")
@SpringBootApplication(scanBasePackages={"com.epita.pricer"})
public class Main {
 
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}