package br.com.fiap.sistemaestoquevinhos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_produtos")
data class Product(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nome: String = "",
    var safra: String = "",
    var tipo: String = "",
    var paisOrigem: String = "",
    var quantidadeEmEstoque: String = ""

)
