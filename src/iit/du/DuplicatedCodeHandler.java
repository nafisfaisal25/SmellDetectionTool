package iit.du;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DuplicatedCodeHandler
 */
@WebServlet("/duplicated_code")
public class DuplicatedCodeHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DuplicatedCodeHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=duplicated_code.csv");
        File file = new File("C:\\uploads\\DuplicatedCode.csv");
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[4096];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
        	out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
	}

}
