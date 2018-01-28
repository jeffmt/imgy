package com.example.imgy;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
//import java.sql.Blob;

@Controller
public class HomeController {
    @RequestMapping(value = "")
    @ResponseBody
    public String index() {
        return "HI";
    }

    final String path = "/Volumes/XFatOn4TB/java";

    @RequestMapping(value = "upload", method=RequestMethod.GET)
    @ResponseBody
    public String upload() {
        String html = "<form method='post' enctype='multipart/form-data'>" +
                "<input type='text' name='name' />" +
                "<input name='photo' type='file' accept='image/*'>" +
                "<input type='submit' value='Upload' />" +
                "</form>";
        return html;
    }

    @RequestMapping(value = "upload", method=RequestMethod.POST)
    @ResponseBody
    public void uploadPost(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("photo");
        InputStream inputStream = filePart.getInputStream();

        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
//        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
//            writer.println("New file " + fileName + " created at " + path);
        } catch (FileNotFoundException fne) {
//            writer.println("You either did not specify a file to upload or are "
//                    + "trying to upload a file to a protected or nonexistent "
//                    + "location.");
//            writer.println("<br/> ERROR: " + fne.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
//            if (writer != null) {
//                writer.close();
//            }
        }

//        SimpleDBUtil util = new SimpleDBUtil();
//        Blob photo = util.getBlobData(request.getParameter("name"));
//        util.close();
//        String[] uploads = request.getParameterValues("myFile");
//
//        for (String file: files) {
//           System.out.println(file);
//        }
//        return "blah";
    }

//    @RequestMapping(value = "upload", method = RequestMethod.POST)
//    public void handleUpload(@RequestParam("myFiles") MultipartFile files) throws IOException {
//        if (!files.isEmpty()) {
//            byte[] bytes = files.getBytes(); // alternatively, file.getInputStream();
//            // application logic
//        }
//
//    }

//    public File convert(MultipartFile file)
//    {
//        File convFile = new File(file.getOriginalFilename());
//        convFile.createNewFile();
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
