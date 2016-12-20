# OrangeRetrofitDemo

##OrangeRetrofit
基于Retrofit封装，API透明化，使用时不涉及Retrofit细节。
链式调用，使用简单：
```
HttpRequest.builder(
	new PostJsonBuilder()
		.url(ApiUrl.LOGIN)
                .defaultHeaders()
                .param("mobile", "15818703208")
                .param("password", Md5.md5Toword("123456789"))
                .modelClazz(LoginModel.class)
                .callback(callback)).startCall();
```
```
new PostJsonBuilder()
	.url(ApiUrl.WALLET_ACCOUNT)
        .modelClazz(WalletAccountModel.class)
        .callback(callback).create().startCall();
```

请查看Demo源码


##Use

To get **OrangeRetrofit** into your build:
####Gradle
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
dependencies {
	compile 'com.github.ashima0512:OrangeRetrofitDemo:v1.0.0'
}
```
####Maven
Step 1. Add the JitPack repository to your build file
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Step 2. Add the dependency
```
<dependency>
	<groupId>com.github.ashima0512</groupId>
	<artifactId>OrangeRetrofitDemo</artifactId>
	<version>v1.0.0</version>
</dependency>
```


