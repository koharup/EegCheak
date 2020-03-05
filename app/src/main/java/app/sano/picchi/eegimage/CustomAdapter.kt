package app.sano.picchi.eegimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

//ウォークスルーに表示するフラグメントの種類
enum class WalkThroughType{
    First,
    Second,
    Third,
    Fourth,
    Fifth
}

const  val WalkThroughTypeKey = "WalkThroughType"

class CustomAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm){

    override fun getCount(): Int = WalkThroughType.values().size

    override fun getItem(position: Int): Fragment {
        val fragment = WalkThroughFragmentActivity()
        fragment.arguments = Bundle().apply {
            putInt(WalkThroughTypeKey,
                WalkThroughType.values().mapNotNull { if (position == it.ordinal) it.ordinal else null}.first())
        }
        return fragment
    }
}