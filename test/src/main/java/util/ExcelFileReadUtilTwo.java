package util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author zf
 * @date 2021/7/20 9:59
 */
public class ExcelFileReadUtilTwo {

    //手机号正则
    private String REX_PHONE= "(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})(?!(\\.))(?!(\\d+))";
    //身份证正则
    private String REX_IDCARD= "([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])(?!(\\.))(?!(\\d{1,20}))";

    public static void main(String[] args) {
        String filePath = "C:\\work_space\\test\\2021年上半年数据.xls";
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Workbook workbook = null;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls") || filePath.endsWith(".et")) {
                workbook = new HSSFWorkbook(fis);
            }
            fis.close();
            /* 读EXCEL文字内容 */
            // 获取第一个sheet表，也可使用sheet表名获取
            Sheet sheet = workbook.getSheetAt(0);
            // 获取行
            Iterator<Row> rows = sheet.rowIterator();
            Row row;
            Cell cell;

            while (rows.hasNext()) {
                row = rows.next();
                // 获取单元格
                Iterator<Cell> cells = row.cellIterator();
                int num = 0;
                while (cells.hasNext()) {
                    cell = cells.next();
                    num ++;
                    String cellValue = POIUtil.getCellValue(cell);
                    System.out.print(num +"==="+ cellValue + " ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
