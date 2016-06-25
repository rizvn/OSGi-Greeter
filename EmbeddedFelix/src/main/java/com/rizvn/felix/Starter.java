package com.rizvn.felix;

import javafx.scene.shape.Path;
import org.apache.felix.framework.FrameworkFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * http://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-configuration-properties.html
 *
 * Created by Riz
 */
public class Starter {
  public static void main(String [] args) throws Exception{
    Properties properties = new Properties();
    Framework framework = new FrameworkFactory().newFramework(properties);
    framework.start();
    BundleContext bundleContext = framework.getBundleContext();

    List<Bundle> bundles = registerBundles("bundle", bundleContext);

    startBundles(bundles);
  }

  protected static List<Bundle> registerBundles(String bundleDir, BundleContext bundleContext) throws Exception{
    List<Bundle> bundles = new ArrayList<>();

    Files.walk(Paths.get(bundleDir)).forEach(path -> {
      try{
        if(path.toString().endsWith("jar")) {
          bundles.add(bundleContext.installBundle("file:" + path.toString()));
        }
      }
      catch (Exception ex)
      {
        throw new RuntimeException(ex);
      }
    });
    return bundles;
  }

  protected static void startBundles(List<Bundle> bundles){
    //start bundles
    bundles.forEach(bundle -> {
      try {
        bundle.start();
      }
      catch (Exception aEx){
        throw new RuntimeException(aEx);
      }
    });
  }

}
