package com.openai.openaimaven.Entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class UploadedFiles extends BaseEntity{
    private String fileId;
    private String purpose;
    private String filename;
    private String bytes;
    private String uploadedOnOpenAi;
    private String status;
    private String remarks;
    private String additionalInformation;
}
