package com.prajwal.MockitoDemoApp;

import com.prajwal.MockitoDemoApp.dao.ApplicationDao;
import com.prajwal.MockitoDemoApp.models.CollegeStudent;
import com.prajwal.MockitoDemoApp.models.StudentGrades;
import com.prajwal.MockitoDemoApp.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockitoDemoAppApplication.class)
class MockitoDemoAppApplicationTests {



	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@Autowired
	ApplicationContext context;

//	@Autowired
//	ApplicationDao applicationDao;
//
//	@InjectMocks
//	ApplicationService applicationService;


	@BeforeEach
	public void setUp() {

		student.setFirstname("prajwal");
		student.setLastname("sable");
		student.setLastname("sable");
		student.setEmailAddress("ps@gmail.com");
		student.setStudentGrades(studentGrades);

//		ReflectionTestUtils.setField(student,"id",11);

		ReflectionTestUtils.invokeMethod(student,"setId",11)-;
//		System.out.println(ReflectionTestUtils.getField(student,"id"));


	}




	@Test
	@DisplayName("ref")
	public void assertEqualAddGrades(){
		System.out.println(student.getId());
		assertEquals(11,student.getId(),"should be equal");

	}




}
