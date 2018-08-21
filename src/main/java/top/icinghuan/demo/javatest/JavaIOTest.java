package top.icinghuan.demo.javatest;

import java.io.*;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
public class JavaIOTest {

    public static void main(String[] args) throws IOException {
//        FileInputStream in = null;
//        FileOutputStream out = null;
        //FileReader in = null;
        FileWriter out = null;

        try {
//            in = new FileInputStream("src/main/resources/in.txt");
//            out = new FileOutputStream("src/main/resources/out.txt");
            //in = new FileReader("src/main/resources/in.txt");
            out = new FileWriter("src/main/resources/out.txt");

            out.write("<FlexQueryResponse queryName=\"cash transactions\" type=\"AF\">\n" +
                    "    <FlexStatements count=\"1\">\n" +
                    "        <FlexStatement accountId=\"IXXXXX\" fromDate=\"20180420\" toDate=\"20180420\" period=\"LastBusinessDay\"\n" +
                    "                       whenGenerated=\"20180423;014358\">\n" +
                    "            <CashTransactions>");
            int c;
            for (int i = 0; i < 10; ++i) {
                out.write("                <CashTransaction accountId=\"UXXXX\" currency=\"USD\" fxRateToBase=\"1\" assetCategory=\"\" symbol=\"\" description=\"CASH RECEIPTS / ELECTRONIC FUND TRANSFERS\" conid=\"\" securityID=\"\" securityIDType=\"\" multiplier=\"0\" principalAdjustFactor=\"\" dateTime=\"20180420\" amount=\"4974\" type=\"Deposits/Withdrawals\" tradeID=\"\" code=\"\" transactionID=\"8915567158\" reportDate=\"20180420\" />\n");
            }
            out.write("            </CashTransactions>\n" +
                    "        </FlexStatement>\n" +
                    "    </FlexStatements>\n" +
                    "</FlexQueryResponse>");
//            while ((c = in.read()) != -1) {
//                out.write(c);
//            }
        } finally {
//            if (in != null) {
//                in.close();
//            }
            if (out != null) {
                out.close();
            }
        }
    }
}
