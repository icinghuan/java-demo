package top.icinghuan.demo.javatest;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;
import static org.mockito.internal.verification.VerificationModeFactory.atMost;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
public class MockitoTest {
    @Test
    public void verifyInvocate(){

        List<String> mockedList = mock(List.class);
        //using mock
        mockedList.add("once");
        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        /**
         * 基本的验证方法
         * verify方法验证mock对象是否有没有调用mockedList.add("once")方法
         * 不关心其是否有返回值，如果没有调用测试失败。
         */
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");//默认调用一次,times(1)可以省略


        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //never()等同于time(0),一次也没有调用
        verify(mockedList, times(0)).add("never happened");

        //atLeastOnece/atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("twice");
        verify(mockedList, atMost(5)).add("three times");

    }
}
