package org.Prajwal;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {
    FizzBuzz fizzBuzz;
    @BeforeEach
    void setUp() {

         fizzBuzz=new FizzBuzz();
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    @Order(1)
    public void testForDivisibleByThree(){

        String expected="Fizz";

        assertEquals(expected,fizzBuzz.compute(33),"should be Fizz");

//        fail("failed : testForDivisibleByThree()");

    }

    @Test
    @Order(2)
    void testFordivisibleByFive() {

        String expected="Buzz";

        assertEquals(expected,fizzBuzz.compute(35),"should be Buzz");
    }

    @ParameterizedTest(name = "value={0},expected={1}")
    @Order(3)
    @CsvFileSource(resources = "/test-data.csv")
    void testFOrMultiple(int value, String expected){
        assertEquals(expected,fizzBuzz.compute(value),"-");
    }
}