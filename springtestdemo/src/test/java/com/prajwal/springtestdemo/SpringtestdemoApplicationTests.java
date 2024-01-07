package com.prajwal.springtestdemo;

import com.prajwal.springtestdemo.component.models.CollegeStudent;
import com.prajwal.springtestdemo.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringtestdemoApplication.class)
class SpringtestdemoApplicationTests {

	@Value("${info.app.name}")
 	private String appName;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	CollegeStudent collegeStudent;

	@Autowired
	ApplicationContext applicationContext;

	@BeforeEach
	void setUp() {
		System.out.println("[testing " + appName + " of school " +schoolName);

		collegeStudent.setFirstname("prajwal");
		collegeStudent.setLastname("sable");
		collegeStudent.setEmailAddress("ps@gmail.com");
		;
		studentGrades.setMathGradeResults( new ArrayList<>(Arrays.asList(1.0,2.0,3.0)));

		collegeStudent.setStudentGrades(studentGrades);

	}

	@Autowired
	StudentGrades studentGrades;



	@Test
	void contextLoads() {
	}

	@Test
	void  simpleTest(){


		assertEquals(6,studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults()),"message pf test");
	}

	@Test
	void  simpleTest2(){

		CollegeStudent student=applicationContext.getBean(CollegeStudent.class);
		StudentGrades Grades =applicationContext.getBean(StudentGrades.class);
		student.setFirstname("prajwal");
		student.setLastname("sable");
		student.setEmailAddress("ps@gmail.com");
		;
		Grades.setMathGradeResults( new ArrayList<>(Arrays.asList(1.0,2.0,3.0)));

		student.setStudentGrades(Grades);




		assertEquals(6,Grades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()),"message pf test");
	}

}
