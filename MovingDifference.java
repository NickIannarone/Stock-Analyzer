import java.util.ArrayList;

public class MovingDifference
{ 
  private ArrayList<Double> values;
  private int period; //the distance in time between each PriceBar in the calculation
  
  //It's highly recommended that you make default constructors for dependency injection (DI).
  public MovingDifference()
  {
    values = new ArrayList<Double>();
    period = 20;
  }
   
  public MovingDifference(int period)
  {
    values = new ArrayList<Double>();
    this.period = period;
  } 
   
  public void setValues(ArrayList<Double> prices)
  {
    int oldDate = 0, newDate = period;
    
    while (newDate < prices.size())
    {
      values.add(prices.get(oldDate) - prices.get(newDate));
      oldDate++;
      newDate++;
    }
  }
  
  public ArrayList<Double> getValues()
  {
    roundValues();
    return values;
  }
  
  public void roundValues()
  {
    if (values.isEmpty())
      System.out.println("Error! Differences haven't been made yet.");
    
    for (int i = 0; i < values.size(); i++){
      values.set(i, Math.round(values.get(i) * 100.0) / 100.0);
      }
  }   
}