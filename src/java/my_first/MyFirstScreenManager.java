/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_first;

/**
 *
 * @author Jessica
 */
public class MyFirstScreenManager 
{
    public static final int HOME_SCREEN = 0;
    private int homeScreen = HOME_SCREEN;
    public static final int ALBUM_SCREEN = 1;
    private int albumScreen = ALBUM_SCREEN;
    public static final int NEW_PAGE_SCREEN = 2;
    private int newPageScreen = NEW_PAGE_SCREEN;

    private MyFirstManager manager;
    private int currentScreen;
    private String homeTabClass;
    private String albumTabClass;
    private String newPageTabClass;
    
    public MyFirstScreenManager(MyFirstManager initManager)
    {
        manager = initManager;
        reset();
    }
    
    public int getHomeScreen()         {   return homeScreen;        }
    public int getAlbumScreen()        {   return albumScreen;       }
    public int getNewPageScreen()      {   return newPageScreen;     }
    public String getHomeTabClass()    {   return homeTabClass;      }
    public String getAlbumTabClass()   {   return albumTabClass;     }
    public String getNewPageTabClass() {   return newPageTabClass;   }
    
    public void setHomeTabClass(String homeTabClass)
    {
        this.homeTabClass = homeTabClass;
    }
    
    public void setAlbumTabClass(String albumTabClass)
    {
        this.albumTabClass = albumTabClass;
    }
    
    public void setNewPageTabClass(String newPageTabClass)
    {
        this.newPageTabClass = newPageTabClass;
    }
    
    public boolean isViewingHomeScreen()     {   return currentScreen == homeScreen;     }
    public boolean isViewingAlbumScreen()    {   return currentScreen == albumScreen;    }
    public boolean isViewingNewPageScreen()  {   return currentScreen == newPageScreen;  }

    public void reset()
    {
        currentScreen = homeScreen;
        homeTabClass = "active";
        albumTabClass = "notActive";
        newPageTabClass = "notActive";
    }
    
    public void switchToScreen(int screen)
    {
         currentScreen = screen;
         
         if (screen == homeScreen)
        {
            albumTabClass = "notActive";
            newPageTabClass = "notActive";
            homeTabClass = "active";
        }
        
        if (screen == albumScreen)
        {
            homeTabClass = "notActive";
            newPageTabClass = "notActive";
            albumTabClass = "active";
        }
        
        if (screen == newPageScreen)
        {
            homeTabClass = "notActive";
            albumTabClass = "notActive";
            newPageTabClass = "active";
        }
    }
}