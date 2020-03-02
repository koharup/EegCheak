package app.sano.picchi.eegimage

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.database.getStringOrNull
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton.setOnClickListener {
            Toast.makeText(this, "接続されました", Toast.LENGTH_SHORT).show();

        }





    }

    fun getImage(context: Context){

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
