package uz.yuk24.driver.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.local.TokenStore;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<DriverApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public AuthRepository_Factory(Provider<DriverApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiProvider.get(), tokenStoreProvider.get());
  }

  public static AuthRepository_Factory create(Provider<DriverApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new AuthRepository_Factory(apiProvider, tokenStoreProvider);
  }

  public static AuthRepository newInstance(DriverApiService api, TokenStore tokenStore) {
    return new AuthRepository(api, tokenStore);
  }
}
