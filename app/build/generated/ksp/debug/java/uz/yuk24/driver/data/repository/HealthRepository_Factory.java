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
public final class HealthRepository_Factory implements Factory<HealthRepository> {
  private final Provider<DriverApiService> apiProvider;

  public HealthRepository_Factory(Provider<DriverApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public HealthRepository get() {
    return newInstance(apiProvider.get());
  }

  public static HealthRepository_Factory create(Provider<DriverApiService> apiProvider) {
    return new HealthRepository_Factory(apiProvider);
  }

  public static HealthRepository newInstance(DriverApiService api) {
    return new HealthRepository(api);
  }
}
