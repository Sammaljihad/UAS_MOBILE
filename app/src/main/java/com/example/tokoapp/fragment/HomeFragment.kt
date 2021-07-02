package com.example.tokoapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokoapp.R
import com.example.tokoapp.adapter.AdapterProduk
import com.example.tokoapp.app.ApiConfig
import com.example.tokoapp.model.Produk
import com.example.tokoapp.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var my_rv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()

        return view
    }

    fun displayProduk() {
        Log.d("cekini", "size:" + myProduk.size)
        val layoutManager = LinearLayoutManager(activity)
        my_rv.adapter = AdapterProduk(requireActivity(), myProduk)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        my_rv.layoutManager = layoutManager
    }
    private var myProduk: ArrayList<Produk> = ArrayList()
    fun getProduk() {
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    val arrayProduk = ArrayList<Produk>()
                    for (p in res.produks) {
                        p.discount = 100000
                        arrayProduk.add(p)
                    }
                    myProduk = arrayProduk
                    displayProduk()
                }
            }
        })
    }
    fun init(view: View) {
        my_rv = view.findViewById(R.id.rv_produk)
    }
}
