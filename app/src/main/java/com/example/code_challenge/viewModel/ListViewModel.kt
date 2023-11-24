package com.example.code_challenge.viewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.code_challenge.model.Item
import com.example.code_challenge.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {
    private val _itemList: MutableLiveData<Item> = MutableLiveData()
    val itemList: LiveData<Item> get() = _itemList

    fun fetchDataFromApi() {
        Api.retrofitService.getAllData().enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    _itemList.value = response.body()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}


