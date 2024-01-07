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

	@Mock
	ApplicationDao applicationDao;

	@InjectMocks
	ApplicationService applicationService;


	@BeforeEach
	public void setUp() {

		student.setFirstname("prajwal");
		student.setLastname("sable");
		student.setLastname("sable");
		student.setEmailAddress("ps@gmail.com");
		student.setStudentGrades(studentGrades);

	}



	private double mockRes=100.0;

	@Test
	@DisplayName("when and verify")
	public void assertEqualAddGrades(){

		double expected=100.0;

		when(
				applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())
		).thenReturn(
				mockRes
		);

		assertEquals(expected,applicationService.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));


		verify(applicationDao,times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
	}




}
