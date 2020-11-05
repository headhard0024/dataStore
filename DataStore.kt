package modle.controler

import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import modle.base.G
import modle.controler.DataStore.dataStore

object DataStore {

    val dataStore = G.getContext().createDataStore("sharid_prifranses")

    fun setValue(value: String, key: String) {
        GlobalScope.launch {
            dataStore.edit {
                it[preferencesKey<String>(key)] = value
            }
        }
    }

    fun getValue(key: String): Flow<String?> {

        return dataStore.data.map { pre ->
            pre[preferencesKey<String>(key)] ?: ""
        }

        //        data.asLiveData().observe(lifecycleOwner, Observer {
        //        value = it ?: "noData"
        //        Log.i("MONO", "A | $value")
        //        })
    }
}