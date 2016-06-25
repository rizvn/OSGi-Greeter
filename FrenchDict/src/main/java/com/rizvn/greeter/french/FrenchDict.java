package com.rizvn.greeter.french;

import com.rizvn.greeter.interfaces.Dict;

/**
 * Created by Riz
 */
public class FrenchDict implements Dict {
  @Override
  public String sayHello() {
    return "Bonjour";
  }

  @Override
  public String sayGoodbye() {
    return "Au Revoir";
  }
}
