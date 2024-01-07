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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockitoDemoAppApplication.class)
class MockitoDemoAppApplicationTests {



	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@Autowired
	ApplicationContext context;

	//@Mock
	@MockBean
	ApplicationDao applicationDao;

//	@InjectMocks
	@Autowired
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

	@Test
	@DisplayName("find gpa")
	public void assertFindGpa() {

		when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.0);

		assertEquals(88.0,applicationService.findGradePointAverage(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("not null")
	public void assertNotNUll() {

		when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

		assertEquals(true,applicationService.checkNull(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("throw excpetion")
	public void assertException() {

		CollegeStudent student1=context.getBean(CollegeStudent.class);
		when(applicationDao.checkNull(student1)).thenThrow(new RuntimeException());

		assertThrows(RuntimeException.class,()->{applicationService.checkNull(student1);});

		verify(applicationDao,times(1)).checkNull(student1);
	}

	@Test
	@DisplayName("multipl calls")
	public void assertMc() {

		CollegeStudent student1=context.getBean(CollegeStudent.class);
		when(applicationDao.checkNull(student1)).thenThrow(new RuntimeException()).thenReturn("hello world");

		assertThrows(RuntimeException.class,()->{applicationService.checkNull(student1);});

		assertEquals("hello world",applicationService.checkNull(student1));

		verify(applicationDao,times(2)).checkNull(student1);


	}
}
