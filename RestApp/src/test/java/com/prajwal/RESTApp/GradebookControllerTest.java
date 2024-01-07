package com.prajwal.RESTApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prajwal.RESTApp.models.CollegeStudent;
import com.prajwal.RESTApp.models.MathGrade;
import com.prajwal.RESTApp.repository.HistoryGradesDao;
import com.prajwal.RESTApp.repository.MathGradesDao;
import com.prajwal.RESTApp.repository.ScienceGradesDao;
import com.prajwal.RESTApp.repository.StudentDao;
import com.prajwal.RESTApp.service.StudentAndGradeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class GradebookControllerTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Mock
    StudentAndGradeService mockStudentAndGradeService;
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

    private static MockHttpServletRequest request;
    public static final MediaType APP_JSON_UTF8=MediaType.APPLICATION_JSON;

    @Autowired
    private CollegeStudent collegeStudent;

    @BeforeAll
    static void beforeAll() {
        request=new MockHttpServletRequest();
        request.setParameter("fistname","chad");
        request.setParameter("lastname","darby");
        request.setParameter("emailAddress","luv2code@gmail.com");

    }
    @AfterAll
    static void afterAll() {

    }

    @BeforeEach()
    void setUp() {
        jdbcTemplate.execute(sqlCreateStudent);
        jdbcTemplate.execute(sqlCreateMath);
        jdbcTemplate.execute(sqlCreateScience);
        jdbcTemplate.execute(sqlCreateHistory);
    }


    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDelMath);
        jdbcTemplate.execute(sqlDelScience);
        jdbcTemplate.execute(sqlDelHistory);

    }

    @Test
    public  void getStudents() throws Exception {

        collegeStudent.setFirstName("chad");
        collegeStudent.setLastName("darby");
        collegeStudent.setEmailAddress("luv2code@gmail.com");
        entityManager.persist(collegeStudent);
        entityManager.flush();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APP_JSON_UTF8))
                .andExpect(jsonPath("$",hasSize(2)));

    }

    @Test
    public  void createStudent() throws Exception {

        collegeStudent.setFirstName("yash");
        collegeStudent.setLastName("raj");
        collegeStudent.setEmailAddress("yr22@gmail.com");


        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collegeStudent))

        ).andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(2)));

        CollegeStudent verify=studentDao.findByEmailAddress("yr22@gmail.com");
        assertNotNull(verify);

    }

    @Test
    public void deleteStudent() throws Exception {

        assertTrue(studentDao.findById(11).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}",11))
                .andExpect(status().isOk()).andExpect(content().contentType(APP_JSON_UTF8))
                .andExpect(jsonPath("$",hasSize(0)));
//
        assertFalse(studentDao.findById(11).isPresent());
    }

    @Test

    public void deleteStudentHttpRequestErrorPage() throws Exception {
        assertFalse(studentDao.findById(0).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));

    }
    @Test
    public void studentInformationHttpRequest() throws Exception {

        Optional<CollegeStudent> student = studentDao.findById(11);

        assertTrue(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 11))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APP_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(11)))
                .andExpect(jsonPath("$.firstName", is("prajwal")))
                .andExpect(jsonPath("$.lastName", is("sable")))
                .andExpect(jsonPath("$.emailAddress", is("prajsa99@gmail.com")));
    }

    @Test
    public void studentInformationHttpRequestEmptyResponse() throws Exception {

        Optional<CollegeStudent> student = studentDao.findById(0);

        assertFalse(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    public void createAValidGradeHttpRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "math")
                        .param("studentId", "11"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APP_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(11)))
                .andExpect(jsonPath("$.firstName", is("prajwal")))
                .andExpect(jsonPath("$.lastName", is("sable")))
                .andExpect(jsonPath("$.emailAddress", is("prajsa99@gmail.com")))
                .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(2)));
    }

    @Test
    public void createAValidGradeHttpRequestStudentDoesNotExistEmptyResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "math")
                        .param("studentId", "0"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    public void createANonValidGradeHttpRequestGradeTypeDoesNotExistEmptyResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "literature")
                        .param("studentId", "1"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }


    @Test
    public void deleteAValidGradeHttpRequest() throws Exception {

        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);

        assertTrue(mathGrade.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1 , "math"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APP_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(11)))
                .andExpect(jsonPath("$.firstName", is("prajwal")))
                .andExpect(jsonPath("$.lastName", is("sable")))
                .andExpect(jsonPath("$.emailAddress", is("prajsa99@gmail.com")))
                .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(0)));
    }

    @Test
    public void deleteANonValidGradeHttpRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1 , "literature"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }



}