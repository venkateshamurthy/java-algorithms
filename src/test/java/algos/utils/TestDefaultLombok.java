package algos.utils;

import org.junit.Test;

import junit.framework.Assert;
import lombok.Data;

public class TestDefaultLombok {
  
  @Test
  public void testTemp() {
    Temp temp = null; //oh! i cant still use Temp.of
    Assert.assertNull(temp);
  }

  @DefaultLombok
  private static class Temp {
    int a;
    String b;
    public void display() {
      //log.debug("{}",a+b);
    }
  }
}


