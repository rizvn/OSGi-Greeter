package com.rizvn.greeter.french;

import com.rizvn.greeter.interfaces.Dict;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Hashtable;

/**
 * Created by Riz
 */
public class Activator implements BundleActivator {
  ServiceRegistration<Dict> registration;

  @Override
  public void start(BundleContext context) throws Exception {
    Hashtable<String, Object> props = new Hashtable<>();
    props.put("lang", "french");

    //register french dictionary as implemetation of Dict
    registration = context.registerService(Dict.class, new FrenchDict(), props);

    System.out.println("French Dictionary Registered");
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    registration.unregister();
  }
}
