package dynamicProxy;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TestLogging implements TestLoggingInterface{

    @Override
    @LogScam
    public void calculation(int param){
    }


//    public void calculation(int param, int param1){
//    }
//
//    @LogScam
//    public void calculation(int param, String param1){
//
//    }


//    @Override
//    public void logMethodParam(Object[] param) {
//        Arrays.asList(param).forEach(o-> System.out.println(o.toString()));
//        System.out.println("loMethodParam, param:" + param);
//    }

}

