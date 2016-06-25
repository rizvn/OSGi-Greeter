package com.rizvn.greeter.english;

import com.rizvn.greeter.interfaces.Dict;

/**
 * Created by Riz
 */
public class EnglishDict implements Dict {
  @Override
  public String sayHello() {
    return "Hello";
  }

  @Override
  public String sayGoodbye() {
    return "Goodbye";
  }
}
