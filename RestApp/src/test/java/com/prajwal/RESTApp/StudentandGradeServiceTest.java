package com.prajwal.RESTApp;

import com.prajwal.RESTApp.models.*;
import com.prajwal.RESTApp.repository.HistoryGradesDao;
import com.prajwal.RESTApp.repository.MathGradesDao;
import com.prajwal.RESTApp.repository.ScienceGradesDao;
import com.prajwal.RESTApp.repository.StudentDao;
import com.prajwal.RESTApp.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
@TestPropertySource("/application-test.properties")
@SpringBootTest
public class StudentandGradeServiceTest {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MathGradesDao mathGradesDao;

    @Autowired
    ScienceGradesDao scienceGradesDao;

    @Autowired
    HistoryGradesDao historyGradesDao;


    // sql script from application.properties
    @Value("${sql.scripts.create.student}")
    private String sqlCreateStudent;
    @Value("${sql.scripts.delete.student}")
    private String sqlDeleteStudent;
    @Value("${sql.scripts.create.math.grade}")
    private String sqlCreateMath;
    @Value("${sql.scripts.create.science.grade}")
    private String sqlCreateScience;
    @Value("${sql.scripts.create.history.grade}")
    private String sqlCreateHistory;
    @Value("${sql.scripts.delete.math.grade}")
    private String sqlDelMath;
    @Value("${sql.scripts.delete.science.grade}")
    private String sqlDelScience;
    @Value("${sql.scripts.delete.history.grade}")
    private String sqlDelHistory;






    @BeforeEach()
    void setUp() {
        //creates with id =


//        jdbcTemplate.execute("insert into student(id,first_name,last_name,email_address)" +
//                "values( 1,'prajwal','sable','prajsa99@gmail.com')"
//                );

        jdbcTemplate.execute(sqlCreateStudent);


//        jdbcTemplate.execute("insert into math_grade (student_id,grade) values (1,100.0) ");
//        jdbcTemplate.execute("insert into science_grade (student_id,grade) values (1,100.0) ");
//        jdbcTemplate.execute("insert into history_grade (student_id,grade) values (1,100.0) ");


        jdbcTemplate.execute(sqlCreateMath);
        jdbcTemplate.execute(sqlCreateScience);
        jdbcTemplate.execute(sqlCreateHistory);
    }


    @AfterEach
    void tearDown() {
//
//        jdbcTemplate.execute("DELETE FROM student");
//        jdbcTemplate.execute("DELETE FROM math_grade");
//        jdbcTemplate.execute("DELETE FROM science_grade");
//        jdbcTemplate.execute("DELETE FROM history_grade");


        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDelMath);
        jdbcTemplate.execute(sqlDelScience);
        jdbcTemplate.execute(sqlDelHistory);

    }

    @Test
    public void createStudentService(){

        studentService.createStudent("pssarajwal","sadaable","a99@gmail.com");

        CollegeStudent student=studentDao.findByEmailAddress("a99@gmail.com");

        System.out.println(student.toString());

        assertEquals("a99@gmail.com",student.getEmailAddress(),"find by email");


    }

    @Test
    public void isStudentNull(){

        assertTrue(studentService.checkIfStudentIsNul(1));

        assertFalse(studentService.checkIfStudentIsNul(0));
    }


    @Test
    public void deleteStudentService(){

        Optional<CollegeStudent> student=studentDao.findById(1);
        assertTrue(student.isPresent(),"should be true");




        studentService.deleteStudentById(1);

        student=studentDao.findById(1);

        assertFalse(student.isPresent(),"should be false");


        // want to delete grades as well
    }

//    @Sql("/insertData.sql")
//    @Test
//    public void getGradeBookService(){
//
//        Iterable<CollegeStudent> studentIterable=studentService.getGradebook();
//        List<CollegeStudent> collegeStudentList=new ArrayList<>();
//        for (CollegeStudent c:studentIterable){
//            collegeStudentList.add(c);
//        }
//
//        assertEquals(5,collegeStudentList.size());
//
//    }


    /////-----------------------------Grades come int picture---------------------///

    @Test
    public void createGradeService(){

        assertTrue(studentService.checkIfStudentIsNul(1));

        // Create the grade
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        assertTrue(mathGrades.iterator().hasNext(), "Student has math grades");
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size());


        assertTrue(studentService.createGrade(81.50, 1, "science"));
        Iterable<ScienceGrade> scienceGrades= scienceGradesDao.findGradeByStudentId(1);
        assertTrue(scienceGrades.iterator().hasNext(), "Student has science grades");
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size());

        assertTrue(studentService.createGrade(82.50, 1, "history"));
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);
        assertTrue(historyGrades.iterator().hasNext(), "Student has history grades");
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size());
    }

    @Test
    public void createGradeServiceFalse() {

        assertFalse(studentService.createGrade(109, 1, "math"));
        assertFalse(studentService.createGrade(-9, 1, "math"));
        assertFalse(studentService.createGrade(67, 3, "math"));
        assertFalse(studentService.createGrade(70, 1, "chemistry"));

    }

    @Test
    public void deleteGradeService(){

            assertEquals(1,studentService.deleteGrade(1,"math"),"Returns 1 deleted grades stud id");
            assertEquals(1,studentService.deleteGrade(1,"science"),"Returns 1 deleted grades stud id");
            assertEquals(1,studentService.deleteGrade(1,"history"),"Returns 1 deleted grades stud id");
    }

    @Test
    public void deleteGradeServiceFalse(){

        assertEquals(0,studentService.deleteGrade(0,"math"),"R");
        assertEquals(0,studentService.deleteGrade(0,"science"),"R");
        assertEquals(0,studentService.deleteGrade(0,"history"),"R");

        assertEquals(0,studentService.deleteGrade(1,"geo"),"R");


    }

    @Test
    public void deleteStudentAndGradesService(){

        Optional<CollegeStudent> student=studentDao.findById(1);
        Optional<MathGrade> mathGrade=mathGradesDao.findById(1);
        Optional<ScienceGrade> scienceGrade=scienceGradesDao.findById(1);
        Optional<HistoryGrade> historyGrade=historyGradesDao.findById(1);

        assertTrue(student.isPresent(),"should be true");
        assertTrue(mathGrade.isPresent());
        assertTrue(scienceGrade.isPresent());
        assertTrue(mathGrade.isPresent());

        studentService.deleteStudentById(1);

        student=studentDao.findById(1);
        mathGrade=mathGradesDao.findById(1);
        scienceGrade=scienceGradesDao.findById(1);
        historyGrade=historyGradesDao.findById(1);


        assertFalse(student.isPresent(),"should be false");
        assertFalse(mathGrade.isPresent());
        assertFalse(scienceGrade.isPresent());
        assertFalse(mathGrade.isPresent());


        // want to delete grades as well
    }


    @Test
    public void studentInformation(){

//        'prajwal','sable','prajsa99@gmail.com'
        GradebookCollegeStudent gradebookCollegeStudent=studentService.studentInformation(1);

        assertNotNull(gradebookCollegeStudent);
        assertEquals(1,gradebookCollegeStudent.getId());
        assertEquals("prajwal",gradebookCollegeStudent.getFirstName());
        assertEquals("sable",gradebookCollegeStudent.getLastName());
        assertEquals("prajsa99@gmail.com",gradebookCollegeStudent.getEmailAddress());

        assertEquals(1, gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size());
        assertEquals(1,gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size());
        assertEquals(1,gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size());

    }

    @Test
    public void studentInformationFalse(){

        GradebookCollegeStudent gradebookCollegeStudent=studentService.studentInformation(0);
        assertNull(gradebookCollegeStudent);


    }
}
