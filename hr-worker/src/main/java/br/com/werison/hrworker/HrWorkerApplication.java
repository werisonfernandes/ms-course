package br.com.werison.hrworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HrWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrWorkerApplication.class, args);
		
		int result = (int) (1 ^ (1 >>> 32));
		System.out.print(result);
	}

}
