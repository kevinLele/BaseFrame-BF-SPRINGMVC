package com.hq.cloudplatform.baseframe.entity;

/**
 * 分片上传的文件信息
 *
 * @author Administrator
 */
public class ChunkFileInfo {

    private String fileName;

    private String fileMd5;

    private String fileSize;

    private String chunkNum;

    private Long chunkSize;

    private String fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getChunkNum() {
        return chunkNum;
    }

    public void setChunkNum(String chunkNum) {
        this.chunkNum = chunkNum;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
