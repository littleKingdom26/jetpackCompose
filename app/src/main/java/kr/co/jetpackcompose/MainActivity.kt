package kr.co.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                onClick = onContinueClicked) {
                Text(text = "컨티뉴")
            }
        }
    }
}

@Composable
fun Greetings(names:List<String> = List(1000){"$it"}){
    LazyColumn(Modifier.padding(vertical = 4.dp)){
        items(items = names) {
            name -> Greeting(name = name)

        }

    }
}


@Composable
fun Greeting(name: String) {
    val expended = remember { mutableStateOf(false)}
    val extraPadding by animateDpAsState(if (expended.value) 48.dp else 0.dp ,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    ))



    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(26.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(onClick = { expended.value = !expended.value }) {
                Text(if (expended.value) "Show less" else "Show more")
            }

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


@Preview(showBackground = true,name="Text preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    JetpackComposeTheme {
        MyApp()
    }
}