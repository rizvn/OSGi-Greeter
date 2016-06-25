package com.rizvn.greeter.activator;

import com.rizvn.greeter.interfaces.Dict;
import org.osgi.framework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Riz
 */
public class Activator implements BundleActivator {

  ExecutorService executor = Executors.newSingleThreadExecutor();
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

    //execute runnable 5 second loop to greet in every language
    executor.execute(()->{
      while(true){
        //say hello in each lang
        dicts.forEach( dict -> System.out.println(dict.sayHello()));

        try{
          Thread.sleep(5000);
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });

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
    executor.awaitTermination(1, TimeUnit.MILLISECONDS);

    //say goodbye
    dicts.forEach(Dict::sayGoodbye);
  }
}
