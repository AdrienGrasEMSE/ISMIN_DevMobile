package di

import android.content.Context
import androidx.room.Room
import com.anat.userlistapp.model.mapper.UserMapper
import com.anat.userlistapp.repository.AppDatabase
import com.anat.userlistapp.repository.UserDao
import com.anat.userlistapp.repository.UserRepository
import com.anat.userlistapp.repository.UserRepositoryImpl
import com.anat.userlistapp.service.RemoteService
import com.anat.userlistapp.service.RemoteServiceImpl
import com.anat.userlistapp.viewmodel.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindRemoteService(
        remoteServiceImpl : RemoteServiceImpl
    ):  RemoteService


    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://app-f94ff18e-2c86-4a83-9fe4-857eafc04801.cleverapps.io/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideUserMapper(): UserMapper = UserMapper()


        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideYourDao(database: AppDatabase): UserDao {
            return database.userDao()
        }

        @Provides
        fun provideUserListViewModel(userRepository: UserRepository): UserListViewModel {
            return UserListViewModel(userRepository)
        }
    }
}