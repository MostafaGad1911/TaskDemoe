package appssquare.training2022.task.domain.settings

import appssquare.training2022.task.domain.apis.SampleApis
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Config {
    companion object {
        lateinit var retrofitDemo: Retrofit
        const val BASE_URL = "https://android-training.appssquare.com/api/"

        private val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        fun getMyApis(): SampleApis {
            return getClient().create(SampleApis::class.java)
        }

        private fun getClient(): Retrofit {
            retrofitDemo = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofitDemo
        }
    }
}