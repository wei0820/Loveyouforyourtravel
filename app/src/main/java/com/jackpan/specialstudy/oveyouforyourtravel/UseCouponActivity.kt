package com.jackpan.specialstudy.oveyouforyourtravel

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.jackpan.libs.mfirebaselib.MfiebaselibsClass
import com.jackpan.libs.mfirebaselib.MfirebaeCallback
import com.jackpan.specialstudy.oveyouforyourtravel.Data.CollectionData
import java.util.*


class UseCouponActivity : AppCompatActivity(), MfirebaeCallback {
    override fun getUserLogoutState(p0: Boolean) {

    }

    override fun resetPassWordState(p0: Boolean) {
    }

    override fun getsSndPasswordResetEmailState(p0: Boolean) {
    }

    override fun getFirebaseStorageType(p0: String?, p1: String?) {
    }

    override fun getUpdateUserName(p0: Boolean) {
    }

    override fun getDatabaseData(p0: Any?) {
    }

    override fun getuserLoginEmail(p0: String?) {
    }

    override fun getDeleteState(p0: Boolean, p1: String?, p2: Any?) {
    }

    override fun getFireBaseDBState(p0: Boolean, p1: String?) {
    }

    override fun getuseLoginId(p0: String?) {
    }

    override fun createUserState(p0: Boolean) {
    }

    override fun useLognState(p0: Boolean) {
    }

    override fun getFirebaseStorageState(p0: Boolean) {
    }

    lateinit var mTimeText :TextView
    lateinit var mFirebselibClass: MfiebaselibsClass
    lateinit var mShopTextView: TextView
    lateinit var mMoneyText:TextView
    lateinit var price:String
    lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebselibClass = MfiebaselibsClass(this, this@UseCouponActivity)
        setContentView(R.layout.activity_use_coupon)
        getNameAndPrice()
        mTimeText = findViewById(R.id.timetext)
        mShopTextView = findViewById(R.id.shoptext)
        mMoneyText = findViewById(R.id.moneytext)
        mShopTextView.text = name
        mMoneyText.text = price
        countdowntime()


    }

    fun countdowntime(){
        object : CountDownTimer(10000, 1000) {

            override fun onFinish() {
                mFirebselibClass.deleteData( CollectionData.KEY_URL_FREE+ "/" + MySharedPrefernces.getIsToken(this@UseCouponActivity), getdata())
                Toast.makeText(this@UseCouponActivity,"折價券使用成功！！",Toast.LENGTH_SHORT).show()
                var intent = Intent()
                intent.setClass(this@UseCouponActivity,MemberFreeListViewActivity::class.java)
                startActivity(intent)
                finish()

//                bundle.putString("id",getdata())
//                intent.putExtras(bundle)

//                setResult(Activity.RESULT_OK,intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d(javaClass.simpleName, (millisUntilFinished / 1000).toString())
                mTimeText.text =(millisUntilFinished / 1000).toString()
            }

        }.start()


    }
    fun getNameAndPrice(){
        var bundle: Bundle = intent.extras
        name = bundle.getString("name")
        price = bundle.getString("price")

    }
    fun getdata():String{
        var bundle: Bundle = intent.extras
        Log.d(javaClass.simpleName, bundle.getString("id"))
        return bundle.getString("id")
    }
}
