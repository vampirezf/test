package excelFileRead;


import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zf
 * @date 2021/7/20 9:59
 * excel读取匹配手机号及身份证号
 */
public class ExcelFileReadUtilOne {

    public static Map<String, List<String>> test(File file) throws Exception{
        FileInputStream in = new FileInputStream(file);
        Map<String,List<String>> mapData = new HashMap<String, List<String>>();
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
        int sheetNums = wk.getNumberOfSheets();
        for(int i = 0 ; i < sheetNums;i ++){
            List<String> sheetData = new ArrayList<String>();
            Sheet sheet = wk.getSheetAt(i);
            String sheetName = wk.getSheetName(i);
            //遍历所有的行
            for (Row row : sheet) {
                StringBuilder sb = new StringBuilder();
                //遍历所有的列
                for (Cell cell : row) {
                    sb.append(cell.getStringCellValue());
                }
                sheetData.add(sb.toString());
            }
            mapData.put(sheetName,sheetData);

        }
        return mapData;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\work_space\\test");
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file1: files){
                Map<String, List<String>> test = test(file1);
                for(Map.Entry<String,List<String>> m : test.entrySet()){
                    for(String s : m.getValue()){
                        System.out.println(s);
                    }
                }
            }
        }
    }
}
