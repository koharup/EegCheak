package app.sano.picchi.eegimage

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.database.getStringOrNull
import app.sano.picchi.eegimage.MainActivity.Companion.REQUEST_CODE_PHOTO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_walk_through_fragment.*
import java.io.File

class StartActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


    }


    fun start(view: View) {

        //写真を取ってくる
        //getImage(applicationContext)
        val intent = Intent(this, ShowActivity::class.java)
        startActivity(intent)


    }


    fun getImage(context: Context) {

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        context.contentResolver.query(
            collection,
            null,
            null,
            null,
            null
        )?.use launch@{ cursor ->
            cursor.moveToFirst()
            // 画像のUri一覧
            val fileUris: MutableList<File> = mutableListOf()
            for (i in 0 until cursor.count) {
                val path =
                    cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                        ?: return@launch
                fileUris.add(File(path))
                cursor.moveToNext()
            }
        } ?: return


    }


}
