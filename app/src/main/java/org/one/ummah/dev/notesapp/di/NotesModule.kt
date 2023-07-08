package org.one.ummah.dev.notesapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.one.ummah.dev.notesapp.data.NotesDb
import org.one.ummah.dev.notesapp.mapper.NotesMapper
import org.one.ummah.dev.notesapp.mapper.NotesMapperImpl
import org.one.ummah.dev.notesapp.repositories.NotesRepository
import org.one.ummah.dev.notesapp.repositories.impl.NotesRepositoryImpl
import org.one.ummah.dev.notesapp.use_case.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotesModule {
    @Provides
    @Singleton
    fun getNotesDataBase(application: Application): NotesDb {
        return Room.databaseBuilder(
            application,
            NotesDb::class.java, NotesDb.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun getNotesRepository(db: NotesDb, mapper: NotesMapper): NotesRepository {
        return NotesRepositoryImpl(db, mapper)
    }

    @Provides
    @Singleton
    fun getNotesUseCase(notesRepository: NotesRepository): NotesUseCase {
        return NotesUseCase(
            GetNotesOrderedUseCase(notesRepository),
            DeleteNoteUseCase(notesRepository),
            AddOrUpdateNotesUseCase(notesRepository),
            GetNoteUseCase(notesRepository)
        )
    }


    @Provides
    @Singleton
    fun bindNotesMapper(): NotesMapper {
        return NotesMapperImpl()
    }
}