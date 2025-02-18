package com.example.notesapp.core.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.add_note.domain.usecases.SearchImagesUseCase
import com.example.notesapp.add_note.domain.usecases.UpsertNoteUseCase
import com.example.notesapp.core.data.local.NoteDb
import com.example.notesapp.core.data.remote.RemoteConstants
import com.example.notesapp.core.data.remote.api.ImagesApi
import com.example.notesapp.core.data.repository.ImagesRepositoryImpl
import com.example.notesapp.core.data.repository.NoteRepositoryImpl
import com.example.notesapp.core.domain.repository.ImagesRepository
import com.example.notesapp.core.domain.repository.NoteRepository
import com.example.notesapp.note_list.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.note_list.domain.use_case.GetAllNotesUseCase
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
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.databaseBuilder(application, NoteDb::class.java, "note_db.db").build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDb: NoteDb): NoteRepository {
        return NoteRepositoryImpl(noteDb)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(noteRepository: NoteRepository): UpsertNoteUseCase {
        return UpsertNoteUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(RemoteConstants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideImagesApi(retrofit: Retrofit): ImagesApi {
        return retrofit.create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCase(imagesRepository: ImagesRepository): SearchImagesUseCase {
        return SearchImagesUseCase(imagesRepository)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(imagesApi: ImagesApi): ImagesRepository {
        return ImagesRepositoryImpl(imagesApi)
    }
}