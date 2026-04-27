package com.example.morelifeforpets

import android.R.attr.contentDescription
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.morelifeforpets.model.PetEntity
import com.example.morelifeforpets.model.TutorEntity
import com.example.morelifeforpets.ui.components.menupopap
import com.example.morelifeforpets.ui.screens.PetViewModel
import com.example.morelifeforpets.ui.screens.TutorViewModel
import com.example.morelifeforpets.ui.theme.Azul_Marinho
import com.example.morelifeforpets.ui.theme.Azul_Rio1
import com.example.morelifeforpets.ui.theme.Azul_Rio2
import com.example.morelifeforpets.ui.theme.Cinza
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private val petViewModel: PetViewModel by viewModels()
    private val tutorViewModel: TutorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            val fontHome = FontFamily(Font(R.font.poppinssemibold))

            // Navegação entre telas , aqui e possivel entender a logica ao entrar em uma tela
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

                //composable("Usuario/{tutorCpf}") E um rotulo do que sera apresentado
                composable("Usuario/{tutorCpf}"){ backStackEntry ->
                    val cpf = backStackEntry.arguments?.getString("tutorCpf")
                    Telausuario(navController,petViewModel,tutorViewModel,cpf)
                }
                composable("NovoPet/{tutorCpf}"){ backStackEntry ->
                    val cpf = backStackEntry.arguments?.getString("tutorCpf")
                    novoPet(navController,petViewModel,cpf)

            }
                composable("Editar"){backStackEntry ->
                    popUpEditar(navController,tutorViewModel)


                }

            }
    }

}

@Composable
fun TelaInicial(navController: NavController) {

    // Column é um layout que organiza os seus filhos verticalmente, se usa de forma simples
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center)
        {

            Text(text = "More life  \nPets ", fontSize = 50.sp,
                fontFamily = FontFamily(Font(R.font.poppinssemibold)),)
            Spacer(modifier = Modifier.size(10.dp))

            Text(text = "Bem-Vindo", fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppinssemibold)),)
            Spacer(modifier = Modifier.size(30.dp))

        // Botão para cadatro, navController e responsavel por navegar entre as telas
        Button(onClick = {navController.navigate("Cadastro") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Azul_Marinho),shape = RectangleShape){
            //texto do botão
            Text(text = "Cadastro")
        }

        Button(onClick = {navController.navigate("Exibir")},
            colors = ButtonDefaults.buttonColors(
                containerColor = Azul_Marinho),shape = RectangleShape,){
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

        //Remember e para ajudar o compouse a lembrar do que se trata
        //By e substitui o variavel.value, simplificando o codigo
        //MutableState e onde se observa o que o usuario ira escrever

         var nomePet by remember { mutableStateOf("")}
        //Definição de campo de texto, com o link para o PetViewModel, pegando o atributo nomePet
        OutlinedTextField(
            value = nomePet,
            onValueChange = { nomePet = it },
            label = { Text("Nome Pet")}
        )

        var tipoPet by remember { mutableStateOf("")}

        //Chamando a função menupopap
        menupopap(
            especie = tipoPet,
            onEspecieChange = { NovaEscolha ->
                tipoPet = NovaEscolha
                })



        var idadePet by remember { mutableStateOf("")}
        OutlinedTextField(
            value = idadePet,
            onValueChange = { idadePet = it },
            label = {Text("Idade Pet")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Cadastro Tutor")
        var nomeTutor by remember { mutableStateOf("")}
        OutlinedTextField(
            value = nomeTutor,
            onValueChange = { nomeTutor = it },
            label = { Text("Nome Tutor")},


        )
        var tell  by remember { mutableStateOf("")}
        OutlinedTextField(
            value = tell,
            onValueChange = { tell = it},
            label = { Text ( "Telefone")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

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
            label = { Text ("CPF")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
                // Salva os dados no banco de dados Room
        Button(onClick = {
            val novoTutor = TutorEntity(nomeT = nomeTutor, email = email, cpf = cpf, tell = tell)
            val novoPet = PetEntity(nomeP = nomePet, tipo = tipoPet, idade = idadePet.toInt(), tutorCpf = cpf)

            // Chamar a função para salvar os dados no banco de dados
            petViewModel.salvarPet(novoPet)
            tutorViewModel.salvar(novoTutor)
            navController.navigate("Exibir")
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Azul_Marinho
            ),shape = RectangleShape )
        {Text(text = "Salvar")}

    }


}
    //OptIN e quando algo ainda esta em desenvolvimento
    @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exibir(navController: NavController, petViewModel: PetViewModel, tutorViewModel: TutorViewModel) {
    val listaDeTutor by tutorViewModel.todosOsTutores.collectAsState(initial = emptyList())
    val listaDePet by petViewModel.todasOsPet.collectAsState(initial = emptyList())
    Scaffold(topBar ={
        TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Azul_Marinho,
        titleContentColor = MaterialTheme.colorScheme.primary),
        title ={ Text (text = "Lista de Cadastrados",color = Cinza)})}){innerPadding ->
//LazyColumn é um layout que organiza para formatos mais complexos ou que necessita de agrupar os itens
        //Aqui e necessario os elemento esteja dentro de um Item{}
    LazyColumn(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {

        // Seção de Tutores Pega todos os tutores e apresenta.
        // item para apena 1 elementro - items para mais de 1 elemento
        items(listaDeTutor) { tutor ->
            val petDoTutor = listaDePet.find{ pet -> pet.tutorCpf == tutor.cpf }

            Card(modifier = Modifier.

                fillMaxWidth()
                .height( height = 150.dp)
                .padding(12.dp)
                .clickable { navController.navigate("Usuario/${tutor.cpf}") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Azul_Marinho)){
                Row (modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically) {


                Column(
                    modifier = Modifier.padding(16.dp)){
                    Text(text = "Tutor: ${tutor.nomeT} Email: ${tutor.email} \nTelefone: ${tutor.tell}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Cinza)
                    if (petDoTutor != null){
                        Text(text = "Pet: ${petDoTutor.nomeP} Tipo: ${petDoTutor.tipo} ",color = Cinza)
                    }
                    else{
                        Text(text = "Esse tutor não possui pets cadastrados",color = Cinza)
                    }
                    }
                    Column(modifier = Modifier.padding(5.dp)){


                    IconButton(onClick = {tutorViewModel.Deletar(tutor)},
                        modifier = Modifier.size(50.dp)){
                            Icon(
                                painter = painterResource(id = R.drawable.botaodedeletar),
                                contentDescription = "Delete",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(35.dp))
                        }


                    IconButton(onClick = {},
                        modifier = Modifier.size(50.dp)){
                        Icon(painter = painterResource(id = R.drawable.editar),
                            contentDescription = "Editar",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(35.dp))
                    }

            }

            }

        }
}}}}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Telausuario(navController: NavController,
                petViewModel: PetViewModel,
                tutorViewModel: TutorViewModel, cpf: String?) {
    // faz um push do banco de dados PetViewModel e TutorViewModel
    val listaDeTutor by tutorViewModel.todosOsTutores.collectAsState(initial = emptyList())
    val listaDePet by petViewModel.todasOsPet.collectAsState(initial = emptyList())

    val tutor = listaDeTutor.find { it.cpf == cpf }
    val pets = listaDePet.filter { it.tutorCpf == cpf }

    if (tutor == null || pets == null) {
        Text(text = "Tutor não encontrado")
        return
    }
    // AQui e onde fica o top bar do usuario
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Azul_Marinho,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                title = {
                    Text(text = tutor.nomeT,color = Cinza)},

                    actions = {
                        IconButton(onClick = {},
                            modifier =  Modifier.size(50.dp)){
                            Icon(painter = painterResource(id = R.drawable.editar),
                                contentDescription = "Editar",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(35.dp))
                        }
                    }
                )
        }) { innerPadding ->



    // card relacionandos aos pets

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){

                LazyColumn(

                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier
                    .weight(1f)
                        .fillMaxSize().
                    padding(9.dp)
                ) {
                    if(pets.isNotEmpty()){

                        items(pets){ pet ->
                            Card(modifier = Modifier.
                            size(width = 400.dp, height = 150.dp)
                                .fillMaxWidth()
                                .padding(12.dp) ,
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Azul_Marinho)){
                                Row(modifier = Modifier.padding(all = 8.dp)) {

                                    // inicindo uma imagem
                                    AsyncImage(
                                        model = R.drawable.gato,
                                        contentDescription = "Imagem do Pet",
                                        contentScale = ContentScale.Crop, // Garante que a imagem preencha o círculo perfeitamente
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape))
                                    Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.padding(16.dp)){
                                    Text(text = "Nome: ${pet.nomeP} \n Idade: ${pet.idade}",color = Cinza)
                                    Text(text = "Tipo: ${pet.tipo}",color = Cinza)
                                }

                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                    ){

                                        IconButton (onClick = {petViewModel.Deletar(pet)},
                                            modifier = Modifier.size(50.dp)){
                                         Icon(painter = painterResource(id = R.drawable.botaodedeletar),
                                             contentDescription = "Delete",
                                                     tint = Color.Unspecified,
                                             modifier = Modifier.size(35.dp))
                                        }

                                        IconButton (onClick = {},
                                                modifier = Modifier.size(35.dp)){
                                            Icon(painter = painterResource(id = R.drawable.editar),
                                                contentDescription = "Editar",
                                                tint = Color.Unspecified,
                                                modifier = Modifier.size(50.dp)

                                                )}

                                    }
                                }



                            }

                        }
                    }
                    else{
                        item{
                            Text(text = "Esse tutor não possui pets cadastrados", color = Cinza)
                        }
                    }
                }

            Button(onClick = {navController.navigate("NovoPet/${tutor.cpf}") },
                colors = ButtonDefaults.buttonColors( containerColor = Azul_Marinho),
                shape = RectangleShape) {
                Text(text = "+",color = Cinza)
            }}

        }
}

    @Composable

fun novoPet(navController: NavController, petViewModel: PetViewModel, cpf: String?) {


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Cadastro Pet")
            var nomePet by remember { mutableStateOf("") }
            OutlinedTextField(
                value = nomePet,
                onValueChange = { nomePet = it },
                label = { Text("Nome Pet") }
            )

            var tipoPet by remember { mutableStateOf("") }
            menupopap(
                especie = tipoPet,
                onEspecieChange = { NovaEscolha ->
                    tipoPet = NovaEscolha
                }
            )


            var idadePet by remember { mutableStateOf("") }
            OutlinedTextField(
                value = idadePet,
                onValueChange = { idadePet = it },
                label = { Text("Idade Pet") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    if (cpf != null) {
                        val novoPet = PetEntity(
                            nomeP = nomePet,
                            tipo = tipoPet,
                            idade = idadePet.toInt(),
                            tutorCpf = cpf
                        )
                        petViewModel.salvarPet(novoPet)
                        navController.popBackStack()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Azul_Marinho
                )
            ) {
                Text(text = "Salvar", color = Cinza)

            }
        }


    }}

fun popUpEditar(navController: NavController, tutorViewModel: TutorViewModel){

    

}


