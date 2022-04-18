package com.jaquelinebruzasco.onlynotes.di

import android.content.Context
import androidx.room.Room
import com.jaquelinebruzasco.onlynotes.domain.local.CategoryDatabase
import com.jaquelinebruzasco.onlynotes.domain.local.DatabaseConstants.Companion.CATEGORY_DATABASE_NAME
import com.jaquelinebruzasco.onlynotes.domain.local.DatabaseConstants.Companion.DATABASE_NAME
import com.jaquelinebruzasco.onlynotes.domain.local.OnlyNotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        OnlyNotesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideOnlyNotesDao(database: OnlyNotesDatabase) = database.onlyNotesDao()

    @Singleton
    @Provides
    fun provideCategoryDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CategoryDatabase::class.java,
        CATEGORY_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCategoryDao(database: CategoryDatabase) = database.categoryDao()
}