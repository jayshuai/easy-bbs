package org.example.controller;


import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.annotation.GlobalInterceptor;
import org.example.entity.config.WebConfig;
import org.example.entity.constants.Constants;
import org.example.entity.enums.ResponseCodeEnum;
import org.example.entity.vo.ResponseVO;
import org.example.exception.BusinessException;
import org.example.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("file")
public class FileController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Resource
    private WebConfig webConfig;

    @RequestMapping("uploadImage")
    public ResponseVO uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExName = FileUtil.getSuffix(fileName);
        if (!Constants.IMAGE_SUFFIX.contains(fileExName)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String path = copyFile(file);
        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("fileName", path);
        return getSuccessResponseVO(fileMap);
    }

    private String copyFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String fileExName = FileUtil.getSuffix(fileName);
            String fileRealName = StringTools.getRandomString(10) + "." + fileExName;
            String folderPath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_TEMP;
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File uploadFile = new File(folderPath + "/" + fileRealName);
            file.transferTo(uploadFile);
            return Constants.FILE_FOLDER_TEMP2 + "/" + fileRealName;
        } catch (Exception e) {
            logger.error("上传文件失败", e);
            throw new BusinessException("上传文件失败");
        }
    }

    @RequestMapping("getImage/{imageFolder}/{imageName}")
    public void getImage(HttpServletResponse response, @PathVariable String imageFolder, @PathVariable String imageName) {
        readImage(response, imageFolder, imageName);
    }

    @RequestMapping("getAvatar/{userId}")
    public void getAvatar(HttpServletResponse response, @PathVariable String userId) {
        String avatarFolderName = Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_AVTAR_NAME;
        String avatarPath = webConfig.getProjectFolder() + avatarFolderName + userId + Constants.AVATAR_SUFFIX;
        File fileFolder = new File(avatarFolderName);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        File file = new File(avatarPath);
        String imageName = userId + Constants.AVATAR_SUFFIX;
        if (!file.exists()) {
            imageName = Constants.AVATAR_DEFAULT;
        }
        readImage(response, Constants.FILE_FOLDER_AVTAR_NAME, imageName);
    }

    private void readImage(HttpServletResponse response, String imageFolder, String imageName) {
        ServletOutputStream sos = null;
        FileInputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            if (StringTools.isEmpty(imageFolder) || StringUtils.isBlank(imageName)) {
                return;
            }
            String imageSuffix = StringTools.getFileSuffix(imageName);
            File file = getFile(imageFolder, imageName);
            if (!file.exists()) {
                return;
            }
            imageSuffix = imageSuffix.replace(".", "");
            if (Constants.FILE_FOLDER_AVTAR_NAME.equals(imageFolder)) {
                response.setHeader("Cache-Control", "max-age=2592000");
            }
            response.setContentType("image/" + imageSuffix);
            in = new FileInputStream(file);
            sos = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = in.read())) {
                baos.write(ch);
            }
            sos.write(baos.toByteArray());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    logger.error("IO异常", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("IO异常", e);
                }
            }
        }

    }

    private File getFile(String imageFolder, String imageName) {
        String filePath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_IMAGE + imageFolder + "/" + imageName;
        if (Constants.FILE_FOLDER_TEMP2.equals(imageFolder)) {
            filePath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + imageFolder + "/" + imageName;
        } else if (imageFolder.contains(Constants.FILE_FOLDER_AVTAR_NAME)) {
            filePath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + imageFolder + imageFolder;
        }
        return new File(filePath);
    }
}
