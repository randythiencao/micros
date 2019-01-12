package com.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.main.PG4Case;

public class ProcessorHook extends Thread {
	@Override
	public void run() {
		PG4Case.driver.close();
		PG4Case.driver.quit();
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			FileUtils.deleteDirectory(new File("temp"));
		} catch (IOException e) {
			System.out.println("error in manual termination");
			e.printStackTrace();
		}
		
	}
}
