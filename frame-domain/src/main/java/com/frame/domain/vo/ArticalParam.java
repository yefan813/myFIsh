package com.frame.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yefan on 2017/7/18.
 */
@ApiModel(value = "ArticalParam")
public class ArticalParam implements Serializable {

    @ApiModelProperty(value = "文字")
    private List<String> contents;

    @ApiModelProperty(value = "图片")
    private List<MultipartFile> files;


    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
