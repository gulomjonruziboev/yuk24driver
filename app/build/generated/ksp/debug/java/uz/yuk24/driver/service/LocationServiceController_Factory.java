package uz.yuk24.driver.service;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class LocationServiceController_Factory implements Factory<LocationServiceController> {
  @Override
  public LocationServiceController get() {
    return newInstance();
  }

  public static LocationServiceController_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocationServiceController newInstance() {
    return new LocationServiceController();
  }

  private static final class InstanceHolder {
    private static final LocationServiceController_Factory INSTANCE = new LocationServiceController_Factory();
  }
}
