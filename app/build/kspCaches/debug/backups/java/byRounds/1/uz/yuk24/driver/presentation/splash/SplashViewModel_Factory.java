package uz.yuk24.driver.presentation.splash;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.local.ActiveOrderStore;
import uz.yuk24.driver.data.repository.AuthRepository;
import uz.yuk24.driver.data.repository.HealthRepository;

@ScopeMetadata
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<HealthRepository> healthRepositoryProvider;

  private final Provider<ActiveOrderStore> activeOrderStoreProvider;

  public SplashViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<ActiveOrderStore> activeOrderStoreProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.healthRepositoryProvider = healthRepositoryProvider;
    this.activeOrderStoreProvider = activeOrderStoreProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(authRepositoryProvider.get(), healthRepositoryProvider.get(), activeOrderStoreProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<ActiveOrderStore> activeOrderStoreProvider) {
    return new SplashViewModel_Factory(authRepositoryProvider, healthRepositoryProvider, activeOrderStoreProvider);
  }

  public static SplashViewModel newInstance(AuthRepository authRepository,
      HealthRepository healthRepository, ActiveOrderStore activeOrderStore) {
    return new SplashViewModel(authRepository, healthRepository, activeOrderStore);
  }
}
