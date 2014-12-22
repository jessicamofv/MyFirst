/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_first;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import my_first.entities.MyFirstPage;
import my_first.entities.MyFirstUser;

/**
 *
 * @author Jessica
 */
public class MyFirstAlbumManager {
    private MyFirstManager manager;
    private MyFirstUser currentUser;
    private final static Logger logger = Logger.getLogger(MyFirstAlbumManager.class.getCanonicalName());
    private Part filePart;
    private static final List<String> EXTENSIONS_ALLOWED = new ArrayList<>();
    private String eventDate;
    private String title;
    private String imgName;
    private Serializable img;
    private String caption;

    static {
        // images only
        EXTENSIONS_ALLOWED.add(".jpg");
        EXTENSIONS_ALLOWED.add(".bmp");
        EXTENSIONS_ALLOWED.add(".png");
        EXTENSIONS_ALLOWED.add(".gif");
    }
    
    public MyFirstAlbumManager(MyFirstManager initManager)
    {
        manager = initManager;
    }
    
    public MyFirstUser getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(MyFirstUser currentUser) {
        this.currentUser = currentUser;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Serializable getImg() {
        return img;
    }

    public void setImg(Serializable img) {
        this.img = img;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part filePart) {
        this.filePart = filePart;
    }

    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        logger.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;

    }
    
    public void upload() {
        logger.info(getFilePart().getName());
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            InputStream is = getFilePart().getInputStream();

            int i = is.available();
            byte[] b = new byte[i];
            is.read(b);

            logger.log(Level.INFO, "Length : {0}", b.length);
            String fileName = getFileName(getFilePart());
            logger.log(Level.INFO, "File name : {0}", fileName);

            // generate *unique* filename 
            final String extension = fileName.substring(fileName.length() - 4);

            if (!EXTENSIONS_ALLOWED.contains(extension)) {
                logger.severe("User tried to upload file that's not an image. Upload canceled.");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Error trying to upload image",
                                            "");
                context.addMessage(null, message);
                //response.sendRedirect("admin/product/List.xhtml?errMsg=Error trying to upload file");
                return;
            }

            MyFirstPage newPage = new MyFirstPage();
            int pageId = manager.getDBManager().generateNewPageId();
            newPage.setId(pageId);
            int pageNumber = manager.getDBManager().getNewestPageNumber(currentUser);
            newPage.setPageNumber(pageNumber);
            newPage.setEventDate(eventDate);
            newPage.setTitle(title);
            newPage.setImgName(fileName);
            newPage.setImg(b);
            newPage.setCaption(caption);
            newPage.setUserId(currentUser);
            manager.getDBManager().saveEntity(newPage);
            
            FacesMessage message = new FacesMessage("Image successfully uploaded!");
            context.addMessage(null, message);
            
        } catch (Exception ex) {
        }

    }
    
    public void reset()
    {
        currentUser = null;
        filePart = null;
    }
}