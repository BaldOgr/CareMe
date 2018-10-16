package kz.careme.android.model.di;

import javax.inject.Singleton;

import dagger.Component;
import kz.careme.android.model.Profiler;

@Component(modules = ProfileModule.class)
@SingletonScope
public interface ProfilerComponent {
    Profiler getProfiler();
}
