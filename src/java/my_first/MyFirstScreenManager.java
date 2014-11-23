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
    public static final int OTHER_SCREEN = 0;
    private int otherScreen = OTHER_SCREEN;
    public static final int ALBUM_SCREEN = 1;
    private int albumScreen = ALBUM_SCREEN;

        // THIS BEAN IS SHARED BY ALL THE MANAGERS
    private MyFirstManager manager;
    private int currentScreen;
    
    public MyFirstScreenManager(MyFirstManager initManager)
    {
        currentScreen = otherScreen;
        manager = initManager;
    }
    
    public int getOtherScreen() {   return otherScreen;  }
    public int getAlbumScreen() {   return albumScreen;  }
   
    public boolean isViewingOtherScreen()    {   return currentScreen == otherScreen;    }
    public boolean isViewingAlbumScreen()    {   return currentScreen == albumScreen;    }

    public void reset()
    {
        currentScreen = otherScreen;
    }
    
    public void switchToScreen(int screen)
    {
         currentScreen = screen;
    }
}