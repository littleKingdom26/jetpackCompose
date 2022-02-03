package kr.co.jetpackcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
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
import kr.co.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(){
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true)}
    if(shouldShowOnboarding){
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding=false})
    }else{
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked:() -> Unit){
    Surface{
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("코드렙에 오신거 환영합니다.")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text(text = "컨티뉴")
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)

        }
    }
}


@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        CardContent(name)
    }
}

@Composable
private fun CardContent(name:String){

    var expended by remember { mutableStateOf(false)}
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(12.dp)) {
            Text("Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
            )
            if (expended) {
                Text(text = ("Composem ipsum color sit lazy, "+
                "padding theme elit, sad do bouncy. ").repeat(4),)
            }
        }
        IconButton(onClick = {expended = !expended}){
            Icon(
                imageVector = if(expended) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(expended){
                    stringResource(id = R.string.show_less)
                }else{
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    JetpackComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    JetpackComposeTheme {
        Greetings()
    }
}