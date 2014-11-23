package my_first;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * This is the session bean that services the WebJotto Web application. This
 * object is constructed for each session when a user first requests the page.
 * Note that it delegates most functionality to its instance variable manager
 * classes.
 * 
 * @author Jessica
 */
@ManagedBean(name = "myFirstManager")
@SessionScoped
public class MyFirstManager implements Serializable
{
    private MyFirstUserManager userManager;
    private MyFirstDBManager dBManager;
    private MyFirstAlbumManager albumManager;
    private MyFirstScreenManager screenManager;

    @PersistenceContext(unitName="MyFirstPU")
    EntityManager em;
    
    @Resource
    private UserTransaction userTransaction;

    public static final String NO_ERROR = "";
    
    public MyFirstManager()
    {
        userManager = new MyFirstUserManager(this);        
        dBManager = new MyFirstDBManager(this);
        albumManager = new MyFirstAlbumManager(this);
        screenManager = new MyFirstScreenManager(this);
        reset();
    }

    public void reset()
    {
        userManager.reset();
        dBManager.reset();
        albumManager.reset();
        screenManager.reset();
    }
    
    public EntityManager        getEntityManager()      {   return em;                      }    
    public MyFirstUserManager   getUserManager()        {   return userManager;             }
    public MyFirstDBManager     getDBManager()          {   return dBManager;               }
    public MyFirstAlbumManager  getAlbumManager()       {   return albumManager;            }
    public MyFirstScreenManager getScreenManager()      {   return screenManager;           }
    public UserTransaction      getUserTransaction()    {   return userTransaction;         }

    public void setUserManager(MyFirstUserManager userManager)
    {   this.userManager = userManager;             }
    public void setDBManager(MyFirstDBManager dBManager)
    {   this.dBManager = dBManager;                 }
    public void setAlbumManager(MyFirstAlbumManager albumManager)
    {   this.albumManager = albumManager;           }
    public void setScreenManager(MyFirstScreenManager screenManager)
    {   this.screenManager = screenManager;         }
}