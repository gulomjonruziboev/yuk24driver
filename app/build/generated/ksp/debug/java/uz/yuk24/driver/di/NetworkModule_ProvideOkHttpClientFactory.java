package uz.yuk24.driver.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import uz.yuk24.driver.data.local.SessionManager;
import uz.yuk24.driver.data.local.TokenStore;

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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<TokenStore> tokenStoreProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public NetworkModule_ProvideOkHttpClientFactory(Provider<TokenStore> tokenStoreProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.tokenStoreProvider = tokenStoreProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(tokenStoreProvider.get(), sessionManagerProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(
      Provider<TokenStore> tokenStoreProvider, Provider<SessionManager> sessionManagerProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(tokenStoreProvider, sessionManagerProvider);
  }

  public static OkHttpClient provideOkHttpClient(TokenStore tokenStore,
      SessionManager sessionManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(tokenStore, sessionManager));
  }
}
