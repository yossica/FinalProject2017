package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ExportReportManager {
	public static void exportToPdf(String filePath, String fileName, Map<String,Object> parameters, List input){
		JasperReport jasperReport = null;
		try {
			jasperReport = JasperCompileManager.compileReport(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(input);
		parameters.put("ItemDataSource",itemsJRBean);
		
		JasperPrint jasperPrint = null;
		
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
		} catch (Exception e) {
			System.out.println("error print");
			e.printStackTrace();
		}
		
		//JFileChooser jfc = new JFileChooser();
		//jfc.setAcceptAllFileFilterUsed(false);
		//jfc.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
		
		try {
			File outDir = new File("D://Finance Solution Reports/");
			outDir.mkdirs();
			String path = outDir.getAbsolutePath()+"\\"+fileName;
			String compare = path.toLowerCase();
			if(!compare.endsWith(".pdf"))
				path+=".pdf";
				
			OutputStream outputStream = new FileOutputStream(new File(path));
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			
		} catch (Exception e) {
			System.out.println("error export");
			e.printStackTrace();
		}
	}
}
