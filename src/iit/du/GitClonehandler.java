package iit.du;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CloneGit.GitRepositoryDownloader;
import ExploreFiles.Main;

/**
 * Servlet implementation class GitClonehandler
 */
@WebServlet("/git_clone")
public class GitClonehandler extends HttpServlet {
	private static final long serialVersionUID = 1L;   
	private static final String UPLOAD_DIRECTORY = "C:\\uploads";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GitClonehandler() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uploadPath = UPLOAD_DIRECTORY;
	    File uploadDir = new File(uploadPath);
	    if(uploadDir.exists())deleteFiles(uploadDir);
	    if (!uploadDir.exists()) {
	    	uploadDir.mkdir();
	    }
		
		try {
			String url=request.getParameter("link");

	        
	        File folder=new File("C:\\uploads\\project");
	        if(folder.exists())deleteFolder(folder);
			
	        String path= new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

			String FolderName="D:\\uploads"+File.separator + path; 

			GitRepositoryDownloader.cloneProject(url,FolderName);
			
			Main.main();
			
		} catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        }
		
        request.getRequestDispatcher("/option.html").forward(request, response);

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
	

}
