package app.sano.picchi.eegimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class WalkThroughFragmentActivity : Fragment() {

    var walkThroughType : WalkThroughType? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_walk_through_fragment, container, false)
    }

    /*
    * View の初期化。
    *
    * 表示するフラグメントの種類（WalkThroughType）に応じて View を変更している。
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(WalkThroughTypeKey) }?.apply {
            walkThroughType = WalkThroughType.values().mapNotNull {
                if (getInt(WalkThroughTypeKey) == it.ordinal) it else null}.first()
            initWalkThroughPage(view)
        }

    }

    private fun initWalkThroughPage(argView : View) {

        val linearLayout: FrameLayout = argView.findViewById(R.id.frame_layout)
        val imageView: ImageView = argView.findViewById(R.id.imageView)
        val textView: TextView = argView.findViewById(R.id.title)

        when (walkThroughType) {
            WalkThroughType.First -> {
                linearLayout.setBackgroundResource(R.color.colorAccent)
                imageView.setImageResource(R.drawable.i1)
                textView.text = getText(R.string.title1)
            }
            WalkThroughType.Second -> {
                linearLayout.setBackgroundResource(R.color.colorPrimary)
                imageView.setImageResource(R.drawable.i2)
                textView.text = getText(R.string.title2)
            }
            WalkThroughType.Third -> {
                linearLayout.setBackgroundResource(R.color.colorPrimaryDark)
                imageView.setImageResource(R.drawable.i3)
                textView.text = getText(R.string.title3)
            }
            WalkThroughType.Fourth -> {
                linearLayout.setBackgroundResource(R.color.colorPrimaryDark)
                imageView.setImageResource(R.drawable.i4)
                textView.text = getText(R.string.title3)
            }
            WalkThroughType.Fifth -> {
                linearLayout.setBackgroundResource(R.color.colorPrimaryDark)
                imageView.setImageResource(R.drawable.i5)
                textView.text = getText(R.string.title3)
            }
        }
    }

}
