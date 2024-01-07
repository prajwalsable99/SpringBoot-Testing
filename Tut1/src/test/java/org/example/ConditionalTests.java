package org.example;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalTests {

    @Test
    @Disabled("dont run it as its method in dev")
    void basicTest(){

    }

    @Test
    @EnabledOnOs(OS.MAC)
    void basicTest2(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_21)
    void basicTest3(){

    }
    @Test
    @EnabledOnJre(JRE.JAVA_15)
    void basicTest4(){

    }

    @Test
    @EnabledForJreRange(max=JRE.JAVA_15)
    void basicTest5(){

    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_15)
    void basicTest6(){

    }


}