/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_first;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
    private MyFirstPage currentPage;
    private final static Logger logger = Logger.getLogger(MyFirstAlbumManager.class.getCanonicalName());
    private Part filePart;
    private static final List<String> EXTENSIONS_ALLOWED = new ArrayList<>();
    private String eventDate;
    private String title;
    private String imgName;
    private Serializable img;
    private String caption;
    private List<String> months;
    private HashMap<String, Integer> monthNums;
    private List<String> days;
    private List<String> yearBeg;
    private List<String> yearEnd;
    private List<MyFirstPage> orderedPages;

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

    public MyFirstPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(MyFirstPage currentPage) {
        this.currentPage = currentPage;
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

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getYearBeg() {
        return yearBeg;
    }

    public void setYearBeg(List<String> yearBeg) {
        this.yearBeg = yearBeg;
    }

    public List<String> getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(List<String> yearEnd) {
        this.yearEnd = yearEnd;
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
            int pageNumber = determinePageNumber();
            updateOtherPageNums(pageNumber);
            newPage.setPageNumber(pageNumber);
            newPage.setEventDate(eventDate);
            newPage.setTitle(title);
            newPage.setImgName(fileName);
            newPage.setImg(b);
            newPage.setCaption(caption);
            newPage.setUserId(currentUser);
            manager.getDBManager().saveEntity(newPage);
            // so that album opens on newest uploaded page
            setCurrentPage(newPage);
            
            FacesMessage message = new FacesMessage("Image successfully uploaded!");
            context.addMessage(null, message);
            
        } catch (Exception ex) {
        }

    }
    
    private int determinePageNumber()
    {
        List<MyFirstPage> userPages = manager.getDBManager().getUserPages(currentUser);
        
        if (userPages == null)
            return 1;
        else
        {
            for (int i = 1; i <= userPages.size(); i++)
            {
                for (MyFirstPage p : userPages)
                {
                    if (p.getPageNumber() == i)
                        orderedPages.add(p);
                }
            }
            
            return binaryPageSearch(eventDate, 0, orderedPages.size() - 1);
        }
    }
    
    private int binaryPageSearch(String currentDate, int newestPageIndex, int oldestPageIndex)
    {
        MyFirstPage newestPage = orderedPages.get(newestPageIndex);
        MyFirstPage oldestPage = orderedPages.get(oldestPageIndex);
        
        if (findLaterDate(currentDate, newestPage.getEventDate()).equals(currentDate))
            return newestPage.getPageNumber();
        else if (findLaterDate(currentDate, oldestPage.getEventDate()).equals(oldestPage.getEventDate()))
            return oldestPage.getPageNumber() + 1;
        else
        {
            int midPageIndex = (newestPageIndex + oldestPageIndex)/2;
            String midPageEventDate = orderedPages.get(midPageIndex).getEventDate();
            int midPagePageNum = orderedPages.get(midPageIndex).getPageNumber();
            
            if (findLaterDate(currentDate, midPageEventDate).equals(currentDate))
            {
                // the event being entered took place in the first (newer) half of the pages
                return binaryPageSearch(currentDate, newestPageIndex, midPageIndex - 1);
            }
            else if (findLaterDate(currentDate, midPageEventDate).equals(midPageEventDate))
            {
                // the event being entered took place in the second (older) half of the pages
                return binaryPageSearch(currentDate, midPageIndex + 1, oldestPageIndex);
            }
            else
            {
                // the event being entered took place on the same date as did the event on
                // the middle page
                return midPagePageNum;
            }
        }
            
    }
    
    private String findLaterDate(String currentDate, String existingDate)
    {
        String[] currentDateParts = currentDate.split(" ");
        String[] existingDateParts = existingDate.split(" ");
        
        String currentYear = currentDateParts[2];
        String existingYear = existingDateParts[2];
        
        String currentMonth = currentDateParts[0];
        String existingMonth = existingDateParts[0];
        
        // get rid of comma after day
        String currentDay = currentDateParts[1].substring(0, currentDateParts[1].length() - 1);
        String existingDay = existingDateParts[1].substring(0, existingDateParts[1].length() - 1);
        
        // check which is later year
        if (Integer.parseInt(currentYear) > Integer.parseInt(existingYear))
            return currentDate;
        else if (Integer.parseInt(existingYear) > Integer.parseInt(currentYear))
            return existingDate;
        // check which is later month
        else if (monthNums.get(currentMonth) > monthNums.get(existingMonth))
            return currentDate;
        else if (monthNums.get(existingMonth) > monthNums.get(currentMonth))
            return existingDate;
        // check which is later day
        else if (Integer.parseInt(currentDay) > Integer.parseInt(existingDay))
            return currentDate;
        else if (Integer.parseInt(existingDay) > Integer.parseInt(currentDay))
            return existingDate;
        else
            return "Same day";
    }
    
    private void updateOtherPageNums(int currentPageNum)
    {
        for (int i = orderedPages.size() - 1; i >= currentPageNum - 1; i--)
        {
            MyFirstPage pageToUpdate = orderedPages.get(i);
            pageToUpdate.setPageNumber(pageToUpdate.getPageNumber() + 1);
            manager.getDBManager().saveEntity(pageToUpdate);
        }
    }
    
    public void reset()
    {
        currentUser = null;
        currentPage = null;
        filePart = null;
        orderedPages = new ArrayList();
        
        months = new ArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        
        monthNums = new HashMap();
        for (int i = 0; i < months.size(); i++)
        {
            monthNums.put(months.get(i), i + 1);
        }
        
        days = new ArrayList();
        for (int i = 1; i <= 31; i++)
        {
           days.add("" + i);
        }
        
        yearBeg = new ArrayList();
        for (int i = 18; i <= 20; i++)
        {
            yearBeg.add("" + i);
        }
        
        yearEnd = new ArrayList();
        for (int i = 1; i <= 99; i++)
        {
            yearEnd.add("" + i);
        }
    }
}