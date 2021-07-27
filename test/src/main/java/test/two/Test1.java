///*
// * FileCenterController.java         2016年5月23日 <br/>
// *
// * Copyright (c) 1994-1999 AnHui LonSun, Inc. <br/>
// * All rights reserved.	<br/>
// *
// * This software is the confidential and proprietary information of AnHui	<br/>
// * LonSun, Inc. ("Confidential Information").  You shall not	<br/>
// * disclose such Confidential Information and shall use it only in	<br/>
// * accordance with the terms of the license agreement you entered into	<br/>
// * with Sun. <br/>
// */
//
//package cn.lonsun.staticcenter.controller;
//
//import cn.lonsun.cache.client.CacheHandler;
//import cn.lonsun.common.excelFileRead.AppUtil;
//import cn.lonsun.content.internal.entity.BaseContentEO;
//import cn.lonsun.content.internal.service.IBaseContentService;
//import cn.lonsun.core.base.entity.AMockEntity;
//import cn.lonsun.core.exception.BaseRunTimeException;
//import cn.lonsun.core.excelFileRead.TipsMode;
//import cn.lonsun.fileserver.service.IFileServerService;
//import cn.lonsun.staticcenter.generate.excelFileRead.MongoUtil;
//import cn.lonsun.staticcenter.generate.excelFileRead.PathUtil;
//import cn.lonsun.staticcenter.excelFileRead.MyFontProvider;
//import cn.lonsun.system.filecenter.internal.entity.FileCenterEO;
//import cn.lonsun.system.filecenter.internal.service.IFileCenterService;
//import cn.lonsun.excelFileRead.LoginPersonUtil;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.*;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import com.mongodb.DB;
//import com.mongodb.Mongo;
//import com.mongodb.gridfs.GridFS;
//import com.mongodb.gridfs.GridFSDBFile;
//import org.apache.commons.lang3.StringUtils;
//import org.bson.types.ObjectId;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.swing.*;
//import java.io.*;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.excelFileRead.HashMap;
//import java.excelFileRead.Map;
//import java.excelFileRead.regex.Matcher;
//import java.excelFileRead.regex.Pattern;
//
///**
// * 文件下载 <br/>
// *
// * @author fangtinghua <br/>
// * @version v1.0 <br/>
// * @date 2016年5月23日 <br/>
// */
//@Controller(value = "staticcenterDownloadController")
//@RequestMapping(value = "download")
//public class DownloadController {
//
//    @Value("${attach.path:''}")
//    private String attach;// 附件文件路径
//    @Autowired
//    private IFileServerService fileServerService;
//    @Resource
//    private IFileCenterService fileCenterService;
//    @Autowired
//    private IBaseContentService baseContentService;
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @RequestMapping("{mongoId}")
//    public void download(@PathVariable String mongoId, HttpServletRequest request, HttpServletResponse response) {
//        GridFSDBFile gridFSDBFile = getGridFSDBFile(mongoId, "fs",request);
//        if((gridFSDBFile==null||gridFSDBFile.getInputStream()==null) && mongoId.contains(".")){
//            gridFSDBFile = getGridFSDBFile(mongoId.substring(0,mongoId.lastIndexOf(".")), "fs",request);
//        }
//        if(gridFSDBFile==null||gridFSDBFile.getInputStream()==null){
//            gridFSDBFile = getXXGKGridFSDBFile(mongoId, "fs",request);
//            //pdf预览
//            if ("pdf".equals(gridFSDBFile.getContentType())) {
//                viewFile(response, gridFSDBFile);
//            } else {//文件下载
//                downloadFileXXGK(response, mongoId, "fs",request);
//            }
//        }
//        if(gridFSDBFile!=null){
//            //pdf预览
//            if ("pdf".equals(gridFSDBFile.getContentType())) {
//                viewFile(response, gridFSDBFile);
//            } else {//文件下载
//                downloadFile(response, mongoId, "fs",request);
//            }
//        }
//
//
//
//    }
//
//    //private Mongo mongo = new Mongo("10.151.60.85", 27017);
//    //整站迁移老站
//    private Mongo mongo = new Mongo("10.151.60.84", 27017);
//    //private Mongo mongo = new Mongo("127.0.0.1", 27117);
//    //信息公开老站--宿州、萧县、砀山、灵璧
//    private Mongo mongo2 = new Mongo("10.151.86.7", 27017);
//    //private Mongo mongo2 = new Mongo("127.0.0.1", 21017);
//
//    private DB db;
//
//
//    private DB getEX8DB(HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        logger.info("requestURL:" + requestURL);
//        if(requestURL.startsWith("http://60.171.247.208:50013")
//                ||requestURL.startsWith("http://10.151.58.118:50013")
//                ||requestURL.startsWith("http://www.sixian.gov.cn")){//泗县
//            db = mongo.getDB("sixianex8");
//        }else if(requestURL.startsWith("http://60.171.247.208:50012")
//                ||requestURL.startsWith("http://10.151.58.118:50012")
//                ||requestURL.startsWith("http://www.ahxx.gov.cn")){//萧县
//            db = mongo.getDB("xiaoxianex8");
//        }else if(requestURL.startsWith("http://60.171.247.208:50015")
//                ||requestURL.startsWith("http://10.151.58.118:50015")
//                ||requestURL.startsWith("http://www.dangshan.gov.cn")){//砀山县
//            db = mongo.getDB("ex9_dangshan");
//        }else if(requestURL.startsWith("http://60.171.247.208:50014")
//                ||requestURL.startsWith("http://10.151.58.118:50014")
//                ||requestURL.startsWith("http://www.szyq.gov.cn")){//埇桥区
//            db = mongo.getDB("yqqex8");
//        }else{//宿州市政府
//            db = mongo.getDB("suzhouex8");
//        }
//        logger.info("db.getName():" + db.getName());
//        return db;
//    }
//
//    private DB getEX8XXGKDB(HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        logger.info("requestURL:" + requestURL);
//        if(requestURL.startsWith("http://60.171.247.208:50012")
//                ||requestURL.startsWith("http://10.151.58.118:50012")
//                ||requestURL.startsWith("http://www.ahxx.gov.cn")){//萧县
//            db = mongo2.getDB("ex8");
//        }else if(requestURL.startsWith("http://60.171.247.208:50015")
//                ||requestURL.startsWith("http://10.151.58.118:50015")
//                ||requestURL.startsWith("http://localhost:8081")
//                ||requestURL.startsWith("http://www.dangshan.gov.cn")){//砀山县
//            db = mongo2.getDB("ex8");
//        }else if(requestURL.startsWith("http://www.lingbi.gov.cn")){//灵璧县
//            db = mongo2.getDB("ex8");
//        }else{//宿州市政府
//            db = mongo2.getDB("xxgknew");
//        }
//        logger.info("db.getName():" + db.getName());
//        return db;
//    }
//
//    private DB getEX8XXGKDB2(HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        logger.info("requestURL:" + requestURL);
//        db = mongo2.getDB("ex8");
//        logger.info("db.getName():" + db.getName());
//        return db;
//    }
//
//
//
//
//    private GridFSDBFile getGridFSDBFile(String mongoDbId, String collectionName,HttpServletRequest request) {
//        GridFS gridFS = new GridFS(getEX8DB(request), collectionName);
//        //如果是文件名称
//        if (mongoDbId.contains(".")) {
//            return gridFS.findOne(mongoDbId);
//        }
//        ObjectId objId = new ObjectId(mongoDbId);
//        return gridFS.findOne(objId);
//    }
//
//
//    private GridFSDBFile getXXGKGridFSDBFile(String mongoDbId, String collectionName,HttpServletRequest request) {
//        GridFS gridFS = new GridFS(getEX8XXGKDB(request), collectionName);
//        if(mongoDbId.contains(".pdf")){
//            String substring = mongoDbId.substring(0, mongoDbId.indexOf(".pdf"));
//            ObjectId objId = new ObjectId(substring);
//            return gridFS.findOne(objId);
//        }
//        //如果是文件名称
//        if (mongoDbId.contains(".")) {
//            return gridFS.findOne(mongoDbId);
//        }
//        ObjectId objId = new ObjectId(mongoDbId);
//        return gridFS.findOne(objId);
//    }
//
//    private GridFSDBFile getXXGKGridFSDBFile2(String mongoDbId, String collectionName,HttpServletRequest request) {
//        GridFS gridFS = new GridFS(getEX8XXGKDB2(request), collectionName);
//        //如果是文件名称
//        if (mongoDbId.contains(".")) {
//            return gridFS.findOne(mongoDbId);
//        }
//        ObjectId objId = new ObjectId(mongoDbId);
//        return gridFS.findOne(objId);
//    }
//
//
//
//
//
//
//    private void viewFile(HttpServletResponse response, GridFSDBFile gridFSDBFile) {
//        OutputStream sos = null;
//        if (gridFSDBFile != null) {
//            try {
//                sos = response.getOutputStream();
//                String fileName = gridFSDBFile.getFilename();
//                if (gridFSDBFile.getMetaData() != null) {
//                    fileName = String.valueOf(gridFSDBFile.getMetaData().get("OriginalFilename"));
//                }
//                String agent = LoginPersonUtil.getRequest().getHeader("User-Agent").toLowerCase();
//                response.setContentType("application/pdf");
//                if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
//                    response.addHeader("Content-Disposition", "filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
//                } else {
//                    response.addHeader("Content-Disposition", "filename=" + fileName);
//                }
//                // 向客户端输出文件
//                gridFSDBFile.writeTo(sos);
//                sos.flush();
//                sos.close();
//            } catch (Exception e) {
//                throw new BaseRunTimeException(TipsMode.Message.toString(), "mongoDb下载文件出错");
//            }
//        }
//    }
//
//    private void downloadFile(HttpServletResponse response, String mongoDbId, String collectionName,HttpServletRequest request) {
//        GridFSDBFile gridFSDBFile = getGridFSDBFile(mongoDbId, collectionName,request);
//        OutputStream sos;
//        if (gridFSDBFile != null) {
//            try {
//                sos = response.getOutputStream();
//                String fileName = String.valueOf(gridFSDBFile.getMetaData().get("OriginalFilename"));
//                System.out.println("fileName:" + fileName);
//                response.setContentType("application/octet-stream");
//                String agent = LoginPersonUtil.getRequest().getHeader("User-Agent").toLowerCase();
//                if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
//                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
//                } else {
//                    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//                }
//                // 向客户端输出文件
//                gridFSDBFile.writeTo(sos);
//                sos.flush();
//                sos.close();
//            } catch (Exception e) {
//                throw new BaseRunTimeException(TipsMode.Message.toString(), "mongoDb下载文件出错");
//            }
//        }
//    }
//
//
//    private void downloadFileXXGK(HttpServletResponse response, String mongoDbId, String collectionName,HttpServletRequest request) {
//        GridFSDBFile gridFSDBFile = getXXGKGridFSDBFile(mongoDbId, collectionName,request);
//        OutputStream sos;
//        if (gridFSDBFile != null) {
//            try {
//                sos = response.getOutputStream();
//                String fileName = String.valueOf(gridFSDBFile.getMetaData().get("OriginalFilename"));
//                System.out.println("fileName:" + fileName);
//                response.setContentType("application/octet-stream");
//                String agent = LoginPersonUtil.getRequest().getHeader("User-Agent").toLowerCase();
//                if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
//                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
//                } else {
//                    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//                }
//                // 向客户端输出文件
//                gridFSDBFile.writeTo(sos);
//                sos.flush();
//                sos.close();
//            } catch (Exception e) {
//                throw new BaseRunTimeException(TipsMode.Message.toString(), "mongoDb下载文件出错");
//            }
//        }
//    }
//
//
//
//    private void downloadFileXXGK2(HttpServletResponse response, String mongoDbId, String collectionName,HttpServletRequest request) {
//        GridFSDBFile gridFSDBFile = getXXGKGridFSDBFile2(mongoDbId, collectionName,request);
//        OutputStream sos;
//        if (gridFSDBFile != null) {
//            try {
//                sos = response.getOutputStream();
//                String fileName = String.valueOf(gridFSDBFile.getMetaData().get("OriginalFilename"));
//                System.out.println("fileName:" + fileName);
//                response.setContentType("application/octet-stream");
//                String agent = LoginPersonUtil.getRequest().getHeader("User-Agent").toLowerCase();
//                if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
//                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
//                } else {
//                    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//                }
//                // 向客户端输出文件
//                gridFSDBFile.writeTo(sos);
//                sos.flush();
//                sos.close();
//            } catch (Exception e) {
//                throw new BaseRunTimeException(TipsMode.Message.toString(), "mongoDb下载文件出错");
//            }
//        }
//    }
//
//
//
//    @RequestMapping("/file")
//    public void download(HttpServletRequest request, HttpServletResponse response,String filePath) {
//        String[] paths = filePath.split("/download/");
//        String path = paths[paths.length-1];
//        if (!AppUtil.isEmpty(path)){
//            GridFSDBFile gridFSDBFile = fileServerService.getGridFSDBFile(path,null);
//            if(gridFSDBFile.getContentType().equals("pdf")) {//pdf预览
//                fileServerService.viewFile(response,path,gridFSDBFile);
//            } else {//文件下载
//                fileServerService.downloadFile(response, path, null);
//            }
//        }
//    }
//
//    @RequestMapping("/fileNoSuffix")
//    public void fileNoSuffix(HttpServletRequest request, HttpServletResponse response,String filePath) {
//        ///oldfiles/bgsoldfiles/attachment/57df33cbceab0671439df91b/201911/201911141634312329_VkJ6HMIi?fileName=八公山区政府2018年财政决算、三公经费及说明.rar1
//        String[] paths = filePath.split("\\?");
//
//        if (paths != null && paths.length > 1) {
//            String path = paths[0];
//            String[] filenames = paths[1].split("=");
//
//            if (filenames != null && filenames.length > 1) {
//                String filename = filenames[1];
//                if (!AppUtil.isEmpty(path) && !AppUtil.isEmpty(filename)){
//                    int index = filename.lastIndexOf(".");
//                    if (index != -1) {
//                        String suffix = filename.substring(index+1,filename.length());
//                        //处理文件名
//                        String suffix_partten = "\\d*([A-Za-z]+)\\d*";
//                        Pattern pattern = Pattern.compile(suffix_partten);
//                        Matcher m = pattern.matcher(suffix);
//                        String newSuffix = null;
//                        while(m.find()){
//                            newSuffix = m.group(1);
//
//                            filename = filename.replace(suffix,newSuffix);
//
//                            //直接通过路径获取文件
//                            byte[] b = null;
//                            BufferedOutputStream bos = null;
//                            try {
//                                System.out.println(filename + "====/home/html" + path);
//                                InputStream in = new FileInputStream("/home/html" + path);
////                                InputStream in = new FileInputStream("D://" + path);
//
//                                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//                                byte[] buff = new byte[in.available()];
//                                int rc = 0;
//                                while ((rc = in.read(buff, 0, 100)) > 0) {
//                                    swapStream.write(buff, 0, rc);
//                                }
//                                b = swapStream.toByteArray();
//
//                                bos = new BufferedOutputStream(response.getOutputStream());
//
//                                response.setContentType("application/x-msdownload;");
//                                response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8"));
//                                response.setHeader("Content-Length", String.valueOf(b.length));
//
//                                bos.write(b);
//                                bos.flush();
//                                bos.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @RequestMapping("/mongoname/{mongoname}")
//    public void downloadByFileName(@PathVariable String mongoname, HttpServletRequest request, HttpServletResponse response) {
//        FileCenterEO fileCenter = fileCenterService.getByMongoName(mongoname);
//        if(null != fileCenter){
//            GridFSDBFile gridFSDBFile = fileServerService.getGridFSDBFile(fileCenter.getMongoId(),null);
//            if(gridFSDBFile.getContentType().equals("pdf")) {//pdf预览
//                fileServerService.viewFile(response,mongoname,gridFSDBFile);
//            } else {//文件下载
//                fileServerService.downloadFile(response, fileCenter.getMongoId(), null);
//            }
//        }else{
//            throw new BaseRunTimeException(TipsMode.Message.toString(),"文件不存在");
//        }
//    }
//
//    @RequestMapping("/attach/{id}")
//    public void download(@PathVariable Long id, @RequestParam(defaultValue = "") String owner, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            BaseContentEO eo = CacheHandler.getEntity(BaseContentEO.class, id);
//            String realName = eo.getAttachRealName();
//            String savedName = eo.getAttachSavedName();
//            response.setContentType("application/octet-stream");
//            File file = null;
//            if ("xiaoxian".equals(owner)) {
//                file = new File(attach + File.separator + savedName);
//            } else {
//                file = new File(attach + savedName.substring(0, 8) + File.separator + savedName);
//            }
//            String agent = request.getHeader("User-Agent").toLowerCase();
//            if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
//                response.addHeader("Content-Disposition", "attachment;filename=" + new String(realName.getBytes("GB2312"), "ISO-8859-1"));
//            } else {
//                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
//            }
//            FileInputStream inputStream = new FileInputStream(file);
//            ServletOutputStream out;
//            out = response.getOutputStream();
//            int b = 0;
//            byte[] buffer = new byte[1024];
//            while (b != -1) {
//                b = inputStream.read(buffer);
//                out.write(buffer, 0, b);
//            }
//            inputStream.close();
//            out.close();
//            out.flush();
//        } catch (Exception e) {
//            throw new BaseRunTimeException(TipsMode.Message.toString(), "文件下载失败！");
//        }
//    }
//
//
//    @RequestMapping("/download2/{mongoId}")
//    public void download2(@PathVariable String mongoId, HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("\n\n\n\ndownload2==============="+mongoId);
//        GridFSDBFile gridFSDBFile = getXXGKGridFSDBFile2(mongoId, "fs",request);
//        if((gridFSDBFile==null||gridFSDBFile.getInputStream()==null) && mongoId.contains(".")){
//            gridFSDBFile = getXXGKGridFSDBFile2(mongoId.substring(0,mongoId.lastIndexOf(".")), "fs",request);
//        }
//        System.out.println("\n\n\n\ngridFSDBFile.getFilename================="+gridFSDBFile.getFilename());
//        System.out.println("\n\n\n\ngridFSDBFile.getContentType================="+gridFSDBFile.getContentType());
//        if ("pdf".equals(gridFSDBFile.getContentType())) {
//            viewFile(response, gridFSDBFile);
//        } else {//文件下载
//            downloadFileXXGK2(response, mongoId, "fs",request);
//        }
//    }
//
//
//    /**
//     * 宿州主站单页面上传文件下载并统计下载量
//     * @param request
//     * @param response
//     * @param filePath
//     */
//    @RequestMapping("/fileDown")
//    public void fileDown(HttpServletRequest request, HttpServletResponse response,String filePath,Long siteId) {
//        String[] paths = filePath.split("/download/");
//        String path = paths[paths.length-1];
//        if (!AppUtil.isEmpty(path)){
//            Map<String,Object> params = new HashMap<>();
//            params.put("siteId",siteId);
//            params.put("recordStatus", AMockEntity.RecordStatus.Normal.toString());
//            params.put("filePath",filePath);
//            FileCenterEO entity = fileCenterService.getEntity(FileCenterEO.class, params);
//            if (!AppUtil.isEmpty(entity)){
//                if(AppUtil.isEmpty(entity.getDownNum())){
//                    entity.setDownNum(1L);
//                }else {
//                    entity.setDownNum(entity.getDownNum()+1L);
//                }
//                fileCenterService.updateEntity(entity);
//            }
//
//            GridFSDBFile gridFSDBFile = fileServerService.getGridFSDBFile(path,null);
//            if(gridFSDBFile.getContentType().equals("pdf")) {//pdf预览
//                fileServerService.viewFile(response,path,gridFSDBFile);
//            } else {//文件下载
//                fileServerService.downloadFile(response, path, null);
//            }
//        }
//    }
//
//
//
//    @RequestMapping("/htmlExportPdf")
//    @ResponseBody
//    public  void htmlExportPdf(HttpServletResponse response, Long id,String siteId) {
//        BaseContentEO entity = baseContentService.getEntity(BaseContentEO.class,id);
//        String name="";
//        StringBuffer pdfFile = new StringBuffer();
//        if(!AppUtil.isEmpty(entity)){
//            name=entity.getTitle();
//        }
//        String content = MongoUtil.queryById(id);
//        try {
//            // 本地
//            //pdfFile = "/test/"+name+".pdf";
//            //线上
//            pdfFile.append(PathUtil.getPathConfig().getCreatePath());
//            if (!pdfFile.toString().endsWith("/")) {
//                pdfFile.append(PathUtil.SEPARATOR);
//            }
//            pdfFile.append("pdf");
//            if (!pdfFile.toString().endsWith("/")) {
//                pdfFile.append(PathUtil.SEPARATOR);
//            }
//            pdfFile.append(name+".pdf");
//            //过滤img标签
//            String regEx_script = "<\\s*img(.+?)src=[\"'](.*?)[\"']\\s*/?\\s*>";
//            Pattern p_html = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//            Matcher m_html = p_html.matcher(content);
//            content = m_html.replaceAll("");
//            //替换<br> to <br></br>
//            content = content.replace("<br>","<br></br>");
//            parseHtml2PdfByString(pdfFile.toString(),content);
//
//            /*if("11708048".equals(siteId)){
//                waterMark(pdfFile.toString(),pdfFile.toString(),"宿州市人民政府信息公开");
//            }*/
//
//            //输出到浏览器
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("multipart/form-data");
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" +
//                            new String( (name + ".pdf").getBytes(),
//                                    "iso-8859-1"));
//            writeBytes(pdfFile.toString(), response.getOutputStream());
//            File file = new File(pdfFile.toString());
//            if (file.exists()) {
//                DataOutputStream temps = new DataOutputStream(response.getOutputStream());
//                DataInputStream in = new DataInputStream(new FileInputStream(pdfFile.toString()));
//                byte[] b = new byte[2048];
//                while ((in.read(b)) != -1) {
//                    temps.write(b);
//                    temps.flush();
//                }
//                in.close();
//                temps.close();
//            } else {
//                logger.info("文件不存在!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    /**
//     * 根据html字符串内容生成pdf
//     *
//     * @param pdfFilePath pdf文件存储位置
//     * @param htmlcontent html内容
//     */
//    public static void parseHtml2PdfByString(String pdfFilePath, String htmlcontent) {
//        Document document = new Document();
//        PdfWriter writer = null;
//        try {
//            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
//            // 设置底部距离60，解决重叠问题
//            document.setPageSize(PageSize.A4);
//            document.setMargins(50, 45, 50, 60);
//            document.setMarginMirroring(false);
//            document.open();
//            // 创建第一页（如果只有一页的话，这一步可以省略）
//            //document.newPage();
//
//            // 加入水印
//
//            PdfContentByte waterMar = writer.getDirectContentUnder();
//            // 开始设置水印
//            waterMar.beginText();
//            // 设置水印透明度
//            PdfGState gs = new PdfGState();
//            // 设置填充字体不透明度为0.4f
//            gs.setFillOpacity(0.4f);
//            try {
//                // 设置水印字体参数及大小                                  (字体参数，字体编码格式，是否将字体信息嵌入到pdf中（一般不需要嵌入），字体大小)
//                waterMar.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 60);
//                // 设置透明度
//                waterMar.setGState(gs);
//                // 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
//                waterMar.showTextAligned(Element.ALIGN_RIGHT, "www.tomatocc.com" , 500, 430, 45);
//                // 设置水印颜色
//                waterMar.setColorFill(BaseColor.GRAY);
//                //结束设置
//                waterMar.endText();
//                waterMar.stroke();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                waterMar = null;
//                gs = null;
//            }
//
//            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(htmlcontent.getBytes("UTF-8")), null, Charset.forName("UTF-8"),new MyFontProvider("/home/html/pdf/simsun.ttf"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != document) {
//                document.close();
//            }
//            if (null != writer) {
//                writer.close();
//            }
//        }
//    }
//
//    /**
//     * 输出指定文件的byte数组
//     *
//     * @param filePath 文件路径
//     * @param os 输出流
//     * @return
//     */
//    public static void writeBytes(String filePath, OutputStream os) throws IOException
//    {
//        FileInputStream fis = null;
//        try
//        {
//            File file = new File(filePath);
//            if (!file.exists())
//            {
//                throw new FileNotFoundException(filePath);
//            }
//            fis = new FileInputStream(file);
//            byte[] b = new byte[1024];
//            int length;
//            while ((length = fis.read(b)) > 0)
//            {
//                os.write(b, 0, length);
//            }
//        }
//        catch (IOException e)
//        {
//            throw e;
//        }
//        finally
//        {
//            if (os != null)
//            {
//                try
//                {
//                    os.close();
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//            if (fis != null)
//            {
//                try
//                {
//                    fis.close();
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    public static void waterMark(String inputFile,String outputFile, String waterMarkName) {
//        try {
//            PdfReader reader = new PdfReader(inputFile);
//            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
//
//            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
//
//            PdfGState gs = new PdfGState();
//            //改透明度
//            gs.setFillOpacity(0.5f);
//            gs.setStrokeOpacity(0.4f);
//            int total = reader.getNumberOfPages() + 1;
//
//            JLabel label = new JLabel();
//            label.setText(waterMarkName);
//
//            PdfContentByte under;
//            // 添加一个水印
//            for (int i = 1; i < total; i++) {
//                // 在内容上方加水印
//                under = stamper.getOverContent(i);
//                //在内容下方加水印
//                //under = stamper.getUnderContent(i);
//                gs.setFillOpacity(0.5f);
//                under.setGState(gs);
//                under.beginText();
//                //改变颜色
//                under.setColorFill(BaseColor.LIGHT_GRAY);
//                //改水印文字大小
//                under.setFontAndSize(base, 20);
//                under.setTextMatrix(70, 200);
//                //后3个参数，x坐标，y坐标，角度
//                under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, 500, 600, 55);
//
//                under.endText();
//            }
//            stamper.close();
//            reader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        waterMark("C:\\Users\\86181\\Desktop\\测试文档\\测试数据.pdf","C:\\Users\\86181\\Desktop\\水印\\测试数据.pdf","宿州市人民政府信息公开");
//    }
//
//
//}