package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// You need to create an abstract RoomDatabase class, annotated with @Database.
// This class has one method that either creates an instance of the RoomDatabase if it doesn't exist,
// or returns the existing instance of the RoomDatabase.

// The @Database annotation requires several arguments, so that Room can build the database.
// -> Specify the Item as the only class with the list of entities.
// -> Set the version as 1. Whenever you change the schema of the database table,
//      you'll have to increase the version number.
// -> Set exportSchema to false, so as not to keep schema version history backups
@Database(entities = [Item::class],version = 1,exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    // object. The companion object allows access to the methods for creating
    // or getting the database using the class name as the qualifier
    companion object{
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null
        fun getDatabase(context: Context): ItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            INSTANCE = instance
            return instance
            }
        }
    }
}