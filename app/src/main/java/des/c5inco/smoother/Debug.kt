package des.c5inco.smoother

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import des.c5inco.smoother.SmootherShape
import des.c5inco.smoother.calculateSmoothRadius
import des.c5inco.smoother.ui.theme.SmootherTheme
import kotlin.math.max
import kotlin.math.min

@Preview
@Composable
fun Debug() {
    val squircleSize = DpSize(300.dp, 200.dp)
    var slidingRadius by remember { mutableStateOf(50.dp) }
    var slidingSmoothness by remember { mutableStateOf(0.6f) }
    var withStrokeStyle by remember { mutableStateOf(false) }

    SmootherTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                val styleModifier = if (withStrokeStyle) {
                    Modifier.border(2.dp, Color.Black, SmootherShape(
                        slidingRadius,
                        slidingSmoothness
                    )
                    )
                } else {
                    Modifier.background(
                        Color.Black.copy(0.7f),
                        SmootherShape(
                            slidingRadius,
                            slidingSmoothness
                        )
                    )
                }
                Box(
                    Modifier
                        .size(squircleSize)
                        .then(styleModifier)
                        .clickable(
                            onClick = { withStrokeStyle = !withStrokeStyle },
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        )
                ) { }
                DebugShape(
                    shapeSize = squircleSize,
                    radiusDp = slidingRadius.value.toInt(),
                    smoothness = slidingSmoothness
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Radius")
                Slider(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = slidingRadius.value,
                    onValueChange = { slidingRadius = it.dp },
                    valueRange = 0f..100f
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Smoothness")
                Slider(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = slidingSmoothness,
                    onValueChange = { slidingSmoothness = it }
                )
            }
        }
    }
}

@Composable
private fun DebugShape(
    shapeSize: DpSize,
    radiusDp: Int,
    smoothness: Float,
) {
    val density = LocalDensity.current.density
    val width = shapeSize.width.value * density
    val height = shapeSize.height.value * density
    val maxRadius = min(width, height) / 2
    val cornerRadius = min(radiusDp * density, maxRadius)

    val (a, b, c, d, p, angleTheta, circularSectionLength) = calculateSmoothRadius(
        width = width,
        height = height,
        cornerRadius = cornerRadius,
        smoothing = smoothness
    )

    Column {
        Canvas(
            Modifier.size(shapeSize)
        ) {
            // Top right
            debugCornerRadius(centerX = width - cornerRadius, centerY = cornerRadius, radius = cornerRadius)
            debugArc(topLeftX = width - cornerRadius * 2, topLeftY = 0f, radius = cornerRadius, startAngle = 270f, angleTheta = angleTheta)
            debugPoint(max(width / 2, width - p), 0f)
            debugPoint(width - (p - a - b - c), d)
            debugPoint(width - d, d + circularSectionLength)
            debugPoint(width, min(height / 2, p))

            // Bottom right
            debugCornerRadius(centerX = width - cornerRadius, centerY = height - cornerRadius, radius = cornerRadius)
            debugArc(topLeftX = width - cornerRadius * 2, topLeftY = height - cornerRadius * 2, radius = cornerRadius, startAngle = 0f, angleTheta = angleTheta)
            debugPoint(width, max(height / 2, height - p))
            debugPoint(width - d, height - (p - a - b - c))
            debugPoint(width - d - circularSectionLength, height - d)
            debugPoint(max(width / 2, width - p), height)

            // Bottom left
            debugCornerRadius(centerX = cornerRadius, centerY = height - cornerRadius, radius = cornerRadius)
            debugArc(topLeftX = 0f, topLeftY = height - cornerRadius * 2, radius = cornerRadius, startAngle = 90f, angleTheta = angleTheta)
            debugPoint(min(width / 2, p), height)
            debugPoint(p - a - b - c, height - d)
            debugPoint(d, height - d - circularSectionLength)
            debugPoint(0f, max(height / 2, height - p))

            // Top left
            debugCornerRadius(centerX = cornerRadius, centerY = cornerRadius, radius = cornerRadius)
            debugArc(topLeftX = 0f, topLeftY = 0f, radius = cornerRadius, startAngle = 180f, angleTheta = angleTheta)
            debugPoint(0f, min(height / 2, p))
            debugPoint(d, p - a - b - c)
            debugPoint(d + circularSectionLength, d)
            debugPoint(min(width / 2, p), 0f)
        }
        Spacer(Modifier.height(20.dp))
        Column {
            Text("cornerRadius: $cornerRadius")
            Text("maxRadius: $maxRadius")
            Text("a: $a")
            Text("b: $b")
            Text("c: $c")
            Text("d: $d")
            Text("p: $p")
            Text("angleTheta: $angleTheta")
            Text("circularSectionLength: $circularSectionLength")
        }
    }
}

private fun DrawScope.debugCornerRadius(centerX: Float, centerY: Float, radius: Float) {
    drawCircle(
        color = Color.Green.copy(alpha = 0.25f),
        radius = radius,
        center = Offset(centerX, centerY)
    )
}

private fun DrawScope.debugArc(topLeftX: Float, topLeftY: Float, radius: Float, startAngle: Float, angleTheta: Float) {
    drawArc(
        color = Color.Red.copy(alpha = 0.5f),
        startAngle = startAngle + angleTheta,
        sweepAngle = 90f - angleTheta * 2,
        useCenter = true,
        topLeft = Offset(topLeftX, topLeftY),
        size = Size(radius * 2, radius * 2)
    )
}

private fun DrawScope.debugPoint(x: Float, y: Float) {
    drawCircle(
        color = Color.Gray,
        style = Stroke(4f),
        radius = 8f,
        center = Offset(x, y)
    )
}