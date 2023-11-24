package com.example.studyapptest.extention;

import com.example.studyapptest.annotations.SlowTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

//@ExtendWith(FindSlowTestExtension.class)
// NoArgus Extension에서만 사용 가능
public class ExtensionTest
{

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);
    @Test
    void test () throws InterruptedException
    {
        Thread.sleep(1000L);
    }
    @Test
    @SlowTest
    void testSlow () throws InterruptedException
    {
        Thread.sleep(1000L);
    }
}
