
package tweeter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.regex.*;

/**
 *
 * @author GAURAV KUMAR
 */
public class Tweeter {

  FileWriter outs;
  static Login login;
  File tweet;
  FilterTweets filter;
  TweetsPrice price;
  String keyword;
  FilterQuery fq;
   TwitterStream twitterStream ;
   ConfigurationBuilder cb;
   
  Double Positive_Tweet_Value=0.0,Negative_Tweet_Value=0.0,Average_TweetValue=0.0,Total_followers=1.0;
  int Positive_Total_follow=0,Negative_Total_follow=0; 
  
  Double Positivity=0.0, Negativity=0.0,Average_Prediction=0.0;
  
  Tweeter(String Keywords) throws IOException 
  {
         login=new Login();
        
         filter=new FilterTweets();
         price=new TweetsPrice();
         keyword=Keywords;
          fq = new FilterQuery();
           tweet=new File(Keywords+".txt"); 
           tweet.createNewFile();
          
          
  }      
        
        StatusListener tweets=new StatusListener() {

            @Override
            public void onStatus(Status status) {
               
                
                
                try {
                    
                     
                    outs=new FileWriter(tweet,true);
                    BufferedWriter out=new BufferedWriter(outs);
                    
                     
                    
                    out.write(" \n");
                    out.newLine();
                   
                out.write("\n"+"Username :"+status.getUser().getScreenName()+"\n");
                out.newLine();
         //---------------------------------------------------------------------------------------------//       
                
                    //String tweet= Matcher..sub('((www\.[^\s]+)|(https?://[^\s]+))','URL',status.getText())
                String modified_tweets = status.getText().replaceAll("((www\\.[^\\s]+)|(https?://[^\\s]+))", " ");
                
                //  Remove White Space
                modified_tweets = modified_tweets.replaceAll("[\\s]+"," ");
                
                modified_tweets = modified_tweets.replaceAll("#[\\s]+","");
                
                //        FUNCTION CALL
                
                  boolean answer=filter.TweetsMatch(modified_tweets);
                  
                
                
       //------------------------------------------------------------------------------------------------//         
               if(answer)
               {
                   // Total Price Positive Price and Negative Price  is Here  
                   
                      //   System.out.println("1111111111111111111"); 
                        Double[] tweetPrice   =     price.SentiwordsMatch(modified_tweets);
                     //    System.out.println("1111111111111111111"+tweetPrice[0]+""+tweetPrice[1]); 
                        Total_followers=Total_followers+status.getUser().getFollowersCount();
                            System.out.println("222222222222222222"+Total_followers); 
                        
                        Positive_Tweet_Value=(Positive_Tweet_Value+tweetPrice[0]*status.getUser().getFollowersCount());
                          System.out.println("3333333333333"+Positive_Tweet_Value); 
                       
                         Negative_Tweet_Value=(Negative_Tweet_Value+tweetPrice[1]*status.getUser().getFollowersCount());
                         System.out.println("444444444444"+Negative_Tweet_Value); 
                         Average_TweetValue=(Positive_Tweet_Value+Negative_Tweet_Value);
                         
                         if(tweetPrice[0]!=0.0)
                          {
                        Positive_Total_follow  =   Positive_Total_follow + status.getUser().getFollowersCount();
                          }
                         if (tweetPrice[1]!=0.0)
                           {
                        Negative_Total_follow =  Negative_Total_follow + status.getUser().getFollowersCount() ;
                          }
                        Positivity=(Positive_Tweet_Value/Positive_Total_follow)*100;
                        Negativity=(Negative_Tweet_Value/Negative_Total_follow)*100;
                        Average_Prediction=(Average_TweetValue/Total_followers)*100;
                        
                        System.out.println("Reciving of Tweets of "+keyword); 
                        System.out.println("Psitive Tweets so  far"+Positivity+"%"); 
                        System.out.println("Negative Tweets so far"+Negativity+"%"); 
                        System.out.println("Averge Prediction"+Average_Prediction+"%"); 
                           
                  out.write("Name :"+status.getUser().getName());
               // out.write("Tweets :"+modified_tweets.toLowerCase()+"\n");
                // out.write("Status :"+"\n");
                out.newLine();
                 out.write("Followers :"+status.getUser().getFollowersCount()+"\n");
                 out.newLine();
                 out.write("Friends :"+status.getUser().getFriendsCount()+"\n");
                 out.newLine();
                 out.write("Favorites :"+status.getUser().getFavouritesCount()+"\n");
                 out.newLine();
                  out.write("TweetId :"+status.getId()+"\n");
                 out.newLine();
                out.write("Date :"+status.getCreatedAt()+"\n");
                out.newLine();
               // out.append((char) status.getFavoriteCount()+"\n");
                out.write("Place :"+status.getPlace()+"\n");
                out.newLine();
                
                out.write("Time Zone :"+status.getUser().getTimeZone()+"\n");
                out.newLine();
                out.write("Tweets :"+modified_tweets.toLowerCase()+"\n");
                out.append(" \n    ");    
                out.newLine();
                
               out.close();
               }
               
               else
               {
                   
               }
                    
                } catch (IOException ex) {
                    
                   System.out.print("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
                    Logger.getLogger(Tweeter.class.getName()).log(Level.SEVERE, null, ex);
                }
              /*  System.out.print("[ \n");
                System.out.println("Username :"+status.getUser().getName());
                System.out.println("Status :"+status.getText());
                 System.out.println("Followers :"+status.getUser().getFollowersCount());
                System.out.println("Date :"+status.getCreatedAt());
                System.out.println(status.getFavoriteCount());
                System.out.println(" \n    ]");  */                                             
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
            System.out.print("Hello");
            }

            @Override
            public void onTrackLimitationNotice(int i) {
               System.out.print("Hello");            }

            @Override
            public void onScrubGeo(long l, long l1) {
                      System.out.print("Hello");  
            }

            @Override
            public void onStallWarning(StallWarning sw) {
               System.out.print("Hello");
            }

            @Override
            public void onException(Exception excptn) {
            System.out.print("Hello"+excptn);     
            }

            
        };
  private void Authenticate()     
  {
      cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("uMZtmnPL5YzlIr4h7R9gWkYRT");
        cb.setOAuthConsumerSecret("tAC8q5V6J9NrC6P1A5hLiAk28DwivQfC4mKbFFW2zWrOVuAeCq");
        cb.setOAuthAccessToken("2691889945-1bJVKGYe282IrELx56cKlnVBmcc0ya9SxeF5r8q");
        cb.setOAuthAccessTokenSecret("lD8r7BzNF4MsdB6J9WiLEs5KH7zR3GQqpcQTipiZ03iqU");
       twitterStream = new TwitterStreamFactory(cb.build()).getInstance();  
  } 
  private void catchStreams(Tweeter startStream) throws IOException
  {
      
      startStream.fq.track(startStream.keyword);
        startStream.twitterStream.addListener(startStream.tweets);
        startStream.twitterStream.filter(startStream.fq); 
      
      TweetsPrice tw=new TweetsPrice();
       tw.HistoryPrice(startStream.keyword+".txt");
  } 
    public static void main(String[] args) throws IOException {
        
         Tweeter amazon=new Tweeter("amazon");
       amazon.Authenticate();
       amazon.catchStreams(amazon);
        
      
       
       
       
       // amazon.login.setVisible(true);
        
        
        // Object For Every Company we have
        
      
      
      
        //Another Object
    //TwitterStream twitterStream1 = new TwitterStreamFactory(cb.build()).getInstance();       
        Tweeter google=new Tweeter("google");
        google.Authenticate();
        google.catchStreams(google);
        
      /* Tweeter infosys=new Tweeter("infosys");
       infosys.Authenticate();;
       infosys.catchStreams(infosys);
      
        
        
     /*   Thread t1 = new Thread(new Runnable() {
     public void run() {
          // code goes here.
     }
});  
t1.start();*/
        
    }
    
    
    
    
}
