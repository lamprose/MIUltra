 package io.github.lamprose.mi_ultra.xp.hook

import android.graphics.BlendMode
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.lamprose.mi_ultra.utils.LogUtil
import io.github.lamprose.mi_ultra.utils.OwnSP
import io.github.lamprose.mi_ultra.utils.OwnSP.valueEqualDo
import io.github.lamprose.mi_ultra.utils.ktx.*

class SystemUIHook(lpparam: XC_LoadPackage.LoadPackageParam) : BaseHook(lpparam) {

    override fun hook() {
        "notification_icon_force_android_style".valueEqualDo(true) {
            "com.android.systemui.statusbar.notification.NotificationUtil".findClass()
                .hookAfterMethod(
                    "shouldSubstituteSmallIcon",
                    "com.android.systemui.statusbar.notification.ExpandedNotification".findClass()
                ) {
                    val bool1 = it.args[0].callMethodAs<Boolean>("isSubstituteNotification")
                    val bool2 = "com.android.systemui.statusbar.notification.MiuiNotificationCompat"
                        .callStaticMethodAs<Boolean>(
                            "isGrayscaleIcon",
                            it.args[0].callMethod("getNotification")
                        )
                    LogUtil.i("notification_icon_force_android_style success")
                    it.result = bool1 && !bool2
                }
        }
        "non_default_theme_notification_blur".valueEqualDo(true) {
            "com.miui.systemui.util.MiuiThemeUtils".findClass()
                .setReturnConstant("isDefaultSysUiTheme", result = true)
        }
        "notification_blur_enable".valueEqualDo(true) {
            "com.miui.blur.sdk.backdrop.BlurStyle".findClass()
                .setStaticObjectField(
                    "DEFAULT_LIGHT", "com.miui.blur.sdk.backdrop.BlurStyle${'$'}Builder".findClass()
                        .getConstructor().newInstance()
                        .callMethod(
                            "setBlurRadius",
                            OwnSP.getInt("notification_blur_radius", 8)
                        )!!
                        .callMethod(
                            "addBlendLayer",
                            OwnSP.getInt("notification_blur_radius_light_color_burn", -2074585000),
                            BlendMode.COLOR_BURN
                        )!!
                        .callMethod(
                            "addBlendLayer",
                            OwnSP.getInt("notification_blur_radius_light_color", 0x40e3e3e3),
                            null
                        )!!
                        .callMethod("build")
                )
            "notification_blur_enable".valueEqualDo(true) {
                "com.miui.blur.sdk.backdrop.BlurStyle".findClass()
                    .setStaticObjectField(
                        "HEAVY_LIGHT",
                        "com.miui.blur.sdk.backdrop.BlurStyle${'$'}Builder".findClass()
                            .getConstructor().newInstance()
                            .callMethod(
                                "setBlurRadius",
                                OwnSP.getInt("notification_blur_radius", 8)
                            )!!
                            .callMethod(
                                "addBlendLayer",
                                OwnSP.getInt(
                                    "notification_blur_radius_light_color_burn",
                                    -2074585000
                                ),
                                BlendMode.COLOR_BURN
                            )!!
                            .callMethod(
                                "addBlendLayer",
                                OwnSP.getInt("notification_blur_radius_light_color", 0x40e3e3e3),
                                null
                            )!!
                            .callMethod("build")
                    )

            }
        }

    }
}