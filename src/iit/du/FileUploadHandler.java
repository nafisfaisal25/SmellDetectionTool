package iit.du;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import zip.UnzipUtility;


/**
 * Servlet implementation class FileUploadHandler
 */
@WebServlet("/upload")
public class FileUploadHandler extends HttpServlet {
   // private final String UPLOAD_DIRECTORY = "C:/uploads";
 // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "C:\\uploads";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
  
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }
 
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = UPLOAD_DIRECTORY;
        
        
        // creates the directory if it does not exist
        
        
        
        
        File uploadDir = new File(uploadPath);
        if(uploadDir.exists())deleteFiles(uploadDir);
        if (!uploadDir.exists()) {
        	uploadDir.mkdir();
        }
        
        
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        
                        // saves the file on disk
                        item.write(storeFile);
                        
                        
                        String extractedFolderName=uploadPath + File.separator + "project"; 
                        
                        File folder=new File(extractedFolderName);
                        if(folder.exists())deleteFolder(folder);
                        
                        UnzipUtility u=new UnzipUtility();
                        u.unzip(filePath, extractedFolderName);
                        
                        
                        
                        
                        /*
                        request.setAttribute("message",
                            "Upload has been done successfully!");*/
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        }
        
        
        request.getRequestDispatcher("/option.html").forward(request, response);
     
    }
    
    private void deleteFiles(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFiles(f);
                } else {
                    f.delete();
                }
            }
        }
        
    }
    
    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                	deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
        
    }
    
  
}