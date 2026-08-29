package br.com.suaequipe.letramentodigital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Central de Letramento Digital") })
                    }
                ) { paddingValues ->
                    CadastroScreen(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun CadastroScreen(modifier: Modifier = Modifier) {
    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var mostrarDica by rememberSaveable { mutableStateOf(false) }
    var mensagemConfirmacao by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Cadastro do usuário", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = "Preencha seus dados para começar a usar os tutoriais de segurança digital.", fontSize = 16.sp)

        CadastroForm(
            nome = nome,
            onNomeChange = { nome = it },
            idade = idade,
            onIdadeChange = { idade = it },
            onCadastrar = {
                mensagemConfirmacao = if (nome.isBlank()) {
                    "Por favor, preencha o nome antes de continuar."
                } else {
                    "Cadastro realizado com sucesso, $nome!"
                }
            }
        )

        if (mensagemConfirmacao.isNotEmpty()) {
            Text(text = mensagemConfirmacao, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }

        Divider()

        Button(onClick = { mostrarDica = !mostrarDica }, modifier = Modifier.fillMaxWidth()) {
            Text(if (mostrarDica) "Ocultar dica de segurança" else "Mostrar dica de segurança")
        }

        if (mostrarDica) {
            DicaSegurancaCard()
        }
    }
}

@Composable
fun CadastroForm(
    nome: String,
    onNomeChange: (String) -> Unit,
    idade: String,
    onIdadeChange: (String) -> Unit,
    onCadastrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(value = nome, onValueChange = onNomeChange, label = { Text("Nome completo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = idade, onValueChange = onIdadeChange, label = { Text("Idade") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = onCadastrar, modifier = Modifier.fillMaxWidth()) {
            Text("Cadastrar")
        }
    }
}

@Composable
fun DicaSegurancaCard(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Dica de segurança", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Desconfie de cobranças via Pix enviadas por mensagens não solicitadas. Sempre confirme por telefone antes de pagar.")
            }
        }
    }
}