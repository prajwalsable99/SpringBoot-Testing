package com.prajwal.MockitoDemoApp;

import com.prajwal.MockitoDemoApp.dao.ApplicationDao;
import com.prajwal.MockitoDemoApp.models.CollegeStudent;
import com.prajwal.MockitoDemoApp.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class MockitoDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockitoDemoAppApplication.class, args);


	}

	/* New for Section 2.2 */
	@Bean(name = "applicationExample")
	ApplicationService getApplicationService() {
		return new ApplicationService();
	}

	/* New for Section 2.2 */
	@Bean(name = "applicationDao")
	ApplicationDao getApplicationDao() {
		return new ApplicationDao();
	}

	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}

}
