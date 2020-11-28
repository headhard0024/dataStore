package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_add.*
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.data.User
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.base.G
import no3ratii.mohammad.dev.app.notebook.base.G.context

class FragmentAdd : AppCompatActivity() {

    private lateinit var userViewModel: ListViewModel
    private var mHourStartTime: Int = 0
    private var mHourEndTime: Int = 0
    private var mMinutesStartTime: Int = 0
    private var mMinutesEndTime: Int = 0
    private var startTime: String = ""
    private var endTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)
        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        initialize()

    }

    private fun initialize() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = "ثبت اطلاعات";

        timerInitialize()

        btnOk.setOnClickListener {
            startTime = "$mHourStartTime:$mMinutesStartTime"
            endTime = "$mHourEndTime:$mMinutesEndTime"
            insertUserToDataBase(edtTitle.text.toString(),edtDesc.text.toString() , startTime , endTime)
        }
    }

    private fun insertUserToDataBase(name: String , family: String , startTime: String , endTime: String) {
        if (checkForEmpty(name, family)) {
            val user = User(0, name, family,startTime,endTime , false)
            userViewModel.addUser(user)
            val intent = Intent(context, ActivityMain::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(context, "لطفا متن را وارد کنید", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForEmpty(name: String, family: String): Boolean {
        return name.isNotEmpty() || family.isNotEmpty()
    }

    private fun timerInitialize() {

        hourStartTime.maxValue = G.MAX_HOUR_TIME
        hourEndTime.maxValue = G.MAX_HOUR_TIME
        minutesStartTime.maxValue = G.MAX_MINUTES_TIME
        minutesEndTime.maxValue = G.MAX_MINUTES_TIME

        hourStartTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mHourStartTime = i2
        }
        hourEndTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mHourEndTime = i2
        }
        minutesStartTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mMinutesStartTime = i2
        }
        minutesEndTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mMinutesEndTime = i2
        }
    }

    //back to mainActivity
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(context, ActivityMain::class.java)
        startActivity(intent)
        finish()
        return true;
    }
}