package com.prajwal.springtestdemo;


import com.prajwal.springtestdemo.component.models.CollegeStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringtestdemoApplication {

	public static void main(String[] args) {
	 	SpringApplication.run(SpringtestdemoApplication.class, args);
	}

	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}

}
