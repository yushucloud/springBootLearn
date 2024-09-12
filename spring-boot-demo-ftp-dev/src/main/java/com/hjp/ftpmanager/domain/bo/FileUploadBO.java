package com.hjp.ftpmanager.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: cms-server
 * @description: 文件上传至ftpBO
 * @author: jumper Hou
 * @create: 2022/9/28 9:06
 **/
@Data
public class FileUploadBO implements Serializable {
    private static final long serialVersionUID = -4063042178426727513L;
    /**
     * 内容id
     */
    private String contentId;
    /**
     * 工单id
     */
    private String worksheetId;
    /**
     * 文件名->要保存的文件名
     */
    private String fileName;
}
