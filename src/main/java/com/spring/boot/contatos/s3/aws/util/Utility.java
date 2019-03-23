package com.spring.boot.contatos.s3.aws.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class Utility {

	private static Logger logger = LoggerFactory.getLogger(Utility.class);
	
	public static void displayText(S3ObjectInputStream s3ObjectInputStream) {
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3ObjectInputStream));  
		
		while(true) {
			
			String line = null;
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
			logger.error("Falha ao tentar fazer upload do arquivo. \n Cause: " + e.getCause());
			}
			
			if(line == null) break;
			
			logger.info("\t " + line);
			
		}
	}
}
