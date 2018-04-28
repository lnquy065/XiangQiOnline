package net.codejava.servlet;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024, // 1MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UploadServlet extends HttpServlet {

    /**
     * Name of the directory where uploaded files will be saved, relative to the
     * web application directory.
     */
    private static final String SAVE_DIR = "avatars";

    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        System.out.println("AppPath = " + appPath);
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
        //System.out.println("Name = "+ request.getParameter("test")); 
        // creates the save directory if it does not exists
        System.out.println("SavePath = "+ savePath);
        HttpSession ss = request.getSession(false);
        String name = (String) ss.getAttribute("loginName");
        System.out.println("Name : " + name);
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            String[] temp = fileName.split(Pattern.quote("."));
            String newname = name.trim() + "." + temp[1];
            System.out.println("Newname = " + newname);
            // refines the fileName in case it is an absolute path
            fileName = new File(newname).getName();
            part.write(savePath + File.separator + fileName);
        }
        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/lobby.jsp").forward(
                request, response);
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
