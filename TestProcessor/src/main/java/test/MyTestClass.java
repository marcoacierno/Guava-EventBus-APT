package test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class MyTestClass {
  /**
   * Should fail.
   * Methods with @Subscribe should have
   * one parameter.
   *
   * Message:
   *  Found 0 paremeter(s) instead of 1
   */
  @Subscribe
  public void method() {
  }

  /**
   * Should compile
   */
  @Subscribe
  public void method2(final String hello) {
  }

  /**
   * Should fail.
   *
   * Message:
   *  Found 3 paremeter(s) instead of 1
   */
  @Subscribe
  public void method3(final String a, final int b, final Void c) {
  }

  @Subscribe
  public void test(final int event) {
    System.out.println("invoked");
  }

  public static void main(String[] args) {
    final MyTestClass myTestClass = new MyTestClass();

    final EventBus eventBus = new EventBus();
    eventBus.register(myTestClass);
    eventBus.post(5);


  }
}
