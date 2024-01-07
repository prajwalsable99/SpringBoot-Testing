package com.prajwal.MVCApp;


import com.prajwal.MVCApp.models.CollegeStudent;
import com.prajwal.MVCApp.models.GradebookCollegeStudent;
import com.prajwal.MVCApp.models.MathGrade;
import com.prajwal.MVCApp.repository.MathGradesDao;
import com.prajwal.MVCApp.repository.StudentDao;
import com.prajwal.MVCApp.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    private  static MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StudentDao studentDao;

    @Autowired
    private MockMvc mockMvc;
    @Mock
    StudentAndGradeService mockStudentService;

    @Autowired
    StudentAndGradeService realstudentService;

    @BeforeAll
    public static void doSome(){

        mockHttpServletRequest=new MockHttpServletRequest();
        mockHttpServletRequest.setParameter("firstname","abc");
        mockHttpServletRequest.setParameter("lastname","xyz");
        mockHttpServletRequest.setParameter("emailAddress","found@gmail.com");
    }

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


    @BeforeEach
    void setUp() {

        //id :1
        jdbcTemplate.execute("insert into student(first_name,last_name,email_address)" +
                "values('prajwal','sable','prajsa99@gmail.com')"
        );
//        jdbcTemplate.execute(sqlCreateStudent);

        jdbcTemplate.execute(sqlCreateMath);
        jdbcTemplate.execute(sqlCreateScience);
        jdbcTemplate.execute(sqlCreateHistory);

    }

    @AfterEach
    void tearDown() {

//        jdbcTemplate.execute("DELETE FROM student");

        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDelMath);
        jdbcTemplate.execute(sqlDelScience);
        jdbcTemplate.execute(sqlDelHistory);
    }

    @Test
    public  void getStudentHttpRequest() throws Exception {
        CollegeStudent student1=new CollegeStudent("abc","xyz","s@gmai.com");
        CollegeStudent student2=new CollegeStudent("abc2","xyz2","s2@gmai.com");

        List<CollegeStudent> studentList=new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(mockStudentService.getGradebook()).thenReturn(studentList);

        assertIterableEquals(studentList, mockStudentService.getGradebook());

        MvcResult mvcResult=mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();

//        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"index");
    }

    @Test
    public void createStudentHttpReuqest() throws Exception {



//        MvcResult mvcResult = this.mockMvc.perform(post("/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstname", mockHttpServletRequest.getParameterValues("firstname"))
//                        .param("lastname", mockHttpServletRequest.getParameterValues("lastname"))
//                        .param("emailAddress", mockHttpServletRequest.getParameterValues("emailAddress")))
//                .andExpect(status().isOk()).andReturn();
//
//        ModelAndView mav = mvcResult.getModelAndView();
//
//        ModelAndViewAssert.assertViewName(mav, "index");
//
//        CollegeStudent verifyStudent = studentDao.findByEmailAddress("found@gmail.com");
//
//        assertNotNull(verifyStudent, "Student should be found");


        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", mockHttpServletRequest.getParameterValues("firstname"))
                        .param("lastname", mockHttpServletRequest.getParameterValues("lastname"))
                        .param("emailAddress", mockHttpServletRequest.getParameterValues("emailAddress")))
                .andExpect(status().is3xxRedirection()) // Expecting a redirection status
                .andReturn();

        // Retrieve the redirected URL after the POST request
        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertNotNull(redirectedUrl); // Ensure the URL is not null

        // You might want to follow the redirection to test further if needed
        MvcResult redirectedResult = this.mockMvc.perform(get(redirectedUrl))
                .andExpect(status().isOk()) // Assuming the redirected view will be OK
                .andReturn();

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("found@gmail.com");
        assertNotNull(verifyStudent, "Student should be found");

    }

    @Test
    public void deleteStudent() throws Exception{
//        assertTrue(studentDao.findById(1).isPresent());
//
//        MvcResult mvcResult=mockMvc.perform(
//                MockMvcRequestBuilders.get("/delete/student/{id}",1)
//        ).andExpect(status().isOk()).andReturn();
//
//        ModelAndView modelAndView=mvcResult.getModelAndView();
//
//        assert modelAndView != null;
//        ModelAndViewAssert.assertViewName(modelAndView,"index");
//
//        assertFalse(studentDao.findById(1).isPresent());

        assertTrue(studentDao.findById(1).isPresent()); // Ensure the student with ID 1 exists initially

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/delete/student/{id}", 1)
                ).andExpect(status().is3xxRedirection()) // Expecting a redirection status
                .andReturn();

        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertNotNull(redirectedUrl); // Ensure the redirected URL is not null

        MvcResult redirectedResult = this.mockMvc.perform(get(redirectedUrl))
                .andExpect(status().isOk()) // Assuming the redirected view will be OK
                .andReturn();



        assertFalse(studentDao.findById(1).isPresent());

    }

    @Test
    public void deleteStudentError() throws Exception{


        assertFalse(studentDao.findById(0).isPresent());



        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/delete/student/{id}", 0)
                ).andExpect(status().isOk()) // Expecting a redirection status
                .andReturn();
//
        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");




    }

    @Test
    public void studentInfoHttpReq() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}",1)).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"studentInformation");
    }

    @Test
    public void studentInfoHttpReqError() throws Exception {
        assertFalse(studentDao.findById(0).isPresent());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}",0)).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"error");
    }

    @Test
    public void createValidGrade() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        GradebookCollegeStudent student=realstudentService.studentInformation(1);
//
        assertEquals(1,student.getStudentGrades().getMathGradeResults().size());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade","85.00").param("gradeType","math")
                .param("studentId","1")
        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"studentInformation");

        student=realstudentService.studentInformation(1);

        assertEquals(2,student.getStudentGrades().getMathGradeResults().size());

    }

    @Test //student absent
    public void createInValidGrade() throws Exception {

        assertFalse(studentDao.findById(0).isPresent());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade","85.00").param("gradeType","math")
                .param("studentId","0")
        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"error");




    }

    @Test //student absent
    public void createInValidGrade2() throws Exception {

        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade","85.00").param("gradeType","geo")
                .param("studentId","1")
        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"error");




    }

    @Autowired
    MathGradesDao mathGradesDao;


    @Test
    public void deleteGrade() throws Exception {
        Optional<MathGrade>mathGrade=mathGradesDao.findById(1);
        assertTrue(mathGrade.isPresent());


        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/grades/{id}/{gradeType}","1","math")

        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"studentInformation");

        assertFalse(mathGradesDao.findById(1).isPresent());

    }

    @Test
    public void deleteGradeInvalid() throws Exception {
        Optional<MathGrade>mathGrade=mathGradesDao.findById(0);
        assertFalse(mathGrade.isPresent());


        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/grades/{id}/{gradeType}","0","math")

        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"error");



    }

    @Test
    public void deleteGradeInvalid2() throws Exception {
        Optional<MathGrade>mathGrade=mathGradesDao.findById(1);
        assertTrue(mathGrade.isPresent());


        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/grades/{id}/{gradeType}","1","geo")

        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView=mvcResult.getModelAndView();
        assert modelAndView != null;
        ModelAndViewAssert.assertViewName(modelAndView,"error");



    }

}
