/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_first.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Image servlet based on BalusC (@link below) implementation
 * @author balusc
 * @author markito
 * @author Jessica
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
@WebServlet(urlPatterns = "/image/*")
public class ImageServlet extends HttpServlet {
    
    private static final long serialVersionUID = 6439315738094263474L;

    private static final Logger logger = Logger.getLogger(ImageServlet.class.getCanonicalName());
    // Constants 
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    // Properties 
    private final String UPLOAD_DIR = "/upload/img/";
    //private final String UPLOAD_DIR = ResourceBundle.getBundle("bundles.Bundle").getString("UPLOAD_LOCATION");

    // Actions 
    @Override
    public void init() throws ServletException {
        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\images".
        // In UNIX, it is just straightforward "/images".
        // If you have stored files in the WebContent of a WAR, for example in the
        // "/WEB-INF/images" folder, then you can retrieve the absolute path by:
        // this.imagePath = getServletContext().getRealPath("/WEB-INF/images");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get requested image by path info.
        String requestedImage = request.getParameter("id"); //request.getPathInfo();
        

        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.

            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        /* undo this commenting out
        Product p = productBean.find(Integer.parseInt(requestedImage));

        if ((p == null) || (p.getImgSrc() == null)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
        }*/ else {
            // Init servlet response.
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            //response.setContentType("image/jpg");
            /* undo this commenting out
            response.setHeader("Content-Length", String.valueOf(p.getImgSrc().length));
            response.setHeader("Content-Disposition", "inline; filename=\"" + p.getName() + "\"");*/

            // Prepare streams.
            ByteArrayInputStream byteInputStream = null;
            BufferedOutputStream output = null;

            try {
                // Open streams.
                /* undo this commenting out
                byteInputStream = new ByteArrayInputStream(p.getImgSrc());*/
                output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

              // Write file contents to response.
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = byteInputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } finally {
                // close streams.
                close(output);
                close(byteInputStream);
            }
        }
    }

    // Helpers (can be refactored to public utility class) 
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, 
                        "Problems during image resource manipulation. {0}", 
                        e.getMessage());
            }
        }
    }
}
