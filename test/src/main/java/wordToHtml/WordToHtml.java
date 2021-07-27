package wordToHtml;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * word 转换成html
 */
public class WordToHtml {

    //手机号正则
    private static String REX_PHONE= "(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})(?!(\\.))(?!(\\d+))";
    //身份证正则
    private static String REX_IDCARD= "([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])(?!(\\.))(?!(\\d{1,20}))";

    /**
     * 2007版本word转换成html
     * @throws IOException
     */
    @Test
    public void Word2007ToHtml() throws IOException {
        String filepath = "C:/work_space/test/";
        String fileName = "1.docx";
        String htmlName = "测试.html";
        final String file = "C:/work_space/test/测试.docx";
        File f = new File(file);
        if (!f.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {

                // 1) 加载word文档生成 XWPFDocument对象
                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);

                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
                File imageFolderFile = new File(filepath);
                XHTMLOptions options = XHTMLOptions.create();
                //options.setExtractor(new FileImageExtractor(imageFolderFile));
                options.setIgnoreStylesIfUnused(false);
                options.setFragment(true);

                // 3) 将 XWPFDocument转换成XHTML
//                OutputStream out = new FileOutputStream(new File(filepath + htmlName));
//                XHTMLConverter.getInstance().convert(document, out, options);

                //也可以使用字符数组流获取解析的内容
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                XHTMLConverter.getInstance().convert(document, baos, options);
                String content = baos.toString();
                content = contentFilter(content);
                System.out.println(content);
                boolean phone = isPhone(content);
                boolean idCard = isIDCard(content);
                System.out.println(phone+"====="+idCard);
                baos.close();
            } else {
                System.out.println("Enter only MS Office 2007+ files");
            }
        }
    }

    /**
     * /**
     * 2003版本word转换成html
     * @throws IOException
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    @Test
    public void Word2003ToHtml() throws IOException, TransformerException, ParserConfigurationException {
        String filepath = "C:/work_space/test/";
        final String imagepath = "C:/test/image/";
        String fileName = "滕王阁序2003.doc";
        String htmlName = "滕王阁序2003.html";
        final String file = filepath + fileName;
        InputStream input = new FileInputStream(new File(file));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(imagepath);
                if(!imgPath.exists()){//图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagepath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagepath + suggestedName;
            }
        });

        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();

        File htmlFile = new File(filepath + htmlName);
        OutputStream outStream = new FileOutputStream(htmlFile);

        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");

        serializer.transform(domSource, streamResult);

        //也可以使用字符数组流获取解析的内容
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
        outStream.close();
    }

    /**
     * word2003转String
     * @param file
     * @throws Exception
     */
    @Test
    public void Word2003TOString(String file) throws Exception{
        File f = new File(file);
        if (f.getName().endsWith(".doc") || f.getName().endsWith(".DOC")) {
            InputStream input = new FileInputStream(f);
            HWPFDocument wordDocument = new HWPFDocument(input);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

            // 解析word文档
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();

            // 也可以使用字符数组流获取解析的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(baos);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);

            // 也可以使用字符数组流获取解析的内容
            String content = new String(baos.toByteArray());
            System.out.println(content);
            baos.close();
        } else {
            System.out.println("Enter only MS Office 2003 files");

        }
    }

    /**
     * 内容过滤
     * @param content
     * @return
     */
    public String contentFilter(String content){
        content = HtmlUtil.getTextFromHtml(content);//过滤html标签
        content = content.replaceAll("&ensp;", "");
        content = ProofreadUtil.strFilter4JsonConflict(content);//过滤Json特殊字符
        return content;
    }

    /**
     * 验证手机号
     * @param str
     * @return
     */
    public static boolean isPhone(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(REX_PHONE);
        m = p.matcher(str);
        b = m.find();
        return b;
    }

    /**
     * 验证身份证
     * @param str
     * @return
     */
    public static boolean isIDCard(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(REX_IDCARD);
        m = p.matcher(str);
        b = m.find();
        return b;
    }

}