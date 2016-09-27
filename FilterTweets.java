
package tweeter;

/**
 *
 * @author GAURAV KUMAR
 */


public class FilterTweets {
    
    
    
    public boolean TweetsMatch(String modified_tweets) {
                
                
              if(modified_tweets.contains("stock")||modified_tweets.contains("share")||modified_tweets.contains("finance"))
              {
                 return true; 
              }
              else if(modified_tweets.contains("price"))
              {
                  return true;
              }
                else
              {
                return false ;
                
            }
    
    }
}
