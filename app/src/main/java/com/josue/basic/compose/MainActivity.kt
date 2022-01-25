package com.josue.basic.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josue.basic.compose.ui.theme.BasicComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicComposeTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding)
        OnboardingScreen {
            shouldShowOnboarding = false
        }
    else
        Greetings()
}

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(items = names) { name ->
            Greeting(name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Olá,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = "Composem ipsum color sit lazy, padding theme elit, sed do bouncy. "
                            .repeat(4)
                    )
                }
            }
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
)
@Preview(
    showBackground = true,
    widthDp = 320,
)
@Composable
fun DefaultPreview() {
    BasicComposeTheme {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClick: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Bem vindo ao Codelab Básico!")
            Button(
                onClick = onContinueClick,
                modifier = Modifier.padding(vertical = 24.dp),
            ) {
                Text(text = "Continue")
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicComposeTheme {
        OnboardingScreen {}
    }
}
