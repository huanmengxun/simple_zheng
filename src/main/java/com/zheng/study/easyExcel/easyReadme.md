# JAVA解析Excel工具easyexcel
## 二方包 
--从网上下载

	 	<dependency>
		    <groupId>com.alibaba</groupId>
		    <artifactId>easyexcel</artifactId>
		    <version>{latestVersion}</version>
		</dependency>
## 快速开始
### 读Excel
测试代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/ReadTest.java](/src/test/java/com/alibaba/easyexcel/test/ReadTest.java)

读07版小于1000行数据返回 List<List<String>>  

```
	List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
```
读07版小于1000行数据返回List<? extend BaseRowModel>

```
List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(2, 1,JavaModel.class));
```
读07版大于1000行数据返回List<List<String>>

```
ExcelListener excelListener = new ExcelListener();
EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1), excelListener);
```

读07版大于1000行数据返回List<? extend BaseRowModel>

```
ExcelListener excelListener = new ExcelListener();
EasyExcelFactory.readBySax(inputStream, new Sheet(2, 1,JavaModel.class), excelListener);
```
读03版方法同上
### 写Excel
测试代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/WriteTest.java](/src/test/java/com/alibaba/easyexcel/test/WriteTest.java)
没有模板

```
OutputStream out = new FileOutputStream("/Users/jipengfei/2007.xlsx");
ExcelWriter writer = EasyExcelFactory.getWriter(out);
//写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
Sheet sheet1 = new Sheet(1, 3);
sheet1.setSheetName("第一个sheet");
//设置列宽 设置每列的宽度
Map columnWidth = new HashMap();
columnWidth.put(0,10000);columnWidth.put(1,40000);columnWidth.put(2,10000);columnWidth.put(3,10000);
sheet1.setColumnWidthMap(columnWidth);
sheet1.setHead(createTestListStringHead());
//or 设置自适应宽度
//sheet1.setAutoWidth(Boolean.TRUE);
writer.write1(createTestListObject(), sheet1);
//写第二个sheet sheet2  模型上打有表头的注解，合并单元格
Sheet sheet2 = new Sheet(2, 3, JavaModel1.class, "第二个sheet", null);
sheet2.setTableStyle(createTableStyle());
writer.write(createTestListJavaMode(), sheet2);
//写第三个sheet包含多个table情况
Sheet sheet3 = new Sheet(3, 0);
sheet3.setSheetName("第三个sheet");
Table table1 = new Table(1);
table1.setHead(createTestListStringHead());
writer.write1(createTestListObject(), sheet3, table1);
//写sheet2  模型上打有表头的注解
Table table2 = new Table(2);
table2.setTableStyle(createTableStyle());
table2.setClazz(JavaModel1.class);
writer.write(createTestListJavaMode(), sheet3, table2);
//关闭资源
writer.finish();
out.close();
```

有模板

```
	InputStream inputStream = new BufferedInputStream(new FileInputStream("/Users/jipengfei/temp.xlsx"));
	OutputStream out = new FileOutputStream("/Users/jipengfei/2007.xlsx");
	ExcelWriter writer = EasyExcelFactory.getWriterWithTemp(inputStream,out,ExcelTypeEnum.XLSX,true);
	//写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
	Sheet sheet1 = new Sheet(1, 3);
	sheet1.setSheetName("第一个sheet");
	//设置列宽 设置每列的宽度
	Map columnWidth = new HashMap();
	columnWidth.put(0,10000);columnWidth.put(1,40000);columnWidth.put(2,10000);columnWidth.put(3,10000);
	sheet1.setColumnWidthMap(columnWidth);
	sheet1.setHead(createTestListStringHead());
	//or 设置自适应宽度
	//sheet1.setAutoWidth(Boolean.TRUE);
	writer.write1(createTestListObject(), sheet1);
	//写第二个sheet sheet2  模型上打有表头的注解，合并单元格
	Sheet sheet2 = new Sheet(2, 3, JavaModel1.class, "第二个sheet", null);
	sheet2.setTableStyle(createTableStyle());
	writer.write(createTestListJavaMode(), sheet2);
	//写第三个sheet包含多个table情况
	Sheet sheet3 = new Sheet(3, 0);
	sheet3.setSheetName("第三个sheet");
	Table table1 = new Table(1);
	table1.setHead(createTestListStringHead());
	writer.write1(createTestListObject(), sheet3, table1);
	//写sheet2  模型上打有表头的注解
	Table table2 = new Table(2);
	table2.setTableStyle(createTableStyle());
	table2.setClazz(JavaModel1.class);
	writer.write(createTestListJavaMode(), sheet3, table2);
	//关闭资源
	writer.finish();
	out.close();
```

### web下载实例写法
```
public class Down {
    @GetMapping("/a.htm")
    public void cooperation(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = response.getOutputStream();
         response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .getBytes(), "UTF-8");
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(getListString(), sheet1);
        writer.finish();
      
        out.flush();
        }
    }
}
