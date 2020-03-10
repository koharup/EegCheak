package app.sano.picchi.eegimage

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stephentuso.welcome.*

class TutorialActivity : WelcomeActivity() {

    companion object {
        /**
         * まだ表示していなかったらチュートリアルを表示
         */
        fun showIfNeeded(activity: Activity, savedInstanceState: Bundle?) {
            WelcomeHelper(activity, TutorialActivity::class.java).show(savedInstanceState)
        }

        /**
         * 強制的にチュートリアルを表示したい時にはこっち
         */
        fun showForcibly(activity: Activity) {
            WelcomeHelper(activity, TutorialActivity::class.java).forceShow()
        }
    }

    /**
     * 表示するチュートリアル画面を定義する
     */
    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
            .defaultBackgroundColor(R.color.colorBack)
            .page(TitlePage(R.drawable.i4, "Museを接続してください"))

            .page(BasicPage(R.drawable.flower,
                "呼吸に集中してください",
                "始めます")
                .background(R.color.colorBack))
            .swipeToDismiss(false)
            .build()
        val intent = Intent(this,ShowActivity::class.java)
        startActivity(intent)


    }
}

