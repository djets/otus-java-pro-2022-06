package dynamicProxy;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TestLogging implements TestLoggingInterface {

    @LogScam
    @Override
    public void calculation(Object... param){
        System.out.println(Arrays.toString(param).replace("[", "").replace("]", ""));
    }
}

