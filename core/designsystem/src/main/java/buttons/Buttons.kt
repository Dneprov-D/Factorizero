package buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hfad.designsystem.components.theme.FactorizeroTheme
import com.hfad.designsystem.components.theme.FzBackground

// Core
@Composable
fun FzButtonCore(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
fun FzOutlinedButtonCore(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = FzButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = FzButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
                )
            },
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun FzTextButtonCore(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        content = content,
    )
}

// Custom Button's

@Composable
fun FzButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    FzButtonCore(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        FzButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
fun FzOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    FzOutlinedButtonCore(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        FzButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
fun FzTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    FzTextButtonCore(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        FzButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
private fun FzButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
    ) {
        text()
    }
}

//Preview's

@Preview(showBackground = true)
@Composable
fun FzTextButtonPreview() {
    FactorizeroTheme {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzTextButton(onClick = {}, text = { Text("Test button") })
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DarkFzButtonPreview() {
    FactorizeroTheme(darkTheme = true) {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkFzOutlinedButtonPreview() {
    FactorizeroTheme(darkTheme = true) {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzOutlinedButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkFzButtonLeadingIconPreview() {
    FactorizeroTheme(darkTheme = true) {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = Icons.FzIcons.Add, contentDescription = null) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkFzTextButtonPreview() {
    FactorizeroTheme(darkTheme = true) {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzTextButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FzButtonPreview() {
    FactorizeroTheme {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FzOutlinedButtonPreview() {
    FactorizeroTheme {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzOutlinedButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FzButtonLeadingIconPreview() {
    FactorizeroTheme {
        FzBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FzButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = Icons.FzIcons.Add, contentDescription = null) },
            )
        }
    }
}

object FzButtonDefaults {
    const val DISABLED_OUTLINED_BUTTON_BORDER_ALPHA = 0.12f
    val OutlinedButtonBorderWidth = 1.dp
}