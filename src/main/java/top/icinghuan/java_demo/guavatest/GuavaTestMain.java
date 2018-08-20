package top.icinghuan.java_demo.guavatest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.icinghuan.java_demo.guavatest.GuavaTest.*;

/**
 * @author : xy
 * @date : 2018/7/16
 * Description :
 */
@Slf4j
public class GuavaTestMain {

//    private static final Logger logger = LoggerFactory.getLogger(GuavaTestMain.class);
    public static void main(String[] args) {
        log.info("Current Time: {}", System.currentTimeMillis());
        log.info("begin");
        OptionalTest.optionalTest();
//        PreconditionsTest.preconditionsTest();
        OrderingTest.ordingTest();
        MultiSetTest.mutiSetTest();
        BimapTest.bimapTest();
        TableTest.tableTest();
        log.info("end");
        /**
         * Guava
         *  Optional
         *  Preconditions
         *  Ordering
         *  Objects
         *  Range
         *  Throwables
         *  Multiset
         *  Bimap
         *  Table
         *  LoadingCache
         *  Joiner
         *  Spiltter
         *  CharMatcher
         *  CaseFormat
         *  Bytes
         *  Shorts
         *  Ints
         *  Longs
         *  Floats
         *  Doubles
         *  Chars
         *  Booleans
         *  IntMath
         *  LongMath
         *  BigIntegerMath
         *  Multimap
         */
    }
}
