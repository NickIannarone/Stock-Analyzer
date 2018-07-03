//This project needs some heavy restructuring b/c of both dependency
//injection and Moving Difference and Ratio being made into base classes.
//I will rename this as Instrument once I'm able to replace that with this class.

import java.util.ArrayList;

public class InstrumentV2 extends ArrayList<PriceBar>
{
  private String companyName;
  
  public InstrumentV2()
  {  
    companyName = "Company";
  }
  
  public InstrumentV2(String name) 
  {
    companyName = name;
  }
  
  public String getCompanyName()
  {
    return companyName;
  }
}