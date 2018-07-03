/*
Nick Iannarone
V1: 06/23/17
Retrieves online stock data, locates the beginnings of steady inclines and declines, and then analyzes those periods for recurring series of events.

Symbols:
MovingDifference = Moving Difference (The difference between the closing price of one day's stock )
MovingRatio = Moving Ratio (The ratio of positive MovingDifference's over a time window of a given length)
*/

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Analyzer
{    
    public static void main(String[] args) throws Exception
    {
        Scanner kbd = new Scanner(System.in);
        
        System.out.println("Enter a URL to a company's historical stock prices. Hint: If you get a link to download a spreadsheet, right click for the link address.");
        URL oracle = new URL(kbd.nextLine());
        
        System.out.println("What is the name of this company?");
        String name = kbd.nextLine();
        
        System.out.println("How many days apart should two stocks be when their differences in price are calculated (how wide is our window)?");
        int movingDifferenceRange = kbd.nextInt();
        
        Instrument company = new Instrument(name, movingDifferenceRange);                
        
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine; 
        int n = 0;       
        while ((inputLine = in.readLine()) != null)
        {
          if (n > 0)
          {   
            company.add(new PriceBar(inputLine));
          }
          
          n++;
        }
        
        company.setMovingDifference();
        company.setMovingRatio(50);       

        //My hope is that every company, over 12 years, will have long (define long) periods of MovingRatio = 0 and MovingRatio = 1.
        //This will lead to a cutoff. Also, note that since the only difference between two consecutive MovingRatioWindows is
        //two dates (one gets removed and one takes its place), these periods of 0 or 1 should be long to be significant.

        for (PriceBar pb : company)
        {
          try
          {          
            //System.out.println("MovingDifference = " + pb.getMovingDifference().getValue());
            System.out.print(" MovingRatio = " + pb.getMovingRatio().getValue());
          }
          catch (NullPointerException e)
          {
          }
        }
        
        System.out.println();        
        System.out.println(company.findStreaks());
        
        in.close();
    }
}
