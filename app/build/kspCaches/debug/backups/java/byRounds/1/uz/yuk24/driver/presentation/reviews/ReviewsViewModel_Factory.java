package uz.yuk24.driver.presentation.reviews;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import uz.yuk24.driver.data.repository.ReviewsRepository;

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
public final class ReviewsViewModel_Factory implements Factory<ReviewsViewModel> {
  private final Provider<ReviewsRepository> reviewsRepositoryProvider;

  public ReviewsViewModel_Factory(Provider<ReviewsRepository> reviewsRepositoryProvider) {
    this.reviewsRepositoryProvider = reviewsRepositoryProvider;
  }

  @Override
  public ReviewsViewModel get() {
    return newInstance(reviewsRepositoryProvider.get());
  }

  public static ReviewsViewModel_Factory create(
      Provider<ReviewsRepository> reviewsRepositoryProvider) {
    return new ReviewsViewModel_Factory(reviewsRepositoryProvider);
  }

  public static ReviewsViewModel newInstance(ReviewsRepository reviewsRepository) {
    return new ReviewsViewModel(reviewsRepository);
  }
}
