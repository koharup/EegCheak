package app.sano.picchi.eegimage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import app.sano.picchi.eegimage.TutorialActivity.Companion.showForcibly
import app.sano.picchi.eegimage.TutorialActivity.Companion.showIfNeeded
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show.*
import kotlinx.android.synthetic.main.activity_walk_through_fragment.*
import java.util.*

class ShowActivity : AppCompatActivity() {

    //realm型の変数を宣言
    lateinit var realm: Realm

    var second = 15
    var count = 0

    //あとで15秒に修正
    var timer :CountDownTimer = object :CountDownTimer(3000,1000){

        override fun onFinish(){
            second = 15
            repertIfNeeded()
        }

        //1秒ごとに呼ばれる
        override fun onTick(millisUnitilFinished: Long){
            second = second - 1
            Log.d("memo",second.toString())
            countText.text = second.toString()
        }
    }


    fun repertIfNeeded(){

        //枚数分にする
        if (count <= 3) {
            //写真を変える処理をかく
            timer.cancel()
            timer.start()

            Log.d("Test",count.toString())

            count += 1

        }else if (count == 4){
            val intent = Intent(this,ResultActivity::class.java)
            startActivity(intent)
            timer.cancel()

            Log.d("Test",count.toString())


        }

        //listから持ってくる
        when(count){
            0 -> showImage.setImageURI(uriList[0])
            1 -> showImage.setImageURI(uriList[1])
            2 -> showImage.setImageURI(uriList[2])
            3 -> showImage.setImageURI(uriList[3])
            4 -> showImage.setImageURI(uriList[4])
        }


    }



    override fun onCreate(savedInstanceState: Bundle?) {

        //チュートリアルの表示
        showForcibly(this)

        //realmに脳波数値と画像を保存する


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        timer.start()

        //選択した画像を表示する

    }


    override fun onDestroy() {
        super.onDestroy()

        //realmを閉じる
        realm.close()
    }

}
