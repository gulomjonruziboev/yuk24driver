package uz.yuk24.driver.presentation.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.yuk24.driver.R
import uz.yuk24.driver.domain.model.Review
import uz.yuk24.driver.presentation.theme.AmberAccent
import uz.yuk24.driver.presentation.theme.BorderColor
import uz.yuk24.driver.presentation.theme.Primary
import uz.yuk24.driver.presentation.theme.SurfaceLight
import uz.yuk24.driver.presentation.theme.TextPrimary
import uz.yuk24.driver.presentation.theme.TextSecondary
import uz.yuk24.driver.util.formatRating
import uz.yuk24.driver.util.formatReviewDate
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    onBack: () -> Unit,
    viewModel: ReviewsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.driver_my_reviews)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SurfaceLight)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Primary
                    )
                }
                uiState.reviews.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Outlined.ChatBubbleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = BorderColor
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(R.string.driver_no_reviews),
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            RatingSummaryCard(
                                avgRating = uiState.summary.avgRating ?: 0.0,
                                totalReviews = uiState.summary.totalReviews,
                                reviews = uiState.reviews
                            )
                        }
                        items(uiState.reviews, key = { it.id.ifBlank { "${it.orderId}_${it.createdAt}" } }) { review ->
                            ReviewCard(review)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingSummaryCard(
    avgRating: Double,
    totalReviews: Int,
    reviews: List<Review>
) {
    val distribution = (5 downTo 1).map { star ->
        star to reviews.count { it.rating == star }
    }
    val maxCount = distribution.maxOf { it.second }.coerceAtLeast(1)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 20.dp)
            ) {
                Text(
                    text = formatRating(avgRating),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = AmberAccent,
                    fontSize = 36.sp
                )
                StarRow(rating = avgRating.roundToInt(), starSize = 14.dp)
                Text(
                    text = stringResource(R.string.reviews_total, totalReviews),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                distribution.forEach { (star, count) ->
                    RatingDistributionRow(
                        star = star,
                        count = count,
                        maxCount = maxCount
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingDistributionRow(star: Int, count: Int, maxCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = star.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            modifier = Modifier.width(12.dp),
            textAlign = TextAlign.End
        )
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(12.dp),
            tint = AmberAccent
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(BorderColor)
        ) {
            if (count > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(count.toFloat() / maxCount)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(AmberAccent)
                )
            }
        }
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            modifier = Modifier.width(20.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun ReviewCard(review: Review) {
    val customerLabel = review.customerName?.takeIf { it.isNotBlank() } ?: "—"
    val avatarLetter = customerLabel.firstOrNull()?.uppercaseChar()?.toString() ?: "?"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = avatarLetter,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }

                Column(modifier = Modifier.padding(start = 10.dp).weight(1f)) {
                    Text(
                        text = customerLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Text(
                        text = formatReviewDate(review.createdAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }

                StarRow(rating = review.rating, starSize = 14.dp)
            }

            review.comment?.takeIf { it.isNotBlank() }?.let { comment ->
                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    modifier = Modifier.padding(start = 46.dp, top = 10.dp)
                )
            }

            Text(
                text = "${stringResource(R.string.review_order)}: ${review.orderId ?: "—"}",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                modifier = Modifier.padding(start = 46.dp, top = 8.dp)
            )
        }
    }
}

@Composable
private fun StarRow(rating: Int, starSize: androidx.compose.ui.unit.Dp) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(5) { index ->
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(starSize),
                tint = if (index < rating) AmberAccent else BorderColor
            )
        }
    }
}
