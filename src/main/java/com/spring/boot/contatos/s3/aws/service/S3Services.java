package com.spring.boot.contatos.s3.aws.service;

import java.io.ByteArrayOutputStream;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public interface S3Services {

	S3ObjectInputStream downloadFileS3(String fileName);
	ByteArrayOutputStream downloadFile(String fileName);
	boolean uploadFile(String fileName, String uploadFilePath);
}
