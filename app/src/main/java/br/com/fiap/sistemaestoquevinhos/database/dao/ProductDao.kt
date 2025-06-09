package br.com.fiap.sistemaestoquevinhos.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.sistemaestoquevinhos.model.Product

@Dao
interface ProductDao {

    @Insert
    fun save(product: Product): Long

    @Update
    fun update(product: Product): Int

    @Delete
    fun delete(product: Product): Int

    @Query("SELECT * FROM tbl_produtos WHERE id = :id")
    fun searchProductById(id: Long): Product

    @Query("SELECT * FROM tbl_produtos ORDER BY nome ASC")
    fun listProducts(): List<Product>

}