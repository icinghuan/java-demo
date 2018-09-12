package top.icinghuan.demo.javatest;

/**
 * @author : xy
 * @date : 2018/9/12
 * Description :
 */
public enum EnumTest {

    ENUM1(1, "1"),
    ENUM2(2, "2");

    private Integer code;
    private String desc;

    EnumTest(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static void main(String[] args) {
        System.out.println(EnumTest.ENUM1);
    }
}
