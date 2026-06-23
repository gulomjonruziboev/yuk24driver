package uz.yuk24.driver;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import uz.yuk24.driver.data.local.ActiveOrderStore;
import uz.yuk24.driver.data.local.SessionManager;
import uz.yuk24.driver.data.local.TokenStore;
import uz.yuk24.driver.data.remote.api.DriverApiService;
import uz.yuk24.driver.data.repository.AuthRepository;
import uz.yuk24.driver.data.repository.HealthRepository;
import uz.yuk24.driver.data.repository.LocationRepository;
import uz.yuk24.driver.data.repository.OrderRepository;
import uz.yuk24.driver.data.repository.ProfileRepository;
import uz.yuk24.driver.data.repository.ReviewsRepository;
import uz.yuk24.driver.data.repository.RouteRepository;
import uz.yuk24.driver.di.NetworkModule_ProvideDriverApiServiceFactory;
import uz.yuk24.driver.di.NetworkModule_ProvideJsonFactory;
import uz.yuk24.driver.di.NetworkModule_ProvideOkHttpClientFactory;
import uz.yuk24.driver.di.NetworkModule_ProvideRetrofitFactory;
import uz.yuk24.driver.presentation.dashboard.DashboardViewModel;
import uz.yuk24.driver.presentation.dashboard.DashboardViewModel_HiltModules;
import uz.yuk24.driver.presentation.login.LoginViewModel;
import uz.yuk24.driver.presentation.login.LoginViewModel_HiltModules;
import uz.yuk24.driver.presentation.reviews.ReviewsViewModel;
import uz.yuk24.driver.presentation.reviews.ReviewsViewModel_HiltModules;
import uz.yuk24.driver.presentation.splash.SplashViewModel;
import uz.yuk24.driver.presentation.splash.SplashViewModel_HiltModules;
import uz.yuk24.driver.service.LocationForegroundService;
import uz.yuk24.driver.service.LocationForegroundService_MembersInjector;
import uz.yuk24.driver.service.LocationServiceController;

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
public final class DaggerYukDriverApplication_HiltComponents_SingletonC {
  private DaggerYukDriverApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public YukDriverApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements YukDriverApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements YukDriverApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements YukDriverApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements YukDriverApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements YukDriverApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements YukDriverApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements YukDriverApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public YukDriverApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends YukDriverApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends YukDriverApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends YukDriverApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends YukDriverApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(4).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_login_LoginViewModel, LoginViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_reviews_ReviewsViewModel, ReviewsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_splash_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
      injectMainActivity2(arg0);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectSessionManager(instance, singletonCImpl.sessionManagerProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String uz_yuk24_driver_presentation_dashboard_DashboardViewModel = "uz.yuk24.driver.presentation.dashboard.DashboardViewModel";

      static String uz_yuk24_driver_presentation_reviews_ReviewsViewModel = "uz.yuk24.driver.presentation.reviews.ReviewsViewModel";

      static String uz_yuk24_driver_presentation_login_LoginViewModel = "uz.yuk24.driver.presentation.login.LoginViewModel";

      static String uz_yuk24_driver_presentation_splash_SplashViewModel = "uz.yuk24.driver.presentation.splash.SplashViewModel";

      @KeepFieldType
      DashboardViewModel uz_yuk24_driver_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      ReviewsViewModel uz_yuk24_driver_presentation_reviews_ReviewsViewModel2;

      @KeepFieldType
      LoginViewModel uz_yuk24_driver_presentation_login_LoginViewModel2;

      @KeepFieldType
      SplashViewModel uz_yuk24_driver_presentation_splash_SplashViewModel2;
    }
  }

  private static final class ViewModelCImpl extends YukDriverApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<ReviewsViewModel> reviewsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.reviewsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(4).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_login_LoginViewModel, ((Provider) loginViewModelProvider)).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_reviews_ReviewsViewModel, ((Provider) reviewsViewModelProvider)).put(LazyClassKeyProvider.uz_yuk24_driver_presentation_splash_SplashViewModel, ((Provider) splashViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String uz_yuk24_driver_presentation_reviews_ReviewsViewModel = "uz.yuk24.driver.presentation.reviews.ReviewsViewModel";

      static String uz_yuk24_driver_presentation_splash_SplashViewModel = "uz.yuk24.driver.presentation.splash.SplashViewModel";

      static String uz_yuk24_driver_presentation_dashboard_DashboardViewModel = "uz.yuk24.driver.presentation.dashboard.DashboardViewModel";

      static String uz_yuk24_driver_presentation_login_LoginViewModel = "uz.yuk24.driver.presentation.login.LoginViewModel";

      @KeepFieldType
      ReviewsViewModel uz_yuk24_driver_presentation_reviews_ReviewsViewModel2;

      @KeepFieldType
      SplashViewModel uz_yuk24_driver_presentation_splash_SplashViewModel2;

      @KeepFieldType
      DashboardViewModel uz_yuk24_driver_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      LoginViewModel uz_yuk24_driver_presentation_login_LoginViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // uz.yuk24.driver.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.orderRepositoryProvider.get(), singletonCImpl.routeRepositoryProvider.get(), singletonCImpl.profileRepositoryProvider.get(), singletonCImpl.reviewsRepositoryProvider.get(), singletonCImpl.activeOrderStoreProvider.get(), singletonCImpl.sessionManagerProvider.get(), singletonCImpl.locationServiceControllerProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // uz.yuk24.driver.presentation.login.LoginViewModel 
          return (T) new LoginViewModel(singletonCImpl.authRepositoryProvider.get());

          case 2: // uz.yuk24.driver.presentation.reviews.ReviewsViewModel 
          return (T) new ReviewsViewModel(singletonCImpl.reviewsRepositoryProvider.get());

          case 3: // uz.yuk24.driver.presentation.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.authRepositoryProvider.get(), singletonCImpl.healthRepositoryProvider.get(), singletonCImpl.activeOrderStoreProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends YukDriverApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends YukDriverApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectLocationForegroundService(LocationForegroundService arg0) {
      injectLocationForegroundService2(arg0);
    }

    private LocationForegroundService injectLocationForegroundService2(
        LocationForegroundService instance) {
      LocationForegroundService_MembersInjector.injectLocationRepository(instance, singletonCImpl.locationRepositoryProvider.get());
      LocationForegroundService_MembersInjector.injectTokenStore(instance, singletonCImpl.tokenStoreProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends YukDriverApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<TokenStore> tokenStoreProvider;

    private Provider<SessionManager> sessionManagerProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Json> provideJsonProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<DriverApiService> provideDriverApiServiceProvider;

    private Provider<OrderRepository> orderRepositoryProvider;

    private Provider<RouteRepository> routeRepositoryProvider;

    private Provider<ProfileRepository> profileRepositoryProvider;

    private Provider<ReviewsRepository> reviewsRepositoryProvider;

    private Provider<ActiveOrderStore> activeOrderStoreProvider;

    private Provider<LocationServiceController> locationServiceControllerProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<HealthRepository> healthRepositoryProvider;

    private Provider<LocationRepository> locationRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.tokenStoreProvider = DoubleCheck.provider(new SwitchingProvider<TokenStore>(singletonCImpl, 1));
      this.sessionManagerProvider = DoubleCheck.provider(new SwitchingProvider<SessionManager>(singletonCImpl, 0));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 5));
      this.provideJsonProvider = DoubleCheck.provider(new SwitchingProvider<Json>(singletonCImpl, 6));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 4));
      this.provideDriverApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<DriverApiService>(singletonCImpl, 3));
      this.orderRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<OrderRepository>(singletonCImpl, 2));
      this.routeRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<RouteRepository>(singletonCImpl, 7));
      this.profileRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ProfileRepository>(singletonCImpl, 8));
      this.reviewsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ReviewsRepository>(singletonCImpl, 9));
      this.activeOrderStoreProvider = DoubleCheck.provider(new SwitchingProvider<ActiveOrderStore>(singletonCImpl, 10));
      this.locationServiceControllerProvider = DoubleCheck.provider(new SwitchingProvider<LocationServiceController>(singletonCImpl, 11));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 12));
      this.healthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<HealthRepository>(singletonCImpl, 13));
      this.locationRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LocationRepository>(singletonCImpl, 14));
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @Override
    public void injectYukDriverApplication(YukDriverApplication yukDriverApplication) {
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // uz.yuk24.driver.data.local.SessionManager 
          return (T) new SessionManager(singletonCImpl.tokenStoreProvider.get());

          case 1: // uz.yuk24.driver.data.local.TokenStore 
          return (T) new TokenStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // uz.yuk24.driver.data.repository.OrderRepository 
          return (T) new OrderRepository(singletonCImpl.provideDriverApiServiceProvider.get());

          case 3: // uz.yuk24.driver.data.remote.api.DriverApiService 
          return (T) NetworkModule_ProvideDriverApiServiceFactory.provideDriverApiService(singletonCImpl.provideRetrofitProvider.get());

          case 4: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 5: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient(singletonCImpl.tokenStoreProvider.get(), singletonCImpl.sessionManagerProvider.get());

          case 6: // kotlinx.serialization.json.Json 
          return (T) NetworkModule_ProvideJsonFactory.provideJson();

          case 7: // uz.yuk24.driver.data.repository.RouteRepository 
          return (T) new RouteRepository(singletonCImpl.provideDriverApiServiceProvider.get());

          case 8: // uz.yuk24.driver.data.repository.ProfileRepository 
          return (T) new ProfileRepository(singletonCImpl.provideDriverApiServiceProvider.get());

          case 9: // uz.yuk24.driver.data.repository.ReviewsRepository 
          return (T) new ReviewsRepository(singletonCImpl.provideDriverApiServiceProvider.get());

          case 10: // uz.yuk24.driver.data.local.ActiveOrderStore 
          return (T) new ActiveOrderStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideJsonProvider.get());

          case 11: // uz.yuk24.driver.service.LocationServiceController 
          return (T) new LocationServiceController();

          case 12: // uz.yuk24.driver.data.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideDriverApiServiceProvider.get(), singletonCImpl.tokenStoreProvider.get());

          case 13: // uz.yuk24.driver.data.repository.HealthRepository 
          return (T) new HealthRepository(singletonCImpl.provideDriverApiServiceProvider.get());

          case 14: // uz.yuk24.driver.data.repository.LocationRepository 
          return (T) new LocationRepository(singletonCImpl.provideDriverApiServiceProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
