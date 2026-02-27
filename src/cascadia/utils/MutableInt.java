package cascadia.utils;

/**
 * replace global var for translation camera in graphic mode
 */
public class MutableInt {
  private int value;

  public MutableInt(int initialValue) {
      this.value = initialValue;
  }

  public int getValue() {
      return value;
  }

  public void increment(int delta) {
      value += delta;
  }

  public void decrement(int delta) {
      value -= delta;
  }
}
