package com.example.alertdialog

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertDialogAndDialog()
        }
    }
}

@Composable
fun AlertDialogAndDialog() {
    var showAlertDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                showAlertDialog = !showAlertDialog
            }
        ) {
            Text(text = "Open Alert Dialog")
        }
        if (showAlertDialog) {
            AlertDialogExample {
                showAlertDialog = !showAlertDialog
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                showDialog = !showDialog
            }
        ) {
            Text(text = "Open Dialog")
        }
        if (showDialog) {
            DialogExample(
                onDismiss = {
                    showDialog = !showDialog
                    Toast.makeText(context, "Dialog Dismissed!", Toast.LENGTH_SHORT).show()
                },
                onNegativeClick = {
                    showDialog = !showDialog
                    Toast.makeText(context, "Negative Button Clicked", Toast.LENGTH_SHORT).show()
                },
                onPositiveClick = {
                    showDialog = !showDialog
                    Toast.makeText(context, "Positive Button Clicked", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}


//Custom Alert Dialog
@Composable
fun AlertDialogExample(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            securePolicy = SecureFlagPolicy.Inherit
        ),
        title = {
            Text(
                text = "AlertDialog with Style",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(text = "This dialog has buttons with custom and aligned vertically as in Column. Properties has custom behaviour of a dialog such as dismissing when back button pressed or pressed outside of dialog")
        },
        buttons = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(percent = 30),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(percent = 30),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color(0xFF8BC34A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "OK")
            }
        }
    )
}

//Create custom Dialog
@Composable
fun DialogExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        val color = Color(0xFF4DB64C)
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(color = color)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_my_location_24),
                        contentDescription = "Location",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = 1.2f
                                scaleY = 1.2f
                            }
                            .align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.medium
                    ) {
                        Text(
                            text = "To Send a nearby place or your location, allow access to Your location."
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "NOT NOW",
                        color = color,
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple(color = Color.DarkGray),
                                onClick = onNegativeClick
                            )
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "CONTINUE",
                        color = color,
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple(color = Color.DarkGray),
                                onClick = onPositiveClick
                            )
                            .padding(8.dp)
                    )
                }

            }
        }
    }

}

