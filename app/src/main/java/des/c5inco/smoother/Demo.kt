package des.c5inco.smoother

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.smoother.ui.theme.SmootherTheme

@Preview
@Composable
fun Demo() {
    SmootherTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.7f)
                    .background(
                        Color.LightGray,
                        SmootherShape()
                    )
            ) { }
            Box(
                Modifier
                    .size(width = 86.dp, height = 36.dp)
                    .background(
                        Color.Black.copy(alpha = 0.6f),
                        SmootherShape(
                            radius = 6.dp
                        )
                    )
            ) { }
            Box(
                Modifier
                    .size(width = 330.dp, height = 94.dp)
                    .background(
                        Color.Black.copy(alpha = 0.7f),
                        SmootherShape(
                            radius = 16.dp
                        )
                    )
            ) { }
            Box(
                Modifier
                    .size(width = 330.dp, height = 29.dp)
                    .background(
                        Color.Black.copy(alpha = 0.7f),
                        SmootherShape(
                            radius = 8.dp
                        )
                    )
            ) { }
            Box(
                Modifier
                    .size(width = 60.dp, height = 47.dp)
                    .background(
                        Color.Black.copy(alpha = 0.7f),
                        SmootherShape(
                            radius = 16.dp
                        )
                    )
            ) { }
            Box(
                Modifier
                    .size(60.dp)
                    .background(
                        Color.Magenta,
                        SmootherShape()
                    )
            ) { }
            Box(
                Modifier
                    .height(60.dp)
                    .fillMaxWidth(0.5f)
                    .background(
                        Color.Cyan,
                        SmootherShape(
                            smoothness = 0f
                        )
                    )
            ) { }
            Box(
                Modifier
                    .size(300.dp, 200.dp)
                    .background(
                        Color.Black,
                        SmootherShape(
                            radius = 50.dp
                        )
                    )
            ) { }
        }
    }
}