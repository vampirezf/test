/*
//业务
*/
/**
 * 下载量表报告pdf
 *
 * @param scaleLogDTO 量表日志dto
 * @return {@link Result<String>}
 *//*

@Override
public Result<String> evaluationReport(ScaleLogDTO scaleLogDTO) {
        Long logId = scaleLogDTO.getLogId();
        List<ScaleLogEchartsDTO> images = scaleLogDTO.getImgs();
        UmsScaleLog umsScaleLog = scaleLogMapper.getScaleLog(logId);
        Long scaleId = umsScaleLog.getScaleId();
        String pdfUrl = umsScaleLog.getPdfUrl();
        if (StringUtils.isNotEmpty(pdfUrl)) {
        return Result.success(pdfUrl);
        }
        String html = "reportTemplates.html";
        Map<String, Object> reportMap = new ConcurrentHashMap<>(16);
        if (Objects.nonNull(images) && !images.isEmpty()) {
        reportMap.put("imgs", images);
        }
        Long userId = umsScaleLog.getUserId();
        UmsUser umsUser = umsUserMapper.selectById(userId);
        String name;
        LocalDate birthday;
        int sex;
        if (Objects.isNull(umsUser)) {
        // 获取学生信息
        Long studentId = umsScaleLog.getStudentId();
        UmsStudent umsStudent = studentMapper.selectById(studentId);
        name = umsStudent.getRealName();
        birthday = umsStudent.getBirthday();
        sex = umsStudent.getSex();
        } else {
        name = umsUser.getRealName();
        birthday = umsUser.getBirthday();
        sex = umsUser.getSex();
        }
        reportMap.put("name", name);
        if (Objects.nonNull(birthday)) {
        reportMap.put("birth", birthday);
        } else {
        reportMap.put("birth", StringConstant.EMPTY);
        }
        if (NumberConstant.ZERO == sex) {
        reportMap.put("sex", "男");
        } else if (NumberConstant.ONE == sex) {
        reportMap.put("sex", "女");
        } else {
        reportMap.put("sex", "性别保密");
        }
        reportMap.put("testStyle", "自评");
        ScaleEvaluationReportVO scaleEvaluationReportVO = getScaleEvaluationLogDetail(logId);
        List<ScaleAnswerLog> scaleAnswerLogs = JSONArray.parseArray(umsScaleLog.getAnswerLog(), ScaleAnswerLog.class);
        scaleEvaluationReportVO.setScaleAnswerLogs(scaleAnswerLogs);
        Map<String, Object> scaleInfo = this.baseMapper.dictTypeByScaleId(scaleId, userId);
        reportMap.put("testSequence", "第" + MapUtil.getStr(scaleInfo, "times") + "次");
        reportMap.put("title", scaleEvaluationReportVO.getScaleName() + " --测评报告");
        reportMap.put("scaleType", MapUtil.getStr(scaleInfo, "dictName"));
        reportMap.put("testTime", scaleEvaluationReportVO.getCreateTime());
        reportMap.put("complexAnalysis", scaleEvaluationReportVO.getSynthesis());
        reportMap.put("factors", scaleEvaluationReportVO.getDivisor());
        if (scaleAnswerLogs != null && !scaleAnswerLogs.isEmpty()) {
        JSONArray answers = new JSONArray();
        scaleAnswerLogs.forEach(scaleAnswerLog -> {
        JSONObject projectAnswers = new JSONObject();
        projectAnswers.put("questionNumber", scaleAnswerLog.getQuestionNumber());
        List<ScaleEvaluationOptionDTO> scaleAnswerDTOList = JSONObject.parseArray(scaleAnswerLog.getAnswerOption(), ScaleEvaluationOptionDTO.class);
        String title = scaleAnswerDTOList.stream().map(answer -> answer.getTitle().substring(0, 1))
        .collect(Collectors.joining(" "));
        projectAnswers.put("answerOption", title);
        answers.add(projectAnswers);
        });
        reportMap.put("projectAnswers", answers);
        }
        //生成PDF文件
        Result<String> result = exportPdf(html, reportMap);
        //更新报告PDF地址
        pdfUrl = result.getData();
        if (StringUtils.isNotBlank(pdfUrl) && Objects.nonNull(logId)) {
        UmsScaleLog scaleLog = new UmsScaleLog();
        scaleLog.setLogId(logId);
        scaleLog.setPdfUrl(pdfUrl);
        scaleLogMapper.updateById(scaleLog);
        }
        return result;
        }



//导pdf
*/
/**
 * 导出pdf
 *
 * @param html      超文本标记语言
 * @param reportMap 报告图
 * @return {@link Result<String>}
 *//*

public Result<String> exportPdf(String html, Map<String, Object> reportMap) {
        String htmlString = PdfUtil.htmlString(html, reportMap);
        //生成PDF文件
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfUtil.htmlToPdf(htmlString, bos, fontPath, active);
        //上传到阿里OSS
        InputStream in = new ByteArrayInputStream(bos.toByteArray());
        MultipartFile file = FileUtil.getMulFile(in, "报告.pdf");
        //更新报告PDF地址
        return ossApi.upload(file);
        }



//工具类

*/
/**
 * pdf工具类
 *
 * @author dingping
 * @date 2020/09/21
 *//*

public class PdfUtil {
    */
/**
     * @param html      模板路径
     * @param paramsMap 填充数据
     * @return html字符串
     *//*

    public static String htmlString(String html, Map<String, Object> paramsMap) {
        //1 创建配置类
        Configuration conf = new Configuration(Configuration.VERSION_2_3_30);
        Template template = null;
        String htmlStr = "";
        try {
            // 2 设置模板所在目录
            conf.setClassForTemplateLoading(PdfUtil.class, "/templates");
            //3.设置字符集
            conf.setDefaultEncoding(FebsConstant.UTF8);
            //4. 加载模板
            template = conf.getTemplate(html);
            //第三步：处理模板及数据之间将数据与模板合成一个HTML
            //5. 创建数据
            paramsMap.forEach(paramsMap::put);
            //6. 创建writer对象
            StringWriter stringWriter = new StringWriter();
            BufferedWriter bw = new BufferedWriter(stringWriter);
            //7. 输出
            template.process(paramsMap, bw);
            htmlStr = stringWriter.toString();
            bw.flush();
            bw.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return htmlStr;
    }

    */
/**
     * @param html html字符串
     * @param out  输出流
     *//*

    public static void htmlToPdf(String html, OutputStream out, String fontPath, String active) {
        //设置字体
        FontProvider fontProvider = new FontProvider();
        if (FebsConstant.ENV_DEV.equals(active)) {
            fontProvider.addFont(PdfUtil.class.getResource("/font/").getPath() + FebsConstant.FONT);
        } else {
            fontProvider.addFont(fontPath);
        }
        ConverterProperties cp = new ConverterProperties();
        cp.setFontProvider(fontProvider);
        cp.setCharset(FebsConstant.UTF8);
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        List<IElement> iElements = null;
        try {
            iElements = HtmlConverter.convertToElements(html, cp);
            iElements.forEach(e -> {
                document.add((IBlockElement) e);
            });
        } finally {
            document.close();
        }
    }











    @RequestMapping("/htmlExportWord")
    @ResponseBody
    public  void htmlExportWord(HttpServletResponse response, Long id) {
        BaseContentEO entity = baseContentService.getEntity(BaseContentEO.class,id);
        String name="";
        if(!AppUtil.isEmpty(entity)){
            name=entity.getTitle();
        }
        String content = MongoUtil.queryById(id);
        try {
           // content += "</html>";
            byte b[] = content.getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            //输出文件
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +
                            new String( (name + ".doc").getBytes(),
                                    "iso-8859-1"));
            response.setContentType("application/msword");
            OutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
*/
