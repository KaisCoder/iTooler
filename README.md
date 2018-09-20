# iTooler
## iTooler 引用

- 加入JitPack仓库依赖。
```
  allprojects {
      repositories {
          maven { url 'https://jitpack.io' }
      }
  }
```
- 在要使用的项目的build.gradle中。添加依赖：
```
dependencies {
    ...
    implementation 'com.github.adair-kit:iTooler-kotlin:0.2'
}
```

## iTooler-kotlin 初始化
 
1.  iTooler-kotlin 工具包必须在程序的Application 类中进行初始化
```
  @Override
  public void onCreate() {
    super.onCreate();
    iTooler.INSTANCE.init(this).isDebug(true, "Sample").initOther(this);
  }
```
2.  init(this) 是对Context进行初始化也是对整体工具包的初始化
3.  isDebug(true,"Sample") 是对Logger日志的初始化,Sample为需要打印日志的TAG。同时Sample 也为项目创建文件时的根文件夹
4.  initOther(this) 是对其它工具类的一些初始化,目前仅对设备号的初始化,保证每台手机每次获取的设备号都是同一个

##  iTooler-kotlin 使用

1.  iToaster 的使用
```
  iToaster.INSTANCE.showShort("iToaster Test");
```
2.  iLogger 的使用
```
  iLogger.INSTANCE.e();
  iLogger.INSTANCE.e("iLogger Test");
```
3.  iUuider 的使用
```
  iUuider.Companion.deviceUuid().toString();
```
4.  iFileUtil 的使用(*ps:这里的images 为文件夹名称，当然你也可以自定义名字*)
```
  iFileUtil.INSTANCE.isFilePath("images");
```
