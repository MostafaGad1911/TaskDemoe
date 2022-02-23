package appssquare.training2022.task.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appssquare.training2022.task.R
import appssquare.training2022.task.domain.model.DemoResponse
import appssquare.training2022.task.domain.model.Product
import appssquare.training2022.task.domain.settings.Config
import appssquare.training2022.task.view.adapter.ProductsAdapter
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var productsRecycler: RecyclerView
    lateinit var productsProgressBar: ProgressBar
    lateinit var addProductBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViews()
        handleProductsCallback(onProductFailed = {
            it.showToast()
        }, onProductsCalled = {
            var productsAdapter = ProductsAdapter(ctx = this , productsList = it)
            productsRecycler.adapter = productsAdapter
            hideLoading()
        })

    }

    private fun iniViews() {
        productsRecycler = findViewById(R.id.productsRecyclerVew)
        productsProgressBar = findViewById(R.id.productsProgressBar)

        var lytManager = GridLayoutManager(this, 2,
            GridLayoutManager.VERTICAL , false)
        productsRecycler.layoutManager = lytManager
        productsRecycler.addItemDecoration(SpacesItemDecoration(6))

    }

    fun showLoading() {
        productsProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        productsProgressBar.visibility = View.GONE
    }

    private fun handleProductsCallback(
        onProductsCalled: (productsList: ArrayList<Product>) -> Unit,
        onProductFailed: (String) -> Unit
    ) {

        Config.getMyApis().getMyProducts()
            .enqueue(object : Callback<DemoResponse<ArrayList<Product>>> {
                override fun onResponse(
                    call: Call<DemoResponse<ArrayList<Product>>>,
                    response: Response<DemoResponse<ArrayList<Product>>>
                ) {
                    when (response.code()) {
                        200 -> {
                            onProductsCalled(response.body()?.data!!)
                        }

                        else -> {
                            response.errorBody()?.getErrorMessage()
                        }
                    }
                }

                override fun onFailure(call: Call<DemoResponse<ArrayList<Product>>>, t: Throwable) {
                    onProductFailed(t.message.toString())
                }

            })
    }

    fun ResponseBody.getErrorMessage(): String {
        val errorJsonString = this?.string()
        val json: JSONObject = JSONObject(errorJsonString)
        val msg = json.getString("message")
        return msg
    }


    fun String.showToast() {
        Toast.makeText(this@MainActivity, this, Toast.LENGTH_LONG).show()
    }
}