package app.sano.picchi.eegimage

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_image_verification.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File

class ImageVerificationActivity : AppCompatActivity() {

    //カメラ
    lateinit var pictureUri: Uri
    var bitmap: Bitmap? = null

    //アルバムとカメラ
    companion object {
        const val REQUEST_CODE_PHOTO: Int = 123
        const val REQUEST_CODE_PERMISSION: Int = 100
        const val REQUEST_CODE_CAMERA: Int = 200
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_verification)
    }

    fun start(view: View){
        val intent = Intent(this,ShowActivity::class.java)
        startActivity(intent)
    }


    fun camera(view: View){
        cameraTask()
    }

    fun alubum(view: View){
        val intent: Intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, MainActivity.REQUEST_CODE_PHOTO)
        Log.d("camera","camera")
    }

    fun createImageData(bitmap: Bitmap) : ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val imageByteArray = baos.toByteArray()
        return imageByteArray
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //listを作る
        val fileUris: MutableList<File> = mutableListOf()

        Log.d("onActivityresult","onActivityresult")

        if (requestCode == MainActivity.REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
            pictureUri = data?.data!!

            //これを5回繰り返したいで、listか何かに入れたい
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUri)

            //選ばれた写真bitmapをfileurisに保存したい
            //fileUris.add(bitmap.toString())
            imageView4.setImageBitmap(bitmap)
        }

        if (requestCode == MainActivity.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUri)
            intent = Intent(this,ShowActivity::class.java)
            imageView4.setImageBitmap(bitmap)
        }
    }


    override fun onRequestPermissionsResult(

        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray

    ) {
        if (requestCode == MainActivity.REQUEST_CODE_PERMISSION) {

            // requestPermissionsで設定した順番で結果が格納されています。
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 許可されたので処理を続行
                takePicture()
            } else {
                // パーミッションのリクエストに対して「許可しない」
                // または以前のリクエストで「二度と表示しない」にチェックを入れられた状態で
                // 「許可しない」を押されていると、必ずここに呼び出されます。
                Toast.makeText(this, "パーミッションが許可されていません。設定から許可してください。", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun cameraTask() {

        Log.d("camera","cameratask")
        checkPermission(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
        if (checkPermission(Manifest.permission.CAMERA) && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            takePicture()
        }
    }

    fun checkPermission(tergerPermissionArray: Array<String>) {

        Log.d("checkpermissionue","checkpermissionue")

        var chechNeededPermissionList = mutableListOf<String>()

        for (tergerPermission in tergerPermissionArray) {
            if (!checkPermission(tergerPermission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, tergerPermission)) {
                    // すでに１度パーミッションのリクエストが行われていて、
                    // ユーザーに「許可しない（二度と表示しないは非チェック）」をされていると
                    // この処理が呼ばれます。
                    Toast.makeText(this, "パーミッションが許可されていません。設定から許可してください。", Toast.LENGTH_SHORT)
                        .show()
                    return
                } else {
                    chechNeededPermissionList.add(tergerPermission)
                }
            }
        }

        if (!chechNeededPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, chechNeededPermissionList.toTypedArray(), MainActivity.REQUEST_CODE_PERMISSION
            )
        }
    }


    fun checkPermission(tergerPermission: String): Boolean {

        Log.d("checkpermissionshita","checkpermissionue")
        return ContextCompat.checkSelfPermission(
            this,
            tergerPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun takePicture() {

        Log.d("takepicture","checkpermissionue")
        val fileName: String = "${System.currentTimeMillis()}.jpg"
        val contentValues: ContentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, fileName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        pictureUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!


        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
        startActivityForResult(intent, MainActivity.REQUEST_CODE_CAMERA)
    }



}
