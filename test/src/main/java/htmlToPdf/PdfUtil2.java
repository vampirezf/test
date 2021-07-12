package htmlToPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PDF文件工具类
 *
 * @author
 * @date
 */
public class PdfUtil2 {

    /**
     * 根据html文件生成pdf
     * @param pdfFilePath pdf文件生成路径
     * @param htmlFilePath html文件路径
     */
    public static void parseHtml2PdfByFilePath(String pdfFilePath, String htmlFilePath) {
        Document document = new Document();
        PdfWriter writer = null;
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileOutputStream = new FileOutputStream(pdfFilePath);
            writer = PdfWriter.getInstance(document, fileOutputStream);
            // 设置底部距离60，解决重叠问题
            document.setPageSize(PageSize.A4);
            document.setMargins(50, 45, 50, 60);
            document.setMarginMirroring(false);
            document.open();
            StringBuffer sb = new StringBuffer();
            fileInputStream = new FileInputStream(htmlFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            String readStr = "";
            while ((readStr = br.readLine()) != null) {
                sb.append(readStr);
            }
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(sb.toString().getBytes("Utf-8")), null, Charset.forName("UTF-8"), new MyFontProvider("/template/pdf/SimSun.ttf"));
            //XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(sb.toString().getBytes("Utf-8")), null, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != document) {
                document.close();
            }
            if (null != writer) {
                writer.close();
            }
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据html字符串内容生成pdf
     *
     * @param pdfFilePath pdf文件存储位置
     * @param htmlcontent html内容
     */
    public static void parseHtml2PdfByString(String pdfFilePath, String htmlcontent) {
        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            // 设置底部距离60，解决重叠问题
            document.setPageSize(PageSize.A4);
            document.setMargins(50, 45, 50, 60);
            document.setMarginMirroring(false);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(htmlcontent.getBytes("UTF-8")), null, Charset.forName("UTF-8"), new MyFontProvider("/template/pdf/SimSun.ttf"));
            //XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(htmlcontent.getBytes("UTF-8")), null, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != document) {
                document.close();
            }
            if (null != writer) {
                writer.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            // 本地
            String htmlFile = "C:\\test\\1.html";
            String pdfFile = "/test/test1.pdf";
            //String htmlContent = "<html><body style=\"font-size:12.0pt; font-family:宋体\">" + "<h1>Test</h1><p>测试中文Hello World</p></body></html>";
            String htmlContent = "";

            //过滤img标签
            String regEx_script = "<\\s*img(.+?)src=[\"'](.*?)[\"']\\s*/?\\s*>";
            Pattern p_html = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlContent);
            htmlContent = m_html.replaceAll("");
            //替换<br> to <br></br>
            htmlContent = htmlContent.replace("<br>","<br></br>");
            parseHtml2PdfByString(pdfFile,htmlContent);
            //parseHtml2PdfByFilePath(pdfFile,htmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}