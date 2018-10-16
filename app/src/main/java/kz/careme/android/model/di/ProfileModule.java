package kz.careme.android.model.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.careme.android.model.Profiler;

@Module
class ProfileModule {
    @Provides
    @SingletonScope
    Profiler getProfiler() {
        return new Profiler();
    }
}
