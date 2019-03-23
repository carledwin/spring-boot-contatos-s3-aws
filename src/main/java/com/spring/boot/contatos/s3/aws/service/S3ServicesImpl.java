package com.spring.boot.contatos.s3.aws.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.spring.boot.contatos.s3.aws.util.Utility;

@Service
public class S3ServicesImpl implements S3Services {

	private static Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${name.aws.s3.bucket}")
	private String bucketName;
	
	@Override
	public S3ObjectInputStream downloadFileS3(String fileName) {
		
		try {
			
			S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, fileName));
			
			if(s3Object != null) {
				
				logger.info("Download do arquivo efetuado com sucesso!");
				
				Utility.displayText(s3Object.getObjectContent());
				
				logger.info(s3Object.getKey());
				logger.info(s3Object.getObjectContent().toString());
				logger.info(s3Object.getRedirectLocation());
				logger.info(s3Object.getBucketName());
				
				if(s3Object.getObjectMetadata() != null) {
					logger.info(s3Object.getObjectMetadata().getContentType());
					logger.info(s3Object.getObjectMetadata().getVersionId());
				}
				
				return s3Object.getObjectContent();
			}else {
				logger.error("Falha ao tentar fazer o download do arquivo.");
				return null;
			}
			
		} catch (AmazonServiceException ase) {
			logger.error("ASE: ErrorCode: " + ase.getErrorCode());
			logger.error("ASE: ErrorMesssage: " + ase.getErrorMessage());
			logger.error("ASE: LocalizedMessage: " + ase.getLocalizedMessage());
			logger.error("ASE: Message: " + ase.getMessage());
			logger.error("ASE: ServiceName: " + ase.getServiceName());
			return null;
		} catch (AmazonClientException ace) {
			logger.error("ACE: LocalizedMessage: " + ace.getLocalizedMessage());
			logger.error("ACE: Message: " + ace.getMessage());
			return null;
		}catch (Exception ex) {
			logger.error("E: Message: " + ex.getMessage());
			logger.error("E: Cause: " + ex.getCause());
			logger.error("E: LocalizadMessage: " + ex.getLocalizedMessage());
			return null;
		}
	}
	
	@Override
	public ByteArrayOutputStream downloadFile(String fileName) {
		
		try {
			
			S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, fileName));
			
			if(s3Object != null) {
				
				logger.info("Download do arquivo efetuado com sucesso!");

				InputStream inputStream = s3Object.getObjectContent();
				
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				
				int len;
				
				byte[] buffer = new byte[4096];
				
				while((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
					byteArrayOutputStream.write(buffer, 0, len);
				}
				
				return byteArrayOutputStream;
				
			}else {
				logger.error("Falha ao tentar fazer o download do arquivo.");
				return null;
			}
			
		} catch (AmazonServiceException ase) {
			logger.error("ASE: ErrorCode: " + ase.getErrorCode());
			logger.error("ASE: ErrorMesssage: " + ase.getErrorMessage());
			logger.error("ASE: LocalizedMessage: " + ase.getLocalizedMessage());
			logger.error("ASE: Message: " + ase.getMessage());
			logger.error("ASE: ServiceName: " + ase.getServiceName());
			return null;
		} catch (AmazonClientException ace) {
			logger.error("ACE: LocalizedMessage: " + ace.getLocalizedMessage());
			logger.error("ACE: Message: " + ace.getMessage());
			return null;
		}catch (Exception ex) {
			logger.error("E: Message: " + ex.getMessage());
			logger.error("E: Cause: " + ex.getCause());
			logger.error("E: LocalizadMessage: " + ex.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public boolean uploadFile(String fileName, String uploadFilePath) {

		try {

			String pathFile = uploadFilePath + File.separator + fileName;
			
			File fileUpload = new File(pathFile);

			if(fileUpload.exists()) {
				
				logger.info("Arquivo " + pathFile + " encontrado! ");
				PutObjectResult putObjectResult = s3client.putObject(new PutObjectRequest(bucketName, fileName, fileUpload));
				
				if (putObjectResult != null) {
					System.out.println("Upload do arquivo: Name: "+ fileName+ " contentMD5: " + putObjectResult.getContentMd5() + " efetuado com sucesso!");
				}
				
				return true;
			}else {
				logger.error("Arquivo " + pathFile + " não encontrado! ");
				logger.error("Arquivo: " + pathFile);
				
				return false;
			}
			
		} catch (AmazonServiceException ase) {
			logger.error("ASE: ErrorCode: " + ase.getErrorCode());
			logger.error("ASE: ErrorMesssage: " + ase.getErrorMessage());
			logger.error("ASE: LocalizedMessage: " + ase.getLocalizedMessage());
			logger.error("ASE: Message: " + ase.getMessage());
			logger.error("ASE: ServiceName: " + ase.getServiceName());
		} catch (AmazonClientException ace) {
			logger.error("ACE: LocalizedMessage: " + ace.getLocalizedMessage());
			logger.error("ACE: Message: " + ace.getMessage());
		} catch (Exception ex) {
			logger.error("E: Message: " + ex.getMessage());
			logger.error("E: Cause: " + ex.getCause());
			logger.error("E: LocalizadMessage: " + ex.getLocalizedMessage());
		}

		return false;
	}

}
