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
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.database.getStringOrNull
import androidx.viewpager.widget.ViewPager
import app.sano.picchi.eegimage.TutorialActivity.Companion.showIfNeeded
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show.*
import kotlinx.android.synthetic.main.activity_walk_through_fragment.*
import java.io.ByteArrayOutputStream
import java.io.File

class MainActivity : AppCompatActivity() {

    //カメラ
    lateinit var pictureUri: Uri
    var bitmap: Bitmap? = null

    //アルバムとカメラ
    companion object {
        const val REQUEST_CODE_PHOTO: Int = 123
        const val REQUEST_CODE_PERMISSION: Int = 100
        const val REQUEST_CODE_CAMERA: Int = 200
    }



    var scale: Float = 0.0f

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: CustomAdapter
    lateinit var indicatorArea: LinearLayout
    private var indicatorViewList: ArrayList<View> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //最初のウォーク周りレイアウトのviewpagerのエラー
        scale = resources.displayMetrics.density

        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = CustomAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until indicatorViewList.size) {
                    if (i == position)
                        indicatorViewList[i].background = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.i4,
                            null
                        )
                    else
                        indicatorViewList[i].background = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.i5,
                            null
                        )

                }
            }
        })

        //ウォークスルーのページ数だけインディケータを表示
        indicatorArea = findViewById(R.id.indicator_area)

        val indicatorWidth = resources.getDimension(R.dimen.activity_horizontal_margin).toInt()
        val indicatorHeight = resources.getDimension(R.dimen.walk_through_indicator_area_height).toInt()
        val indicatorMarginStart =
            resources.getDimension(R.dimen.activity_horizontal_margin).toInt()
        for (i in 0 until WalkThroughType.values().size) {
            var view = View(this)
            if (i == 0) {
                view.background = getDrawable(R.drawable.i4)
                val layoutParams = LinearLayout.LayoutParams(indicatorWidth, indicatorHeight)
                view.layoutParams = layoutParams as ViewGroup.LayoutParams?
            } else {
                view.background = getDrawable(R.drawable.i5)
                val layoutParams = LinearLayout.LayoutParams(indicatorWidth, indicatorHeight)
                layoutParams.marginStart = indicatorMarginStart
                view.layoutParams = layoutParams
            }
            indicatorArea.addView(view)
            indicatorViewList.add(view)
        }
    }

    fun start(view: View) {

        val intent = Intent(this,ImageVerificationActivity::class.java)
        startActivity(intent)
        //cameraTask()
//        val intent: Intent =
//            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_CODE_PHOTO)
//        Log.d("camera","camera")
    }




}
