package uz.yuk24.driver.data.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.serialization.json.Json;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ActiveOrderStore_Factory implements Factory<ActiveOrderStore> {
  private final Provider<Context> contextProvider;

  private final Provider<Json> jsonProvider;

  public ActiveOrderStore_Factory(Provider<Context> contextProvider, Provider<Json> jsonProvider) {
    this.contextProvider = contextProvider;
    this.jsonProvider = jsonProvider;
  }

  @Override
  public ActiveOrderStore get() {
    return newInstance(contextProvider.get(), jsonProvider.get());
  }

  public static ActiveOrderStore_Factory create(Provider<Context> contextProvider,
      Provider<Json> jsonProvider) {
    return new ActiveOrderStore_Factory(contextProvider, jsonProvider);
  }

  public static ActiveOrderStore newInstance(Context context, Json json) {
    return new ActiveOrderStore(context, json);
  }
}
