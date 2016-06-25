package com.rizvn.greeter.activator;

import com.rizvn.greeter.interfaces.Dict;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Riz
 */
public class Activator implements BundleActivator {

  ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
  final List<Dict> dicts         = new ArrayList<>();

  @Override
  public void start(BundleContext context) throws Exception {
    addNewDictionaryListener(context);

    //get all dictionary references with osgi container
    ServiceReference<Dict>[] dictRefs
      = (ServiceReference<Dict>[]) context.getAllServiceReferences(Dict.class.getName(), "(lang=*)");

    if(dictRefs != null){
      //get the dictionary instance, and to dicts list
      for (ServiceReference<Dict> dictRef : dictRefs) {
        dicts.add(context.getService(dictRef));
      }
    }

    //execute every 5 seconds
    executor.scheduleWithFixedDelay(
      ()->  dicts.forEach(dict -> System.out.println(dict.sayHello()))
    , 5l, 5l, TimeUnit.SECONDS);

  }

  public void addNewDictionaryListener(BundleContext context){
    context.addServiceListener(event -> {
      if (event.getType() == ServiceEvent.REGISTERED) {
        Object service = context.getService(event.getServiceReference());
        if (service instanceof Dict) {
          System.out.println("Adding new dictionary");
          dicts.add((Dict) service);
        }
      }
    });
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    //shutdown threads
    executor.shutdown();

    System.out.println("Greeter Stopped");

    //say goodbye
    dicts.forEach(Dict::sayGoodbye);
  }
}
