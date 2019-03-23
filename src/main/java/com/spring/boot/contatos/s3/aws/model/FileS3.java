package com.spring.boot.contatos.s3.aws.model;

import com.amazonaws.services.rekognition.model.S3Object;

public class FileS3 {

	private String fileName; 
	private String uploadFilePath;
	private S3Object s3Object;
	
	public FileS3() {
		super();
	}
	
	public FileS3(String fileName, String uploadFilePath, S3Object s3Object) {
		super();
		this.fileName = fileName;
		this.uploadFilePath = uploadFilePath;
		this.setS3Object(s3Object);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public S3Object getS3Object() {
		return s3Object;
	}

	public void setS3Object(S3Object s3Object) {
		this.s3Object = s3Object;
	}
	
}
