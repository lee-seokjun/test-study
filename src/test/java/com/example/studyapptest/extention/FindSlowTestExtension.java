package com.example.studyapptest.extention;

import java.lang.reflect.Method;

import com.example.studyapptest.annotations.SlowTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback
{
    private final long THRESHOLD ;

    public FindSlowTestExtension(long THRESHOLD)
    {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception
    {
        Method targetMethod =extensionContext.getRequiredTestMethod();
        SlowTest annotion = targetMethod.getAnnotation(SlowTest.class);
        String testClassName = extensionContext.getRequiredTestClass().getName();

        String targetMethodName =extensionContext.getRequiredTestMethod().getName();

        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(testClassName, targetMethodName));
        long startTime = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - startTime;
        if (duration > THRESHOLD && annotion == null) {
            System.out.printf("please consider mark method [%s] with @SlowTest \n", targetMethodName);
        }

    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception
    {
        String testClassName = extensionContext.getRequiredTestClass().getName();
        String targetMethodName =extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(testClassName, targetMethodName));
        store.put("START_TIME", System.currentTimeMillis());

    }
}
