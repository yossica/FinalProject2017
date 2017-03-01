package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ExportReportManager {
	public static String exportToPdf(String filePath, String fileName,
			Map<String, Object> parameters, List input) {
		JasperReport jasperReport = null;
		try {
			jasperReport = JasperCompileManager.compileReport(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(
				input);
		parameters.put("ItemDataSource", itemsJRBean);

		JasperPrint jasperPrint = null;

		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, new JREmptyDataSource());
		} catch (Exception e) {
			System.out.println("error print");
			e.printStackTrace();
		}

		String path = "";
		try {
			File outDir = new File("D://");
			outDir.mkdirs();
			path = outDir.getAbsolutePath() + "\\" + fileName;
			String compare = path.toLowerCase();
			if (!compare.endsWith(".pdf"))
				path += ".pdf";

			OutputStream outputStream = new FileOutputStream(new File(path));

			JasperExportManager.exportReportToPdfStream(jasperPrint,
					outputStream);
			
			outputStream.close();
		} catch (Exception e) {
			System.out.println("error export");
			e.printStackTrace();
		}
		
		//for download file
		return path;
	}
	
	public static void downloadFile(HttpServletResponse response, String resultServerPath, String fileName) throws IOException{
		//download to client
		//https://coderanch.com/t/293523/java/Download-file-Server-client-machine
		response.setContentType("application/octet-stream");
		String disHeader = "Attachment; Filename=\""+fileName+"\"";
		response.setHeader("Content-Disposition", disHeader);

		InputStream in = null;
		ServletOutputStream outs = response.getOutputStream();

		try {
			in = new BufferedInputStream(new FileInputStream(
					resultServerPath));
			int ch;
			while ((ch = in.read()) != -1) {
				outs.print((char) ch);
			}
		} finally {
			if(outs != null){
				outs.flush();
			}
			if (in != null)
				in.close(); // very important
		}

		response.setContentType("text/javascript");
		//download until here
		//hapus file dari server
		new File(resultServerPath).delete();
	}
}
