package uz.yuk24.driver.presentation.login;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.repository.AuthRepository;

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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public LoginViewModel_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider) {
    return new LoginViewModel_Factory(authRepositoryProvider);
  }

  public static LoginViewModel newInstance(AuthRepository authRepository) {
    return new LoginViewModel(authRepository);
  }
}
