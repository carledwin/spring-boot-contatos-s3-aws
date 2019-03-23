package com.spring.boot.contatos.s3.aws.restcontroller;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.spring.boot.contatos.s3.aws.model.FileS3;
import com.spring.boot.contatos.s3.aws.service.S3Services;

@RestController
public class S3RestController {

	private static Logger logger = LoggerFactory.getLogger(S3RestController.class);
	
	@Autowired
	private S3Services s3Services;
	
	@Value("${path.upload.files}")
	private String pathUploadFiles;
	
	@Value("${file.upload}")
	private String fileUpload;
	
	@Value("${file.download}")
	private String download;

	@GetMapping("/s3")
	private String s3(){
		return "Bem vindo ao sistema de upload S3 AWS";
	}

	
	@PostMapping("/upload")
	private ResponseEntity<?> upload(@RequestBody(required = true) FileS3 fileS3){
		
		logger.info("Iniciando upload...");
		boolean uploadFileSuccess = s3Services.uploadFile(fileS3.getFileName(), fileS3.getUploadFilePath());
		
		if(uploadFileSuccess) {
			
			logger.info("Upload efetuado com sucesso!");
			return new ResponseEntity<String>("Sucesso ao salvar o arquivo: " + fileS3,HttpStatus.CREATED); 
		}else {
			return new ResponseEntity<String>("Falha ao tentar fazer upload do arquivo: " + fileS3, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/download/{fileName}")
	private ResponseEntity<?> download(@PathVariable("fileName") String fileName){
		
		logger.info("Iniciando download...");
		
		ByteArrayOutputStream byteArrayOutputStream = s3Services.downloadFile(fileName);
		
		if(byteArrayOutputStream != null) {
			
			logger.info("Download do arquivo " + fileName +" efetuado com sucesso!");
			return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), HttpStatus.OK); 
		}else {
			return new ResponseEntity<String>("Falha ao tentar fazer upload do arquivo: " + fileName, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
