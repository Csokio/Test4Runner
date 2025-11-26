package maytestuse.probetestsuite;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;

public class QAShellGB{

    public static void runTestCases(){

    }

    @Test
    public void dummyTestA(){
        System.out.println("dummyTestA is invoked");
        Assertions.assertTrue(true);
    }

    @Test
    public void dummyTestB(){
        System.out.println("dummyTestB is invoked");
        Assertions.assertArrayEquals(new int[]{1,2,4}, new int[]{1,2,4});
    }

    @Test
    public void dummyTestC(){
        System.out.println("dummyTestC is invoked");
        Assertions.assertEquals(2+2,1+3);
    }

}
