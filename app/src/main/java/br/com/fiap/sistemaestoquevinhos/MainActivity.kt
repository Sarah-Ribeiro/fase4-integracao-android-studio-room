package br.com.fiap.sistemaestoquevinhos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.sistemaestoquevinhos.database.repository.ProductRepository
import br.com.fiap.sistemaestoquevinhos.model.Product
import br.com.fiap.sistemaestoquevinhos.ui.theme.SistemaestoquevinhosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SistemaestoquevinhosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF8F8F8) // Um branco suave para o fundo principal
                ) {
                    ProductScreen() // Não precisa mais de Column aqui, a ProductScreen fará isso
                }
            }
        }
    }
}

@Composable
fun ProductScreen() {
    val nomeState = remember { mutableStateOf("") }
    val safraState = remember { mutableStateOf("") }
    val tipoState = remember { mutableStateOf("") }
    val paisOrigemState = remember { mutableStateOf("") }
    val quantidadeEmEstoqueState = remember { mutableStateOf("") }

    val nomeErrorState = remember { mutableStateOf(false) }
    val safraErrorState = remember { mutableStateOf(false) }
    val tipoErrorState = remember { mutableStateOf(false) }
    val paisOrigemErrorState = remember { mutableStateOf(false) }
    val quantidadeEmEstoqueErrorState = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val productRepository = ProductRepository(context)

    val listProductsState = remember {
        mutableStateOf(productRepository.listProducts())
    }

    val produtoEmEdicao = remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Permite rolagem de todo o conteúdo da tela
    ) {
        // --- Header da Aplicação ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF8B0000)) // Fundo bordô para o cabeçalho
                .padding(vertical = 24.dp) // Mais padding vertical
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_wine_bar_24),
                    contentDescription = stringResource(id = R.string.wine_icon),
                    modifier = Modifier
                        .size(40.dp) // Ícone um pouco maior
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Vinheria Agnello",
                    fontSize = 30.sp, // Tamanho de fonte ajustado
                    fontWeight = FontWeight.ExtraBold, // Mais destaque
                    color = Color.White // Texto branco para contraste
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // Espaço após o cabeçalho

        ProductForm(
            nome = nomeState.value,
            safra = safraState.value,
            tipo = tipoState.value,
            paisOrigem = paisOrigemState.value,
            quantidadeEmEstoque = quantidadeEmEstoqueState.value,
            onNomeChange = {
                nomeState.value = it
                nomeErrorState.value = false
            },
            onSafraChange = {
                safraState.value = it
                safraErrorState.value = false
            },
            onTipoChange = {
                tipoState.value = it
                tipoErrorState.value = false
            },
            onPaisOrigemChange = {
                paisOrigemState.value = it
                paisOrigemErrorState.value = false
            },
            onQuantidadeEmEstoqueChange = {
                quantidadeEmEstoqueState.value = it
                quantidadeEmEstoqueErrorState.value = false
            },
            atualizar = {
                listProductsState.value = productRepository.listProducts()
            },
            produtoEmEdicao = produtoEmEdicao,
            limparCampos = {
                nomeState.value = ""
                safraState.value = ""
                tipoState.value = ""
                paisOrigemState.value = ""
                quantidadeEmEstoqueState.value = ""
                nomeErrorState.value = false
                safraErrorState.value = false
                tipoErrorState.value = false
                paisOrigemErrorState.value = false
                quantidadeEmEstoqueErrorState.value = false
            },
            nomeError = nomeErrorState.value,
            safraError = safraErrorState.value,
            tipoError = tipoErrorState.value,
            paisOrigemError = paisOrigemErrorState.value,
            quantidadeEmEstoqueError = quantidadeEmEstoqueErrorState.value,
            onNomeErrorChange = { nomeErrorState.value = it },
            onSafraErrorChange = { safraErrorState.value = it },
            onTipoErrorChange = { tipoErrorState.value = it },
            onPaisOrigemErrorChange = { paisOrigemErrorState.value = it },
            onQuantidadeEmEstoqueErrorChange = { quantidadeEmEstoqueErrorState.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espaço entre o formulário e a lista

        ProductList(
            listProducts = listProductsState,
            atualizar = {
                listProductsState.value = productRepository.listProducts()
            },
            produtoEmEdicao = produtoEmEdicao,
            onNomeChange = { nomeState.value = it },
            onSafraChange = { safraState.value = it },
            onTipoChange = { tipoState.value = it },
            onPaisOrigemChange = { paisOrigemState.value = it },
            onQuantidadeEmEstoqueChange = { quantidadeEmEstoqueState.value = it }
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espaço no final da lista
    }
}

@Composable
fun ProductForm(
    nome: String,
    safra: String,
    tipo: String,
    paisOrigem: String,
    quantidadeEmEstoque: String,
    onNomeChange: (String) -> Unit,
    onSafraChange: (String) -> Unit,
    onTipoChange: (String) -> Unit,
    onPaisOrigemChange: (String) -> Unit,
    onQuantidadeEmEstoqueChange: (String) -> Unit,
    atualizar: () -> Unit,
    produtoEmEdicao: MutableState<Product?>,
    limparCampos: () -> Unit,
    nomeError: Boolean,
    safraError: Boolean,
    tipoError: Boolean,
    paisOrigemError: Boolean,
    quantidadeEmEstoqueError: Boolean,
    onNomeErrorChange: (Boolean) -> Unit,
    onSafraErrorChange: (Boolean) -> Unit,
    onTipoErrorChange: (Boolean) -> Unit,
    onPaisOrigemErrorChange: (Boolean) -> Unit,
    onQuantidadeEmEstoqueErrorChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val productRepository = ProductRepository(context)
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp) // Padding horizontal para o formulário
    ) {
        Text(
            text = "Cadastro de Vinhos",
            fontSize = 26.sp, // Tamanho ligeiramente menor para o título da seção
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B0000) // Cor bordô para o título
        )
        Spacer(modifier = Modifier.height(16.dp)) // Mais espaço após o título da seção

        // Campos de Texto
        OutlinedTextField(
            value = nome,
            onValueChange = { onNomeChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nome do Vinho") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            isError = nomeError,
            singleLine = true
        )
        if (nomeError) {
            Text(
                text = "O nome do vinho não pode ser vazio.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp)) // Espaçamento consistente entre campos

        OutlinedTextField(
            value = safra,
            onValueChange = { onSafraChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Safra (Ano)") }, // Label mais claro
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = safraError,
            singleLine = true
        )
        if (safraError) {
            Text(
                text = "A safra deve ser um ano válido.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = tipo,
            onValueChange = { onTipoChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Tipo de Vinho") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            isError = tipoError,
            singleLine = true
        )
        if (tipoError) {
            Text(
                text = "O tipo de vinho não pode ser vazio.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = paisOrigem,
            onValueChange = { onPaisOrigemChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "País de Origem") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            isError = paisOrigemError,
            singleLine = true
        )
        if (paisOrigemError) {
            Text(
                text = "O país de origem não pode ser vazio.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = quantidadeEmEstoque,
            onValueChange = { onQuantidadeEmEstoqueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Quantidade em Estoque") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = quantidadeEmEstoqueError,
            singleLine = true
        )
        if (quantidadeEmEstoqueError) {
            Text(
                text = "A quantidade deve ser um número válido.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp)) // Mais espaço antes do botão

        Button(
            onClick = {
                var hasError = false

                if (nome.isBlank()) {
                    onNomeErrorChange(true)
                    hasError = true
                } else {
                    onNomeErrorChange(false)
                }

                // Considerar validação de ano para safra (ex: 4 dígitos)
                if (safra.isBlank() || safra.toIntOrNull() == null || safra.length != 4) {
                    onSafraErrorChange(true)
                    hasError = true
                } else {
                    onSafraErrorChange(false)
                }

                if (tipo.isBlank()) {
                    onTipoErrorChange(true)
                    hasError = true
                } else {
                    onTipoErrorChange(false)
                }

                if (paisOrigem.isBlank()) {
                    onPaisOrigemErrorChange(true)
                    hasError = true
                } else {
                    onPaisOrigemErrorChange(false)
                }

                if (quantidadeEmEstoque.isBlank() || quantidadeEmEstoque.toIntOrNull() == null || quantidadeEmEstoque.toInt() < 0) { // Validação de número positivo
                    onQuantidadeEmEstoqueErrorChange(true)
                    hasError = true
                } else {
                    onQuantidadeEmEstoqueErrorChange(false)
                }

                if (hasError) {
                    Toast.makeText(
                        context,
                        "Por favor, preencha todos os campos corretamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                val product = Product(
                    id = produtoEmEdicao.value?.id ?: 0,
                    nome = nome,
                    safra = safra,
                    tipo = tipo,
                    paisOrigem = paisOrigem,
                    // Garante que quantidade é um Int
                    quantidadeEmEstoque = quantidadeEmEstoque
                )

                if (produtoEmEdicao.value == null) {
                    productRepository.save(product)
                    Toast.makeText(context, "Vinho adicionado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    productRepository.update(product)
                    Toast.makeText(context, "Vinho atualizado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    produtoEmEdicao.value = null
                }

                atualizar()
                limparCampos()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Altura fixa para o botão
                .clip(RoundedCornerShape(8.dp)), // Cantos mais arredondados
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
        ) {
            Text(
                text = if (produtoEmEdicao.value == null) "ADICIONAR VINHO" else "ATUALIZAR VINHO",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold, // Um pouco mais de peso na fonte
                color = Color.White
            )
        }
    }
}

@Composable
fun ProductList(
    listProducts: MutableState<List<Product>>,
    atualizar: () -> Unit,
    produtoEmEdicao: MutableState<Product?>,
    onNomeChange: (String) -> Unit,
    onSafraChange: (String) -> Unit,
    onTipoChange: (String) -> Unit,
    onPaisOrigemChange: (String) -> Unit,
    onQuantidadeEmEstoqueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Preenche a largura, mas não a altura para permitir o verticalScroll do pai
            .padding(horizontal = 16.dp) // Padding horizontal para a lista
    ) {
        Text(
            text = "Seu Estoque",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B0000)
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espaço após o título da seção

        if (listProducts.value.isEmpty()) {
            // Empty State - quando não há produtos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_wine_bar_24), // Reutilize o ícone do vinho
                    contentDescription = "Estoque Vazio",
                    modifier = Modifier.size(80.dp),
                    tint = Color(0xFFCCCCCC) // Uma cor mais neutra para o ícone de vazio
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Seu estoque está vazio!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Adicione seu primeiro vinho para começar a gerenciar.",
                    fontSize = 16.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Lista de Produtos
            listProducts.value.forEach { product ->
                ProductCard(
                    product = product,
                    atualizar = atualizar,
                    produtoEmEdicao = produtoEmEdicao,
                    onNomeChange = onNomeChange,
                    onSafraChange = onSafraChange,
                    onTipoChange = onTipoChange,
                    onPaisOrigemChange = onPaisOrigemChange,
                    onQuantidadeEmEstoqueChange = onQuantidadeEmEstoqueChange
                )
                Spacer(modifier = Modifier.height(12.dp)) // Espaço entre os cards
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    atualizar: () -> Unit,
    produtoEmEdicao: MutableState<Product?>,
    onNomeChange: (String) -> Unit,
    onSafraChange: (String) -> Unit,
    onTipoChange: (String) -> Unit,
    onPaisOrigemChange: (String) -> Unit,
    onQuantidadeEmEstoqueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, // Sombra mais sutil
                shape = RoundedCornerShape(12.dp) // Cantos mais arredondados
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp) // Mais padding interno no card
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // Usa o peso para o texto ocupar o espaço restante
            ) {
                // Melhorando a apresentação das informações:
                ProductInfoRow(label = "Nome do vinho:", value = product.nome)
                ProductInfoRow(label = "Tipo de vinho:", value = product.tipo)
                ProductInfoRow(label = "Safra:", value = product.safra)
                ProductInfoRow(label = "País de origem:", value = product.paisOrigem)
                ProductInfoRow(label = "Em estoque:", value = product.quantidadeEmEstoque)
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    produtoEmEdicao.value = product
                    onNomeChange(product.nome)
                    onSafraChange(product.safra)
                    onTipoChange(product.tipo)
                    onPaisOrigemChange(product.paisOrigem)
                    onQuantidadeEmEstoqueChange(product.quantidadeEmEstoque)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF666666), // Cinza escuro para o ícone de editar
                        modifier = Modifier.size(24.dp) // Tamanho padrão
                    )
                }
                IconButton(onClick = {
                    val productRepository = ProductRepository(context = context)
                    productRepository.delete(product)
                    atualizar()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar",
                        tint = Color(0xFFD32F2F), // Vermelho para deletar
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

// Componente auxiliar para padronizar a exibição de informações no card
@Composable
fun ProductInfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 4.dp)) { // Espaçamento entre as linhas
        Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(text = value, fontSize = 15.sp, modifier = Modifier.padding(start = 4.dp))
    }
}