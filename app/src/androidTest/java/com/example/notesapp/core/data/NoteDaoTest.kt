package com.example.notesapp.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.notesapp.core.data.local.NoteDao
import com.example.notesapp.core.data.local.NoteDb
import com.example.notesapp.core.data.local.NoteEntity
import com.example.notesapp.core.di.AppModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var noteDb: NoteDb
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        hiltRule.inject()
        noteDao = noteDb.noteDao
    }

    @After
    fun tearDown() {
        noteDb.close()
    }

    @Test
    fun getAllNotesFromEmptyDb_noteListIsEmpty() = runTest {
        assertThat(noteDao.getAllNoteEntities().isEmpty()).isTrue()
    }

    @Test
    fun getAllNotesFromDb_noteListIsNotEmpty() = runTest {
        for (i in 1..4) {
            val noteEntity = NoteEntity(
                id = i,
                title = "title $i",
                description = "description $i",
                imageUrl = "image $i",
                dateAdded = System.currentTimeMillis()
            )
            noteDao.upsertNoteEntity(noteEntity)
        }
        assertThat(noteDao.getAllNoteEntities().isNotEmpty()).isTrue()
    }

    @Test
    fun upsertNote_noteIsUpserted() = runTest {

        val noteEntity = NoteEntity(
            id = 1,
            title = "title",
            description = "description",
            imageUrl = "image",
            dateAdded = System.currentTimeMillis()
        )
        noteDao.upsertNoteEntity(noteEntity)
        assertThat(
            noteDao.getAllNoteEntities().contains(noteEntity)
        )
    }

    @Test
    fun deleteNote_noteIsDeleted() = runTest {
        val noteEntity = NoteEntity(
            id = 1,
            title = "title",
            description = "description",
            imageUrl = "image",
            dateAdded = System.currentTimeMillis()
        )
        noteDao.upsertNoteEntity(noteEntity)
        noteDao.deleteNoteEntity(noteEntity)
        assertThat(noteDao.getAllNoteEntities().contains(noteEntity).not())
    }
}