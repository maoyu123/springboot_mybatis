package com.my.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtil {
        public static void createFile(HttpServletResponse response, HSSFWorkbook workbook) {
            // 设置文件名
            String fileName ="用户信息";
            try {
                // 捕获内存缓冲区的数据，转换成字节数组
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workbook.write(out);
                // 获取内存缓冲中的数据
                byte[] content = out.toByteArray();
                // 将字节数组转化为输入流
                InputStream in = new ByteArrayInputStream(content);
                //通过调用reset（）方法可以重新定位。
                response.reset();
                // 如果文件名是英文名不需要加编码格式，如果是中文名需要添加"iso-8859-1"防止乱码
                response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
                response.addHeader("Content-Length", "" + content.length);
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                ServletOutputStream outputStream = response.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(in);
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                byte[] buff = new byte[8192];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bis.close();
                bos.close();
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }
}

