package app.sano.picchi.eegimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    fun clese(view:View){
        intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
