package br.com.digitalparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class DigitalParkingApplication {

  public static void main(String[] args) {
    SpringApplication.run(DigitalParkingApplication.class, args);
  }

}
