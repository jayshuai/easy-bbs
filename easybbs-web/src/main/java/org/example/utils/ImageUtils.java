package org.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.StrUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.example.entity.config.AppConfig;
import org.example.entity.constants.Constants;
import org.example.entity.enums.DateTimePatterEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("ImageUtils")
public class ImageUtils {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
    @Resource
    private AppConfig appConfig;

    /**
     * 文件压缩--得到File
     *
     * @param tempPath      临时文件目录
     * @param multipartFile 要压缩的文件
     * @param width         压缩后的宽
     * @param height        压缩后的高
     * @return java.io.File
     */
    public File compressFile(String tempPath, MultipartFile multipartFile, int width, int height) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        //确保此路径下有 temp.jpg文件
        File file = new File(tempPath + "." + suffix);
        Thumbnails.of(multipartFile.getInputStream()).size(width, height).toFile(file);
        return file;
    }

    /**
     * 文件压缩--转成MultipartFile
     *
     * @param tempPath      临时文件目录
     * @param multipartFile 要压缩的文件
     * @param width         压缩后的宽
     * @param height        压缩后的高
     * @return org.springframework.web.multipart.MultipartFile
     */
    public MultipartFile compressMultipart(String tempPath, MultipartFile multipartFile, int width, int height) throws IOException {
        File file = compressFile(tempPath, multipartFile, width, height);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
    }

    public String resetImageHtml(String html) {
        String month = DateUtil.format(new Date(), DateTimePatterEnum.YYYY_MM.getPatter());
        List<String> imageList = getImageList(html);
        for (String s : imageList) {
            resetImage(s, month);
        }
        return month;
    }

    private String resetImage(String imagePath, String month) {
        if (StrUtil.isEmpty(imagePath) || imagePath.contains(Constants.FILE_FOLDER_TEMP2)) {
            return imagePath;
        }
        imagePath = imagePath.replace(Constants.READ_IMAGE_PATH, "");
        String imageFileName = month + "/" + imagePath.substring(imagePath.lastIndexOf("/") + 1);
        File targetFile = new File(appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_IMAGE + imageFileName);
        try {
            File file = new File(appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + imagePath);
            FileUtil.copyFile(new File(appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + imagePath), targetFile);
            file.delete();
        } catch (Exception e) {
            logger.error("文件替换有误", e);
            e.printStackTrace();
            return imagePath;
        }
        return imageFileName;
    }

    public List<String> getImageList(String html) {
        List<String> imageList = new ArrayList<>();
        String regEx_ima = "(<img.*src\\s*(.*?)[^>]*?>)";
        Pattern p_image = Pattern.compile(regEx_ima, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(html);
        while (m_image.find()) {
            String img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String imageUrl = m.group(1);
                imageList.add(imageUrl);
            }
        }
        return imageList;
    }
}
