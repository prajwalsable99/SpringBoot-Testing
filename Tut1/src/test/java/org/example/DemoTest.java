package org.example;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)


//@TestMethodOrder(MethodOrderer.MethodName.class)
//@TestMethodOrder(MethodOrderer.DisplayName.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // use @Order(int) with methods

class DemoTest {

    private  Demo demo;

    @BeforeAll
    static void beforeAll() {
        System.out.println("[In Before all ]");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("[In After all ]");
    }

    @BeforeEach
    void setUp() {
        System.out.println("[In Before Each ]");
        demo=new Demo();

    }

    @AfterEach
    void tearDown() {
        System.out.println("[In After  each ]");

    }



    @Test
//    @DisplayName("Test for add func()")
//    @Order(10)
    void addTest() {

        System.out.println("[In addTest() method ]");

        assertEquals(5,demo.add(2,3),"2+3 must be 5");

    }

    @Test
//    @DisplayName("Test for checkNull func()")
    void checkNullTest() {
        System.out.println("[In checkNullTest() method ]");
                assertNull(demo.checkNull(null), "object must be null");
//        assertNotEquals(demo.checkNull( demo), "object must be null");


    }

    @Test
    void sameOrNotTest(){
        System.out.println("[In sameOrNotTest() method ]");
        assertSame(demo.getS1(),demo.getS2(),"both should be same");
        assertNotSame(demo.getS1(),"orange","both should be diff");
    }

    @Test
    void isGreaterTest() {
        System.out.println("[In isGreaterTest() method ]");

        assertTrue(demo.isGreater(10,3),"10 must be greater than 3");
        assertFalse(demo.isGreater(10,11),"10 must  not be greater than 11");

    }

    @Test
    void arrayEqualsTest() {
        System.out.println("[In arrayEqualsTest() method ]");
         String [] temparr= {"A","B","C"};
        assertArrayEquals(demo.getFirst3LettersABC(),temparr,"both must be eeual");
    }
    @Test
    void iterableEqualsTest() {
        System.out.println("[In iterableEqualsTest() method ]");
        List<String> templist =  List.of("A", "B", "C");

        assertIterableEquals(demo.getArrayList(),templist,"both must be eeual");
    }

//    @Order(1)
    @Test
    void throwExceptionTest() {

        assertThrows(Exception.class,()->{demo.throwException(-8);},"should throw exception");
        assertDoesNotThrow(()->{demo.throwException(8);},"should  not throw exception");
    }

    @Test
    void checkTimeout() {

        assertTimeoutPreemptively(Duration.ofSeconds(3),()->{demo.checkTimeout();},"should finish in 3 sec");

    }
}