package ni.edu.uam.interfazinteractiva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.interfazinteractiva.ui.theme.InterfazInteractivaTheme

/**
 * Actividad principal de la aplicación.
 * Aquí se carga la pantalla del login del estudiante.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterfazInteractivaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LoginEstudianteScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Composable principal de la pantalla.
 *
 * Funcionalidades:
 * - Muestra una imagen local
 * - Permite cambiar la imagen con un botón
 * - Permite ingresar usuario y contraseña
 * - Valida datos básicos
 * - Muestra mensajes dinámicos
 * - Permite limpiar los campos
 */
@Composable
fun LoginEstudianteScreen(modifier: Modifier = Modifier) {

    /**
     * Estados para los campos de texto y mensajes.
     * remember mantiene los valores durante la recomposición.
     */
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Ingresa tus datos") }

    /**
     * Índice de la imagen actual.
     * Sirve para recorrer las imágenes como una mini galería.
     */
    var indiceImagen by remember { mutableStateOf(0) }

    /**
     * Lista de imágenes locales almacenadas en drawable.
     * Cada vez que se presiona el botón "Cambiar imagen",
     * se muestra la siguiente imagen de la lista.
     */
    val listaImagenes = listOf(
        R.drawable.dog,
        R.drawable.pink,
        R.drawable.orange
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEAF2F8))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF3EFF4)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /**
                 * Título principal de la pantalla.
                 */
                Text(
                    text = "Login Estudiante",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF184E77)
                )

                Spacer(modifier = Modifier.height(8.dp))

                /**
                 * Mensaje dinámico que cambia según la interacción del usuario.
                 */
                Text(
                    text = mensaje,
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(18.dp))

                /**
                 * Imagen actual de la galería.
                 * Se carga desde recursos locales con painterResource.
                 */
                Image(
                    painter = painterResource(id = listaImagenes[indiceImagen]),
                    contentDescription = "Imagen del estudiante",
                    modifier = Modifier.size(130.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Botón para cambiar la imagen actual.
                 * Recorre la lista de imágenes y vuelve al inicio cuando llega al final.
                 */
                Button(
                    onClick = {
                        indiceImagen = (indiceImagen + 1) % listaImagenes.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5C6FAE)
                    )
                ) {
                    Text(
                        text = "Cambiar foto perfil",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Campo para ingresar el usuario.
                 */
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                /**
                 * Campo para ingresar la contraseña.
                 * El texto se oculta por seguridad.
                 */
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                /**
                 * Botón para validar el inicio de sesión.
                 *
                 * Reglas:
                 * - Si hay campos vacíos, muestra advertencia.
                 * - Si usuario = "estudiante" y contraseña = "1234", acceso correcto.
                 * - En otro caso, datos incorrectos.
                 */
                Button(
                    onClick = {
                        mensaje = when {
                            usuario.isBlank() || password.isBlank() ->
                                "Completa todos los campos"

                            usuario == "estudiante" && password == "1234" ->
                                "BIENVENIDOOO $usuario"

                            else ->
                                "Datos incorrectos"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5C6FAE)
                    )
                ) {
                    Text(
                        text = "Ingresar",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                /**
                 * Botón para limpiar los campos.
                 * También restablece el mensaje principal.
                 */
                Button(
                    onClick = {
                        usuario = ""
                        password = ""
                        mensaje = "Campos clean"
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8E44AD)
                    )
                ) {
                    Text(
                        text = "Limpiar",
                        color = Color.White
                    )
                }
            }
        }
    }
}