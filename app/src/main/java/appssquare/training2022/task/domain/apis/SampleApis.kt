package appssquare.training2022.task.domain.apis

import appssquare.training2022.task.domain.model.DemoResponse
import appssquare.training2022.task.domain.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface SampleApis {

    @GET("products")
    fun getMyProducts():Call<DemoResponse<ArrayList<Product>>>

}