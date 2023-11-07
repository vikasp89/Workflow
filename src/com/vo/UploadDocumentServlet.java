package com.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadDocumentServlet extends HttpServlet {
  private static Logger logger = LogManager.getLogger("consoleLogger");
  
  private static Logger loggerErr = LogManager.getLogger("consoleLogger");
  
  private ServletFileUpload uploader = null;
  
  public final String FILES_DIR = "FILES_DIR";
  
  private static final long serialVersionUID = 1L;
  
  public void init() throws ServletException {
    DiskFileItemFactory fileFactory = new DiskFileItemFactory();
    File filesDir = (File)getServletContext().getAttribute("FILES_DIR_FILE");
    fileFactory.setRepository(filesDir);
    this.uploader = new ServletFileUpload((FileItemFactory)fileFactory);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Inside UploadDocumentServlet of dopost method");
    if (request.getParameter("folderName") != null) {
      try {
        logger.debug("When click on download button");
        String fileName = request.getParameter("fileName");
        String folderName = request.getParameter("folderName");
        logger.debug("file going to download is::" + fileName);
        logger.debug("folderName::" + folderName);
        folderName = folderName.replace(",", File.separator);
        if (fileName == null || "".equals(fileName))
          throw new ServletException("File Name can't be null or empty"); 
        File file = new File(getServletContext().getAttribute("FILES_DIR") + File.separator + folderName + File.separator + fileName);
        if (!file.exists())
          throw new ServletException("File doesn't exists on server."); 
        logger.debug("File location on server::::" + file.getAbsolutePath());
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType((mimeType != null) ? mimeType : "application/octet-stream");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ServletOutputStream os = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read;
        while ((read = fis.read(bufferData)) != -1)
          os.write(bufferData, 0, read); 
        os.flush();
        os.close();
        fis.close();
        logger.debug("File downloaded at client successfully");
      } catch (Exception e) {
        loggerErr.error("Exception while downloading file: ", e);
      } 
    } else if ("GetFile".equalsIgnoreCase(request.getParameter("RequestType"))) {
      logger.debug("inside else if ");
      String files = "";
      PrintWriter out = response.getWriter();
      String sFolderId = request.getParameter("folderid");
      logger.debug("sFolderId::" + sFolderId);
      sFolderId = sFolderId.replace(",", File.separator);
      File folder = new File(getServletContext().getAttribute("FILES_DIR") + File.separator + sFolderId);
      logger.debug("folder::" + folder);
      if (folder.isDirectory()) {
        logger.debug("isDirectory::");
        if ((folder.list()).length > 0) {
          File[] listOfFiles = folder.listFiles();
          logger.debug("listOfFiles length::" + listOfFiles.length);
          for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
              files = String.valueOf(files) + "," + listOfFiles[i].getName();
            } else if (listOfFiles[i].isDirectory()) {
              files = String.valueOf(files) + "," + listOfFiles[i].getName();
            } 
          } 
        } else {
          files = "Invalid";
          logger.debug("Directory is empty!");
        } 
      } else {
        files = "Invalid";
        logger.debug("This is not a directory");
      } 
      out.println(files);
    } else if ("DeleteFile".equalsIgnoreCase(request.getParameter("RequestType"))) {
      logger.debug("DeleteFile");
      PrintWriter out = response.getWriter();
      String sFolderId = request.getParameter("foldername");
      String sFilename = request.getParameter("filename");
      File file = new File(getServletContext().getAttribute("FILES_DIR") + File.separator + sFolderId + File.separator + sFilename);
      logger.debug("file::" + file);
      if (file.delete()) {
        out.println("File deleted successfully");
      } else {
        out.println("Failed to delete the file");
      } 
    } else {
      try {
        logger.debug("When click on Attach document button");
        PrintWriter out = response.getWriter();
        if (!ServletFileUpload.isMultipartContent(request))
          throw new ServletException("Content type is not multipart/form-data"); 
        response.setContentType("text/html");
        uploadfiles(out, request);
      } catch (Exception e) {
        loggerErr.error("Exception while uploading file: ", e);
      } 
    } 
  }
  
  private void uploadfiles(PrintWriter out, HttpServletRequest request) {
    try {
      String folderid = "";
      String folderName = "";
      List<FileItem> fileItemsList = this.uploader.parseRequest(request);
      Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
      FileItem fileItem = null;
      while (fileItemsIterator.hasNext()) {
        fileItem = fileItemsIterator.next();
        logger.debug("check field name::::" + fileItem.getFieldName());
        if (!fileItem.isFormField()) {
          logger.debug("FileName::::" + fileItem.getName());
          logger.debug("ContentType::::" + fileItem.getContentType());
          logger.debug("Size in bytes::::" + fileItem.getSize());
          continue;
        } 
        if ("folderid".equals(fileItem.getFieldName())) {
          folderid = fileItem.getString();
          logger.debug("folderid::::" + folderid);
          folderName = folderid.replace(",", File.separator);
        } 
      } 
      File file = new File(getServletContext().getAttribute("FILES_DIR") + File.separator + folderName);
      if (!file.exists())
        file.mkdirs(); 
      File file1 = new File(getServletContext().getAttribute("FILES_DIR") + File.separator + folderName + File.separator + fileItem.getName());
      if (file1.exists()) {
        out.println("Duplicate File");
      } else {
        fileItem.write(file1);
        out.println(folderid);
      } 
    } catch (FileUploadException e) {
      loggerErr.error("FileUploadException: ", (Throwable)e);
      out.write("Exception in uploading file.");
    } catch (Exception e) {
      loggerErr.error("Exception in FileLocationContextListener: ", e);
      out.write("Exception in uploading file.");
    } 
  }
}
