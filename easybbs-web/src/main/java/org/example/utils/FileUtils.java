package org.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import org.example.entity.config.AppConfig;
import org.example.entity.constants.Constants;
import org.example.entity.dto.FileUploadDto;
import org.example.entity.enums.DateTimePatterEnum;
import org.example.entity.enums.FileUploadTypeEnum;
import org.example.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

@Component
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    @Resource
    private AppConfig appConfig;

    public FileUploadDto uploadFile2Local(MultipartFile file, String folder, FileUploadTypeEnum uploadTypeEnum) {
        try {
            FileUploadDto fileUploadDto = new FileUploadDto();
            String originalFilename = file.getOriginalFilename();
            String fileSuffix = FileUtil.getSuffix(originalFilename);
            if (originalFilename.length() > Constants.LENGTH_15) {
                originalFilename = StringTools.getFileNameNoSuffix(originalFilename).substring(0, Constants.LENGTH_10) + fileSuffix;
            }
            if (ArrayUtil.contains(uploadTypeEnum.getSuffixArray(), fileSuffix)) {
                throw new BusinessException("文件类型不正确");
            }
            String month = DateUtil.format(new Date(), DateTimePatterEnum.YYYY_MM.getPatter());
            String baseFolder = appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFolder + folder + month + "/");
            String fileName = StringTools.getRandomString(Constants.LENGTH_15) + "." + fileSuffix;
            File targetFile = new File(targetFileFolder.getPath() + "/" + fileName);
            String localPath = month + "/" + fileName;
            if (uploadTypeEnum == FileUploadTypeEnum.AVATAR) {
                //TODO  头像上传
                targetFileFolder = new File(baseFolder + Constants.FILE_FOLDER_AVTAR_NAME);
                targetFile = new File(targetFileFolder.getPath() + folder + Constants.AVATAR_SUFFIX);
                localPath = folder + Constants.AVATAR_SUFFIX;
            }
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            file.transferTo(targetFile);
            //压缩图片
//            if (uploadTypeEnum == FileUploadTypeEnum.COMMENT_IMAGE) {
//                String thumbnailName = targetFile.getName().replace(".", "_.");
//                File thumbnail = new File(targetFile.getParent() + "/" + thumbnailName);
//                ImageUtils.compressFile(targetFile.getParent(), file, 200, 200);
//            }
//            else {
//
//            }
            fileUploadDto.setLocalPath(localPath);
            fileUploadDto.setOriginalFileName(originalFilename);
            return fileUploadDto;
        } catch (BusinessException e) {
            logger.error("文件上传失败", e);
            throw e;
        } catch (Exception e) {
            logger.error("文件上传失败", e);
            throw new BusinessException("上传文件失败");
        }
    }
}
