package br.com.fiap.sistemaestoquevinhos.database.repository

import android.content.Context
import br.com.fiap.sistemaestoquevinhos.database.dao.ProductDb
import br.com.fiap.sistemaestoquevinhos.model.Product

class ProductRepository(context: Context) {

    var db = ProductDb.getDatabase(context).produtoDao()

    fun save(product: Product): Long {
        return db.save(product = product)
    }

    fun update(product: Product): Int {
        return db.update(product = product)
    }

    fun delete(product: Product): Int {
        return db.delete(product = product)
    }

    fun searchProductById(id: Long): Product {
        return db.searchProductById(id = id)
    }

    fun listProducts(): List<Product> {
        return db.listProducts()
    }

}