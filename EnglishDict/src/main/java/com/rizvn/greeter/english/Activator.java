package com.rizvn.greeter.english;

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
    props.put("lang", "english");

    //register english dictionary as implemetation of Dict
    registration = context.registerService(Dict.class, new EnglishDict(), props);

    System.out.println("English Dictionary Registered");
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    registration.unregister();
  }
}
