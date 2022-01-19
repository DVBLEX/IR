package ie.irishrail.server.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "ie.irishrail.server")
public class FpnRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpnRestApplication.class, args);
    }
}
