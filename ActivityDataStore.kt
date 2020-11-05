package view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_data_store.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import modle.controler.DataStore
import modle.controler.UserManager
import no3ratii.mohammad.dev.app.livedata.R

class ActivityDataStore : AppCompatActivity() {

    lateinit var userManager: UserManager
    var age = 0
    var name = ""
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)
        userManager = UserManager(this)

//        DataStore.setValue("mohammad", "USER_NAME_TEST")

        val test = DataStore.getValue("USER_NAME_TEST_2")
        test.asLiveData().observe(this, Observer {
            Toast.makeText(this, "" + it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun test1() {
        val test = userManager.dataStore.data.map {
            it[preferencesKey<Int>("USER_AGE_TEST")]
        }

        test.asLiveData().observe(this, Observer {
            Toast.makeText(this, "" + it.toString(), Toast.LENGTH_SHORT).show()
        })

        btn_save.setOnClickListener {
            GlobalScope.launch {
                userManager.dataStore.edit {
                    it[preferencesKey<Int>("USER_AGE_TEST")] = 18
                }
            }
        }
    }

    private fun singleData() {

        val dataStore = this.createDataStore(name = "user_prefs")

        GlobalScope.launch {
            dataStore.edit {
                it[preferencesKey<Int>("USER_AGE_11")] = 18
            }
        }

        val test = dataStore.data.map {
            it[preferencesKey<Int>("USER_AGE_11")]
        }

        test.asLiveData().observe(this, Observer {
            Toast.makeText(this, "" + it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun buttonSave() {

        btn_save.setOnClickListener {
            name = et_name.text.toString()
            age = et_age.text.toString().toInt()
            val isMale = switch_gender.isChecked

            //Stores the values
            GlobalScope.launch {
                userManager.storeUser(age, name, isMale)
            }
        }


    }

    private fun observeData() {
        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, Observer {
            age = it
            tv_age.text = it.toString()
        })

        //Updates name
        userManager.userNameFlow.asLiveData().observe(this, Observer {
            name = it
            tv_name.text = it.toString()
        })

        //Updates gender
        userManager.userGenderFlow.asLiveData().observe(this, Observer {
            gender = if (it) "Male" else "Female"
            tv_gender.text = gender
        })
    }


}