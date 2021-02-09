package me.portfolio.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;


@EnableAutoConfiguration
@EnableJpaAuditing
@CrossOrigin(origins = "http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com, http://localhost:8000, http://localhost:3000")
@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}
