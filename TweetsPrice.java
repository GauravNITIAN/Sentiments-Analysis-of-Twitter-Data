/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweeter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author GAURAV KUMAR
 */


class TweetsPrice {
    
   
         
        int i=0;  
    
        
       // final TimeSeries_awt demo=new TimeSeries_awt("Amazon");
     
     public Double[] SentiwordsMatch(String tweets) throws IOException
     {
        String[] ar=new String[100];
        
        ar=tweets.split(" ");
        
       /* for(String splited_text:ar)
         System.out.println(splited_text);*/
          
          Double price[]=PriceCalculator(ar);
        
        
         return price;
         
     }
     
     public Double[] PriceCalculator(String ar[]) throws IOException
     { 
         double TotalPrice=0,PositivePrice=0,NegativePrice=0;
         Double arrayprice[]=new Double[3];
          String CurrentLine;
         try{
         BufferedReader buffer=new BufferedReader(new FileReader("Sentiwords.txt"));
         while ((CurrentLine = buffer.readLine()) != null) {
                String Line=CurrentLine;
                 String[] Sentiments_Price=new String[2];
                      Sentiments_Price=Line.split("\t",-1);
             for(int j=0;j<ar.length;j++){
             if( ar[j].equals(Sentiments_Price[1]) ){
                 // Price Will be calculated here;
                 if(Double.parseDouble(Sentiments_Price[0])>0)
                         {
                            PositivePrice += Double.parseDouble(Sentiments_Price[0]);
                         }
                 if(Double.parseDouble(Sentiments_Price[0])<0)
                 {
                       NegativePrice+=Double.parseDouble(Sentiments_Price[0]);
                 }
                 TotalPrice=Double.parseDouble(Sentiments_Price[0]);
             }   } } }
         catch(FileNotFoundException e)
         {
             System.out.println("File not Found"+e);
         }
    
         Double arrayPrice[]={PositivePrice,NegativePrice,TotalPrice};
          System.out.println("aaaaaaaaaaaaaaa"+PositivePrice);
              System.out.println(NegativePrice);
                     System.out.println(TotalPrice);
         
         return arrayPrice;
     }
     
     
   public Double[] HistoryPrice(String textFile) throws FileNotFoundException, IOException
   {
       String currentLine;
       Double price[] = new Double[2];
       String tweet[] =new String[2];
       String follower[] = new String[2];
       Double Positive_Tweet_Value=0.0,Negative_Tweet_Value=0.0,Average_TweetValue=0.0,Total_followers=1.0;
       int  Positive_Total_follow=0,Negative_Total_follow=0;
       BufferedReader buffer=new BufferedReader(new FileReader(textFile));
          while((currentLine=buffer.readLine())!=null)
          {
              
              if(currentLine.contains("Followers :"))
              {
                 follower=currentLine.split(":"); 
            //     System.out.println(follower[1]+"22222222222222222");
              }
              else if(currentLine.contains("Tweets :"))
              {
                  price= new Double[2];
                  tweet=currentLine.split(":");
                
               //   System.out.println(tweet[1]+"333333333333333333333");
              
                 price= SentiwordsMatch(tweet[1]);
                 
                int follow= Integer.parseInt(follower[1]);
                Total_followers=Total_followers+follow;
                
            // System.out.println(Total_followers+"11111111111111111111111111111"+follower[1]);  
             if(price[0]!=0.0)
             {
                Positive_Total_follow  =   Positive_Total_follow + follow;
             }
              if (price[1]!=0.0)
             {
                 Negative_Total_follow =  Negative_Total_follow + follow;
             }
           Positive_Tweet_Value   = Positive_Tweet_Value+price[0]*follow ;
           Negative_Tweet_Value   =   Negative_Tweet_Value+price[1]*follow ;
           Average_TweetValue   =   Negative_Tweet_Value+Positive_Tweet_Value;
           Total_followers   =   Total_followers+(Integer.parseInt(follower[1]));
                                   
           System.out.println(Total_followers);
            System.out.println(Positive_Tweet_Value);
             System.out.println(Negative_Tweet_Value);
             System.out.println(Average_TweetValue);
             
                 
              }
               
          }
          Positive_Tweet_Value =  Positive_Tweet_Value /  Positive_Total_follow;
          Negative_Tweet_Value =  Negative_Tweet_Value /  Negative_Total_follow ;
          Average_TweetValue  =  Average_TweetValue  /  Total_followers;
         
            System.out.println(textFile);
           System.out.println(Total_followers);
            System.out.println(" Percentage of Positivity  "+Positive_Tweet_Value*100+" %");
             System.out.println(" Percentage of Negativity   "+Negative_Tweet_Value*100+" %");
             System.out.println(" Human Sensor Says To buy Stock   " +Average_TweetValue*100+" %");
          
             price[0]=Positive_Tweet_Value;
               price[1] =Negative_Tweet_Value;
                  price[2]= Average_TweetValue;
           
       
   
            return price;
       
   }
    
   
   
 public void JfreeChart()
 {
     
 }
}
