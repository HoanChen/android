## 依赖
**Step 1.** Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
**Step 2.** Add the dependency
```
implementation 'com.github.zhaoyuehai.android:base:1.0.0'
implementation 'com.github.zhaoyuehai.android:net:1.0.0'
```
## 提供的功能
### MVP基类
* Acitvity/Fragment
* RxJava2订阅管理  
### 网络框架【retrofit2+RxJava2】
* Json转换、拦截验证
* 文件下载、上传进度监听
## Net Library用法
**1.** 在Application的onCreate()中初始化NetHelper
```kotlin
 NetHelper.init(MyNetConfig())
```

```kotlin
class MyNetConfig : NetConfig, ResponseVerify {
    //设置BaseUrl  
    private val baseUrl = BuildConfig.BASE_URL
    //添加okhttp拦截器
    private val interceptors: Array<Interceptor> = arrayOf(TokenInterceptor())
    private val networkInterceptors: Array<Interceptor> = arrayOf(
        HttpLoggingInterceptor(HttpLogger())
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    )

    override fun baseUrl(): String {
        return baseUrl
    }

    override fun interceptors(): Array<Interceptor> {
        return interceptors
    }

    override fun networkInterceptors(): Array<Interceptor> {
        return networkInterceptors
    }

    override fun responseVerify(): ResponseVerify? {
        return this
    }
    
    //全局的Json结果拦截验证
    @Throws(JSONException::class)
    override fun verify(response: String) {
        val jsonObject = JSONObject(response)
        if (jsonObject.has("code") && !jsonObject.isNull("code")) {
            val code = jsonObject.getString("code")
            if (code != "10000") {
                val message = jsonObject.getString("message")
                throw ApiException(code.toInt(), message)
            }
        }
    }
}
```
**2.** 使用NetHelper

```kotlin
val apiService: ApiService = NetHelper.instance.create(ApiService::class.java)
```

  
