package com.example.thindie.kodetrainee.presentation.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.thindie.kodetrainee.R


private val light = Font(R.font.sf_pro, FontWeight.W300)
private val regular = Font(R.font.sf_pro, FontWeight.W400)
private val medium = Font(R.font.sf_pro, FontWeight.W500)
private val semibold = Font(R.font.sf_pro, FontWeight.W600)

private val unActiveMedium = Font(R.font.inter_regular, FontWeight.W500)
private val activeMedium = Font(R.font.inter_bold, FontWeight.W600)

private val interRegular = FontFamily(Font(R.font.inter_regular))

private val KodeTraineeFonts =
    FontFamily(fonts = listOf(light, regular, medium, semibold, unActiveMedium, activeMedium))

val KodeTraineeTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = KodeTraineeFonts,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp
    ),
    displayMedium = TextStyle(
        fontFamily = KodeTraineeFonts,
        fontWeight = FontWeight.W400,
        fontSize = 60.sp
    ),
    displaySmall = TextStyle(
        fontFamily = KodeTraineeFonts,
        fontWeight = FontWeight.W600,
        fontSize = 48.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = interRegular,
        color = lightTextPrimary,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = interRegular,
        color = lightTextPrimary,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp
    ),
     headlineSmall = TextStyle(
         fontFamily = interRegular,
         color = lightTextPrimary,
         fontWeight = FontWeight.W400,
         fontSize = 20.sp
     ),
    titleLarge = TextStyle(
        fontFamily = interRegular,
        color = lightTextPrimary,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = interRegular,
        fontWeight = FontWeight.W600,
        fontSize = 17.sp
    ),

    titleMedium = TextStyle(
        fontFamily = interRegular,
        fontWeight = FontWeight.W600,
        color = lightContentActivePrimary,
        fontSize = 16.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = interRegular,
        color = lightTextTetriary,
        fontSize = 16.sp
    ),

    labelLarge = TextStyle(
        fontFamily = interRegular,
        fontWeight = FontWeight.W500,
        color = lightContentDefaultSecondary,
        fontSize = 15.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interRegular,
        color = lightTextTetriary,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = interRegular,
        color = lightTextSecondary,
        fontSize = 13.sp
    ),
)