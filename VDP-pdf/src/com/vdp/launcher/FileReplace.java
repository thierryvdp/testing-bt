package com.vdp.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;


public class FileReplace {

	public static void main(String[] args) {
		try {
			
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("path.txt");
			prop.load(in);
			in.close();
			// Extraction des propriétés
			String pathOfficiel = prop.getProperty("pathOfficiel");
			String pathModif    = prop.getProperty("pathModif");
			String pathBack     = prop.getProperty("pathBack");
			String lastS        = prop.getProperty("last");
			long   lastN        = Long.parseLong(lastS);
			
			File fOfficiel = new File(pathOfficiel);
			File fModif    = new File(pathModif);
			File fBack     = new File(pathBack);

			check(fOfficiel);
			check(fModif);
			check(fBack);
			
			boolean errors=false;
			
			for(File file:fOfficiel.listFiles()) {
				System.out.println("file="+file.getAbsolutePath()+" modified="+file.lastModified()+" old:"+getNumFactureOld(file.getName()));
			}
			for(File file:fModif.listFiles()) {
				System.out.println("file="+file.getAbsolutePath()+" modified="+file.lastModified()+" old:"+getNumFactureOld(file.getName())+" new:"+getNumFactureNew(file.getName()));
			}
			
			
			prop.setProperty("last",String.valueOf(lastN)) ;
			FileOutputStream out = new FileOutputStream("path.txt") ;
			prop.store(out,"test");
			out.close();
			
		} catch (Exception e) {
			System.out.println("Grosse erreur "+e.toString()+" on recommence.");
			for(StackTraceElement stack:e.getStackTrace()) {
				System.out.println(stack.toString());
			}
			System.exit(3);
		}
		System.exit(0);
	}
	
	private static void check(File file) {
		if(!file.exists()) {
			System.out.println("directory="+file.getAbsolutePath()+" doesn't exists.");
			System.exit(1);
		}
		if(!file.isDirectory()) {
			System.out.println("directory="+file.getAbsolutePath()+" is not a directory.");
			System.exit(2);
		}
	}
	
	private static long getNumFactureOld(String shortFileName) {
		long num=0;
		try {
			String[] pieces=shortFileName.split("[-/.]");
			return Long.parseLong(pieces[1]);
		} catch (Exception e) {
		}
		return num;
	}
	private static long getNumFactureNew(String shortFileName) {
		long num=0;
		try {
			String[] pieces=shortFileName.split("[-/.]");
			return Long.parseLong(pieces[2]);
		} catch (Exception e) {
		}
		return num;
	}
	
}
