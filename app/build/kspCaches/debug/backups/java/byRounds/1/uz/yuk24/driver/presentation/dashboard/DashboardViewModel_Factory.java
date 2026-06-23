package uz.yuk24.driver.presentation.dashboard;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.local.ActiveOrderStore;
import uz.yuk24.driver.data.local.SessionManager;
import uz.yuk24.driver.data.repository.OrderRepository;
import uz.yuk24.driver.data.repository.ProfileRepository;
import uz.yuk24.driver.data.repository.ReviewsRepository;
import uz.yuk24.driver.data.repository.RouteRepository;
import uz.yuk24.driver.service.LocationServiceController;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  private final Provider<RouteRepository> routeRepositoryProvider;

  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<ReviewsRepository> reviewsRepositoryProvider;

  private final Provider<ActiveOrderStore> activeOrderStoreProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<LocationServiceController> locationServiceControllerProvider;

  private final Provider<Context> contextProvider;

  public DashboardViewModel_Factory(Provider<OrderRepository> orderRepositoryProvider,
      Provider<RouteRepository> routeRepositoryProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<ReviewsRepository> reviewsRepositoryProvider,
      Provider<ActiveOrderStore> activeOrderStoreProvider,
      Provider<SessionManager> sessionManagerProvider,
      Provider<LocationServiceController> locationServiceControllerProvider,
      Provider<Context> contextProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
    this.routeRepositoryProvider = routeRepositoryProvider;
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.reviewsRepositoryProvider = reviewsRepositoryProvider;
    this.activeOrderStoreProvider = activeOrderStoreProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.locationServiceControllerProvider = locationServiceControllerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(orderRepositoryProvider.get(), routeRepositoryProvider.get(), profileRepositoryProvider.get(), reviewsRepositoryProvider.get(), activeOrderStoreProvider.get(), sessionManagerProvider.get(), locationServiceControllerProvider.get(), contextProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<OrderRepository> orderRepositoryProvider,
      Provider<RouteRepository> routeRepositoryProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<ReviewsRepository> reviewsRepositoryProvider,
      Provider<ActiveOrderStore> activeOrderStoreProvider,
      Provider<SessionManager> sessionManagerProvider,
      Provider<LocationServiceController> locationServiceControllerProvider,
      Provider<Context> contextProvider) {
    return new DashboardViewModel_Factory(orderRepositoryProvider, routeRepositoryProvider, profileRepositoryProvider, reviewsRepositoryProvider, activeOrderStoreProvider, sessionManagerProvider, locationServiceControllerProvider, contextProvider);
  }

  public static DashboardViewModel newInstance(OrderRepository orderRepository,
      RouteRepository routeRepository, ProfileRepository profileRepository,
      ReviewsRepository reviewsRepository, ActiveOrderStore activeOrderStore,
      SessionManager sessionManager, LocationServiceController locationServiceController,
      Context context) {
    return new DashboardViewModel(orderRepository, routeRepository, profileRepository, reviewsRepository, activeOrderStore, sessionManager, locationServiceController, context);
  }
}
