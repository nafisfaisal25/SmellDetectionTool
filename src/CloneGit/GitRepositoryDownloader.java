package CloneGit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import copyFiles.CopyFiles;

public class GitRepositoryDownloader {
	
	
	public static void cloneProject(String repoUrl,String cloneDirectoryPath ) throws IOException {
		
		try {
		    //System.out.println("Cloning "+repoUrl+" into "+repoUrl);
		    Git.cloneRepository()
		        .setURI(repoUrl)
		        .setDirectory(new File(cloneDirectoryPath))
		        .call();
		    //System.out.println("Completed Cloning");
		} catch (GitAPIException e) {
		    //System.out.println("Exception occurred while cloning repo");
		    //e.printStackTrace();
		}
		
		CopyFiles.copyDirectory(new File(cloneDirectoryPath), new File("C:\\uploads\\project"));
	}
}	
	
