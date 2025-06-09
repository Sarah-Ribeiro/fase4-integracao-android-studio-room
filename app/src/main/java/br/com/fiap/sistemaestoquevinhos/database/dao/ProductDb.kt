package br.com.fiap.sistemaestoquevinhos.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.sistemaestoquevinhos.model.Product

@Database(
    entities = [Product::class],
    version = 2
)
abstract class ProductDb : RoomDatabase() {

    abstract fun produtoDao(): ProductDao

    companion object {

        private lateinit var instance: ProductDb

        fun getDatabase(context: Context): ProductDb {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(context, ProductDb::class.java, "produto_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }

    }

}