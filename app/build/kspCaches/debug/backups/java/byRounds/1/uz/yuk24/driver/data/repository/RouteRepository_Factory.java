package uz.yuk24.driver.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.remote.api.DriverApiService;

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
public final class RouteRepository_Factory implements Factory<RouteRepository> {
  private final Provider<DriverApiService> apiProvider;

  public RouteRepository_Factory(Provider<DriverApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public RouteRepository get() {
    return newInstance(apiProvider.get());
  }

  public static RouteRepository_Factory create(Provider<DriverApiService> apiProvider) {
    return new RouteRepository_Factory(apiProvider);
  }

  public static RouteRepository newInstance(DriverApiService api) {
    return new RouteRepository(api);
  }
}
