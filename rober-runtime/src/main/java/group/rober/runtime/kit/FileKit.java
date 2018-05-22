package group.rober.runtime.kit;

import group.rober.runtime.lang.RoberException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * 文件操作工具类
 * Created by tisir<yangsong158@qq.com> on 2017-02-19
 */
public abstract class FileKit {
    private static DecimalFormat decimalFormat=(DecimalFormat) NumberFormat.getInstance();
    static{
        decimalFormat.setMaximumFractionDigits(2);
    }

    private static Logger logger = LoggerFactory.getLogger(FileKit.class);

    /**
     * 把文件路径处理成为标准文件路径,都用"/"作为路径分割符
     * @param path
     * @return
     */
    public static String standardFilePath(String path){
        if(path!=null){
            return path.replaceAll("\\\\", "/");
        }else{
            return path;
        }
    }

    /**
     * 如果文件不存在，则创建新文件
     * @param file
     * @throws IOException
     */
    public static void touchFile(String file) throws IOException {
        touchFile(new File(file));
    }
    /**
     * 如果文件不存在，则创建新文件
     * @param file
     * @throws IOException
     */
    public static void touchFile(File file) throws IOException {
        if(!file.exists()){
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
    }

    public static long getFileSize(File file){
        FileChannel channel = null;
        if(file.exists() && file.isFile()){
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                channel = inputStream.getChannel();
                return channel.size();
            } catch (FileNotFoundException e) {
                throw new RoberException("读取文件大小出错，文件:[{0}]不存在",file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("读取文件出错");
            }finally{
                IOKit.close(inputStream);
                IOKit.close(channel);
            }
        }
        return 0L;
    }

    public static boolean deleteFile(File file){
        String fileFullPath;
        try {
            fileFullPath = file.getCanonicalPath();
        } catch (IOException e) {
            fileFullPath = e.getMessage();
        }

        boolean r = file.delete();
        logger.trace(MessageFormat.format("文件删除,【{0}】，删除结果【{1}】",fileFullPath,r));
        return r;
    }

    public static InputStream openInputStream(File file) throws IOException{
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw e;
        }

        return inputStream;
    }

    public static OutputStream openOutputStream(File file,boolean autoCreate) throws IOException{
        OutputStream outputStream = null;

        if(autoCreate&&!file.exists()){
            File parent = file.getParentFile();
            if(!parent.exists())parent.mkdirs();
            file.createNewFile();
        }
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw e;
        }

        return outputStream;
    }

    public static OutputStream openOutputStream(File file) throws IOException{
        return openOutputStream(file,true);
    }

    /**
     * 文件复制
     * @param src
     * @param dsc
     * @param autoCreateDscDir
     * @throws IOException
     */
    public static void copy(File src,File dsc,boolean autoCreateDscDir) throws IOException{
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcIn = null;
        FileChannel fcOut = null;

        try {
            logger.trace(MessageFormat.format("文件复制,【{0}】-->【{1}】", src.getCanonicalPath(),dsc.getCanonicalPath()));
            if(autoCreateDscDir){
                File parentFile = dsc.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
            }
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dsc);
            fcIn = fis.getChannel();
            fcOut = fos.getChannel();

            fcIn.transferTo(0, fcIn.size(), fcOut);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            IOKit.close(fis);
            IOKit.close(fcIn);
            IOKit.close(fos);
            IOKit.close(fcOut);
        }
    }

    public  static String convertSizeText(Long size) {
        if (size == null || size == 0) return "0K";
        String text = "";
        Double size1 = size.doubleValue();
        text = decimalFormat.format(size1) + "B";
        int mulNumber = 1024;
        if (size1 > mulNumber) {
            size1 = size1 / mulNumber;
            text = decimalFormat.format(size1) + "K";
        }
        if (size1 > mulNumber) {
            size1 = size1 / mulNumber;
            text = decimalFormat.format(size1) + "M";
        }
        if (size1 > mulNumber) {
            size1 = size1 / mulNumber;
            text = decimalFormat.format(size1) + "G";
        }
        return text;
    }

    /**
     * 获取文件的扩展名
     * @param file
     * @return
     */
    public static String getSuffix(String file){
        String suffix = "";
        if(StringUtils.isBlank(file)) return suffix;
        String[] ss = file.split("\\.");
        if(ss.length>1){
            suffix = ss[ss.length-1];
        }
        return suffix;
    }
}
