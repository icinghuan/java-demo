package top.icinghuan.demo.guavatest.GuavaTest;

import com.google.common.base.Preconditions;

/**
 * @author : xy
 * @date : 2018/7/16
 * Description :
 */
public class PreconditionsTest {

    public static void preconditionsTest() {
        PreconditionsTest preconditionsTest = new PreconditionsTest(-1);
    }

    private PreconditionsTest(double input) {
        Preconditions.checkArgument(input > 0.0, "Illegal Argument passed: Negative value %s.", input);
    }
}
