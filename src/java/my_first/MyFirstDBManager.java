package my_first;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import my_first.entities.MyFirstUser;

/**
 * 
 * @author Jessica
 */
public class MyFirstDBManager
{
    public static final String USER_COUNT_QUERY = "SELECT Count(*) FROM MYFIRSTUSER";
    
    private MyFirstManager manager;

    public MyFirstDBManager(MyFirstManager initManager)
    {
        manager = initManager;
        reset();
    }

    public MyFirstUser getUserByUsername(String username) {
        EntityManager em = manager.getEntityManager();
        TypedQuery<MyFirstUser> query = em.createNamedQuery("MyFirstUser.findByUsername", MyFirstUser.class);
        
        query.setParameter("username", username);
        
        List<MyFirstUser> foundUsers = query.getResultList();
        
        if (foundUsers.isEmpty())
            return null;
        else
            return foundUsers.get(0);
    }

    public int generateNewUserId()
    {
        EntityManager em = manager.getEntityManager();
        Query countQuery = em.createNativeQuery(USER_COUNT_QUERY);

        int count = (Integer)countQuery.getResultList().get(0); 

        return count+1;          
    }

    public void saveEntity(Object entityToSave)
    {
        EntityManager em = manager.getEntityManager();
        UserTransaction utx = manager.getUserTransaction();
        try
        {
            // START THE SAVE TRANSACTION
            utx.begin();
            
            // MERGE THE OBJECT WITH THE TABLE
            em.merge(entityToSave);
            
            // COMMIT THE TRANSACTION
            utx.commit();
        }
        // ERROR OCCURRED
        catch(NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void reset()
    {
        
    }
}