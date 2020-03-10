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
import kotlinx.android.synthetic.main.activity_show.*
import java.io.ByteArrayOutputStream
import java.io.File

val uriList = mutableListOf<Uri>()

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

        Toast.makeText(this, "写真を5枚選択してください", Toast.LENGTH_LONG).show();
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_PICK
        this.startActivityForResult(Intent.createChooser(intent, "Choose Photo"),
            REQUEST_CODE_PHOTO)

    }

    fun start(view: View){
        val intent = Intent(this,ShowActivity::class.java)
        startActivity(intent)
    }


    fun camera(view: View){
        cameraTask()
    }

    //onCreateに書いたけどあとでわかるように残しとく
    //画像を複数選択するときの処理
    fun albums(view: View){
        Toast.makeText(this, "写真を5枚選択してください", Toast.LENGTH_LONG).show();
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_PICK
        this.startActivityForResult(Intent.createChooser(intent, "Choose Photo"),
            REQUEST_CODE_PHOTO)
    }


    //画像を一枚選択するときの処理(albumと同じ,違う書き方してみただけ)
    fun album2(view: View){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        this.startActivityForResult(Intent.createChooser(intent, "Choose Photo"),
            REQUEST_CODE_PHOTO)
    }


    //画像を一枚選択するときの処理(album２と違う書き方してみただけ)
    fun album(view: View){
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

        Log.d("onActivityresult","onActivityresult")

        if (requestCode == MainActivity.REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
            /*pictureUri = data?.data!!
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUri)
            Log.d("test",pictureUri.toString())
            imageView4.setImageBitmap(bitmap)*/

            val itemCount = data?.clipData?.itemCount ?: 0
            //val uriList = mutableListOf<Uri>()
            for (i in 0..itemCount - 1) {
                val uri = data?.clipData?.getItemAt(i)?.uri
                uri?.let { uriList.add(it) }
            }

            Log.d("datatest",data.toString())

            Log.d("test",uriList.toString())
            image1.setImageURI(uriList[0])
            image2.setImageURI(uriList[1])
            image3.setImageURI(uriList[2])
            image4.setImageURI(uriList[3])
            image5.setImageURI(uriList[4])


        }

        /*カメラを選んだ時に表示するとこ(今回はカメラ使わないからいらない)
        if (requestCode == MainActivity.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUri)
            intent = Intent(this,ShowActivity::class.java)
            imageView4.setImageBitmap(bitmap)
        }*/
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
