package com.example.hiltexample

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject
import javax.inject.Qualifier



@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() {

    @Inject lateinit var analytics: AnalyticsAdapter


}

@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {
    @Provides
    fun provideAnalyticsService(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): AnalyticsService {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .client(okHttpClient)
            .build()
            .create(AnalyticsService::class.java)
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient