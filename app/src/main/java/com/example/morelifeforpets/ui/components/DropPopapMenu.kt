package com.example.morelifeforpets.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import com.example.morelifeforpets.ui.theme.Cinza
import com.example.morelifeforpets.ui.theme.Teste_Verde

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun menupopap(
    especie: String,
    onEspecieChange: (String) -> Unit
){
    val menuItemData = listOf("Gato","Cachorro","Passaro","Outros")

    var expanded by remember {mutableStateOf(false)}
    var escoItem  = if ( especie.isEmpty()) "Selecione uma Especie" else especie

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ){

        OutlinedTextField(
            value = escoItem,
            onValueChange = {},
            readOnly = true,
            label ={Text("Espécie do Animal")},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = OutlinedTextFieldDefaults.colors(
                //Cor do fundo
                focusedContainerColor = Cinza,
                unfocusedContainerColor = Cinza,
                //Cor da Borda
                focusedBorderColor = Teste_Verde,
                unfocusedBorderColor = Teste_Verde,
                //Cor do Texto
                focusedLabelColor = Teste_Verde,
                unfocusedLabelColor = Teste_Verde
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false}
        ) {
            menuItemData.forEach { escoItem ->
                DropdownMenuItem(
                    text = {Text (escoItem)},
                    onClick = {
                        onEspecieChange(escoItem)
                        expanded = false

                    }
                )
            }

        }

    }}




