package yqtrack.ap.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import yqtrack.ap.complex.ComplexManager

@Module
@InstallIn(ActivityComponent::class)
object ComplexModule {

    @Provides
    @ActivityScoped
    fun provideComplexManager(@ActivityContext activityContext: Context): ComplexManager {
        return ComplexManager(activityContext)
    }
}