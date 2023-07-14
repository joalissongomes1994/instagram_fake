package joalissongomes.dev.instagram.common.base

import android.content.Context
import joalissongomes.dev.instagram.add.data.AddLocalDataSource
import joalissongomes.dev.instagram.add.data.AddRepository
import joalissongomes.dev.instagram.add.data.FireAddDataSource
import joalissongomes.dev.instagram.home.data.FeedMemoryCache
import joalissongomes.dev.instagram.home.data.HomeDataSourceFactory
import joalissongomes.dev.instagram.home.data.HomeRepository
import joalissongomes.dev.instagram.login.data.FireLoginDataSource
import joalissongomes.dev.instagram.login.data.LoginRepository
import joalissongomes.dev.instagram.post.data.PostLocalDataSource
import joalissongomes.dev.instagram.post.data.PostRepository
import joalissongomes.dev.instagram.profile.data.PostListMemoryCache
import joalissongomes.dev.instagram.profile.data.ProfileDataSourceFactory
import joalissongomes.dev.instagram.profile.data.ProfileMemoryCache
import joalissongomes.dev.instagram.profile.data.ProfileRepository
import joalissongomes.dev.instagram.register.data.FireRegisterDataSource
import joalissongomes.dev.instagram.register.data.RegisterRepository
import joalissongomes.dev.instagram.search.data.FireSearchDataSource
import joalissongomes.dev.instagram.search.data.SearchRepository
import joalissongomes.dev.instagram.splash.data.FireSplashDataSource
import joalissongomes.dev.instagram.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository(): SplashRepository {
        return SplashRepository(FireSplashDataSource())
    }

    fun loginRepository(): LoginRepository {
        return LoginRepository(FireLoginDataSource())
    }

    fun registerRepository(): RegisterRepository {
        return RegisterRepository(FireRegisterDataSource())
    }

    fun profileRepository(): ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    fun homeRepository(): HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    fun addRepository(): AddRepository {
        return AddRepository(FireAddDataSource(), AddLocalDataSource())
    }

    fun postRepository(context: Context): PostRepository {
        return PostRepository(PostLocalDataSource(context))
    }

    fun searchRepository(): SearchRepository {
        return SearchRepository(FireSearchDataSource())
    }
}