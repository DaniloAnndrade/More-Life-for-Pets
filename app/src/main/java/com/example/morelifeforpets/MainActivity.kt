package com.example.morelifeforpets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morelifeforpets.model.PetEntity
import com.example.morelifeforpets.model.TutorEntity
import com.example.morelifeforpets.ui.PetViewModel
import com.example.morelifeforpets.ui.TutorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private val petViewModel: PetViewModel by viewModels()
    private val tutorViewModel: TutorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{


            val navController =  rememberNavController()
            NavHost(navController = navController, startDestination = "inicial") {
                composable("inicial") {
                    TelaInicial(navController)
                }
                composable("Cadastro") {
                    TelaCadastro(navController,petViewModel,tutorViewModel)
                }
                composable("Exibir"){
                    Exibir(navController,petViewModel,tutorViewModel)
                }

            }

            }
    }

}

@Composable
fun TelaInicial(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){


        Text(text = "Bem-Vindo \nMore Life \nfor \nPets ")
        Button(onClick = {navController.navigate("Cadastro") }){
            Text(text = "Cadastro")
        }
        Button(onClick = {navController.navigate("Exibir")}){
            Text(text = "Entrar")}
}}
@Composable

fun TelaCadastro(navController: NavController, petViewModel: PetViewModel, tutorViewModel: TutorViewModel){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Cadastro Pet")
         var nomePet by remember { mutableStateOf("")}
        OutlinedTextField(
            value = nomePet,
            onValueChange = { nomePet = it },
            label = { Text("Nome Pet")}
        )

        var tipoPet by remember { mutableStateOf("")}
        OutlinedTextField(
            value = tipoPet,
            onValueChange = { tipoPet = it},
            label = {Text("Especie ex (Felino,Canidio)")}
        )
        var idadePet by remember { mutableStateOf("")}
        OutlinedTextField(
            value = idadePet,
            onValueChange = { idadePet = it},
            label = {Text("Idade Pet")}
        )
        Text(text = "Cadastro Tutor")
        var nomeTutor by remember { mutableStateOf("")}
        OutlinedTextField(
            value = nomeTutor,
            onValueChange = { nomeTutor = it },
            label = {Text("Nome Tutor")}

        )
        var tell  by remember { mutableStateOf("")}
        OutlinedTextField(
            value = tell,
            onValueChange = { tell = it},
            label = { Text ( "Telefone")}

        )
        var email by  remember { mutableStateOf("")}
        OutlinedTextField(
            value = email,
            onValueChange = { email= it},
            label = { Text ("Email")}
        )

        var cpf by remember { mutableStateOf("")}
        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it},
            label = { Text ("CPF")}
        )
                // Salva os dados no banco de dados Room
        Button(onClick = {
            val novoTutor = TutorEntity(nomeT = nomeTutor, email = email, cpf = cpf, tell = tell)
            val novoPet = PetEntity(nomeP = nomePet, tipo = tipoPet, idade = idadePet.toInt(), tutorCpf = cpf)

            petViewModel.salvarPet(novoPet)
            tutorViewModel.salvar(novoTutor)
            navController.navigate("Exibir")


        })
        {Text(text = "Salvar")}

    }


}

@Composable
fun Exibir(navController: NavController, petViewModel: PetViewModel, tutorViewModel: TutorViewModel) {
    val listaDeTutor by tutorViewModel.todosOsTutores.collectAsState(initial = emptyList())
    val listaDePet by petViewModel.todasOsPet.collectAsState(initial = emptyList())

    LazyColumn(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        item {// Título da página
        Text(text = "Lista de Cadastrados", style = MaterialTheme.typography.headlineMedium)

        }
        // Seção de Tutores }
        items(listaDeTutor) { tutor ->
            val petDoTutor = listaDePet.find{ pet -> pet.tutorCpf == tutor.cpf }

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)){
                Column(
                    modifier = Modifier.padding(16.dp)){
                    Text(text = "Tutor: ${tutor.nomeT} \nEmail: ${tutor.email} \nCPF: ${tutor.cpf} \nTelefone: ${tutor.tell}\n",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold)
                    if (petDoTutor != null){
                        Text(text = "Pet: ${petDoTutor.nomeP} \nTipo: ${petDoTutor.tipo} \nIdade: ${petDoTutor.idade}\n")
                    }
                    else{
                        Text(text = "Esse tutor não possui pets cadastrados")
                    }
            }
            }

        }
}}

