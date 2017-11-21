package com.ghz.flow.base.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**文件上传工具类
 * Created by admi on 2017/6/22.
 */

public class upFile {
    /**
     * 将文件地址写入数据库
     * 将文件复制到硬盘
     * @param resource
     */



    public static  String  copyFile(MultipartFile resource, String name) throws IOException {
        SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd");
        String format = dateFormat.format(new Date());
        File filepath=new File("e://myuploadFile/"+name+"/"+format);
        System.out.println(filepath);
        if(!filepath.exists()){
            filepath.mkdirs();
        }
        File file=new File(filepath+"//"+ UUID.randomUUID());
        InputStream inputStream = resource.getInputStream();
        FileUtils.copyInputStreamToFile(inputStream,file);
        System.out.println(filepath.getPath());
        return file.getPath();
    }
     public static HttpServletResponse upLoadFile(String docPath,HttpServletResponse response)throws  Exception{
         File file =new File(docPath);
         InputStream inputStream=new FileInputStream(file);
         OutputStream outputStream=response.getOutputStream();
         byte[] bytes=new byte[1024];
         int b=0;
         while((b=inputStream.read(bytes))!=-1){
             outputStream.write(bytes,0,b);
         }
         return response;
     }



     public static void main(String[] args) throws IOException {

     }
}
