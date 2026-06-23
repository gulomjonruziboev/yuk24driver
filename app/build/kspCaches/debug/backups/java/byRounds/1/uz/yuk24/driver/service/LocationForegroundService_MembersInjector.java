package uz.yuk24.driver.service;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.local.TokenStore;
import uz.yuk24.driver.data.repository.LocationRepository;

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
public final class LocationForegroundService_MembersInjector implements MembersInjector<LocationForegroundService> {
  private final Provider<LocationRepository> locationRepositoryProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public LocationForegroundService_MembersInjector(
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.locationRepositoryProvider = locationRepositoryProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  public static MembersInjector<LocationForegroundService> create(
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new LocationForegroundService_MembersInjector(locationRepositoryProvider, tokenStoreProvider);
  }

  @Override
  public void injectMembers(LocationForegroundService instance) {
    injectLocationRepository(instance, locationRepositoryProvider.get());
    injectTokenStore(instance, tokenStoreProvider.get());
  }

  @InjectedFieldSignature("uz.yuk24.driver.service.LocationForegroundService.locationRepository")
  public static void injectLocationRepository(LocationForegroundService instance,
      LocationRepository locationRepository) {
    instance.locationRepository = locationRepository;
  }

  @InjectedFieldSignature("uz.yuk24.driver.service.LocationForegroundService.tokenStore")
  public static void injectTokenStore(LocationForegroundService instance, TokenStore tokenStore) {
    instance.tokenStore = tokenStore;
  }
}
