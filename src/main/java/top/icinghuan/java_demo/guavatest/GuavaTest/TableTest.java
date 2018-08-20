package top.icinghuan.java_demo.guavatest.GuavaTest;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;

/**
 * @author : xy
 * @date : 2018/7/17
 * Description :
 */
public class TableTest {

    public static void tableTest() {
        Table<String, String, String> employeeTable = HashBasedTable.create();

        employeeTable.put("IBM", "101", "Mahesh");
        employeeTable.put("IBM", "102", "Ramesh");
        employeeTable.put("IBM", "103", "Suresh");

        employeeTable.put("Microsoft", "111", "Sohan");
        employeeTable.put("Microsoft", "112", "Mohan");
        employeeTable.put("Microsoft", "113", "Rohan");

        employeeTable.put("TCS", "121", "Ram");
        employeeTable.put("TCS", "122", "Shyam");
        employeeTable.put("TCS", "123", "Sunil");

        Map<String, String> ibmEmployees = employeeTable.row("IBM");

        for (Map.Entry<String, String> entry : ibmEmployees.entrySet()) {
            System.out.println("Emp id: " + entry.getKey() + ", Name: " + entry.getValue());
        }

    }
}
