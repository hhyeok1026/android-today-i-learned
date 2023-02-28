package com.example.hiltexample


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject


interface AnalyticsService {
    fun analyticsMethods()
}

// Constructor-injected, because Hilt needs to konw how to
// provide instance of AnalyticsServiceImpl, too.
class AnalyticsServiceImpl @Inject constructor(

) : AnalyticsService {
    override fun analyticsMethods() {
        TODO("Not yet implemented")
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService
}

@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule2 {

    /*@Provides
    fun provideAnalyticsService(
        // Potential dependencies of this type
    ): AnalyticsService {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
            .create(AnalyticsService::class.java)
    }*/
}