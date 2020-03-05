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
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show.*
import java.util.*

class ShowActivity : AppCompatActivity() {

    //realm型の変数を宣言
    lateinit var realm: Realm
    lateinit var bitmap: Bitmap

    var second = 15
    var timer :CountDownTimer = object :CountDownTimer(15000,1000){
        override fun onFinish(){
            second = 15
            val intent = Intent(this@ShowActivity,ResultActivity::class.java)
            startActivity(intent)
        }

        //1秒ごとに呼ばれる
        override fun onTick(millisUnitilFinished: Long){
            second = second - 1
            Log.d("memo",second.toString())
            countText.text = second.toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        /*//memoから画像をとってくる
        val memo
                = realm.where(Memo::class.java).equalTo(
            "updateDate",
            this.intent.getStringExtra("updateDate")
        ).findFirst()*/


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        timer.start()

        //選択した画像を表示する
        //bitmap = BitmapFactory.decodeByteArray(memo?.bitmap, 0, memo?.bitmap?.size!!)
        //showImage.setImageBitmap(bitmap)

        //チュートリアルの表示
        //showIfNeeded(activity,savedInstanceState)

//        for (i in 1..2){
//            if (second == 0){
//                timer.start()
//                imageView.setImageResource(R.drawable.i2)
//            }
//        }




    }





    fun move(view: View){
        intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        //realmを閉じる
        realm.close()
    }




}
