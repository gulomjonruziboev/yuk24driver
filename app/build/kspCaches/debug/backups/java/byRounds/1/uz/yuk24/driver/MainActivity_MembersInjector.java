package uz.yuk24.driver;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.local.SessionManager;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<SessionManager> sessionManagerProvider;

  public MainActivity_MembersInjector(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<SessionManager> sessionManagerProvider) {
    return new MainActivity_MembersInjector(sessionManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectSessionManager(instance, sessionManagerProvider.get());
  }

  @InjectedFieldSignature("uz.yuk24.driver.MainActivity.sessionManager")
  public static void injectSessionManager(MainActivity instance, SessionManager sessionManager) {
    instance.sessionManager = sessionManager;
  }
}
