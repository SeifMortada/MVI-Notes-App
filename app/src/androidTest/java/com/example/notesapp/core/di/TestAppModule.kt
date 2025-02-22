package com.example.notesapp.core.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.add_note.domain.usecases.SearchImagesUseCase
import com.example.notesapp.add_note.domain.usecases.UpsertNoteUseCase
import com.example.notesapp.core.data.local.NoteDb
import com.example.notesapp.core.data.remote.RemoteConstants
import com.example.notesapp.core.data.remote.api.ImagesApi
import com.example.notesapp.core.data.repository.FakeAndroidImagesRepository
import com.example.notesapp.core.data.repository.FakeAndroidNoteRepository
import com.example.notesapp.core.domain.repository.ImagesRepository
import com.example.notesapp.core.domain.repository.NoteRepository
import com.example.notesapp.note_list.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.note_list.domain.use_case.GetAllNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.inMemoryDatabaseBuilder(application, NoteDb::class.java).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository {
        return FakeAndroidNoteRepository()
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
    fun provideImagesApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(RemoteConstants.BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFakeImagesRepository(): ImagesRepository {
        return FakeAndroidImagesRepository()
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCase(imagesRepository: ImagesRepository): SearchImagesUseCase {
        return SearchImagesUseCase(imagesRepository)
    }
}