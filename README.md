# hputimetable

怪兽课表是一款免费、开源的通用型课表软件，可以导入各个学校的课程数据（持续适配中），可以无缝从超级课程表和课程格子（暂不支持）的账户以及课程码中导入数据，并且有桌面小部件和学校专区，快来体验吧，感觉好用就分享给你的朋友吧~

## 注意事项

**本项目开源，你可以基于此项目进行二次开发，但是必须遵循以下规则：**

- 1.不能使用我的签名
- 2.如果你要发布的话，请务必修改包名
- 3.务必修改bugly的配置，否则你的崩溃日志会记录在我的后台
- 4.升级时，请修改`tools/VersionTools.java`中的版本号
- 5.在你没有获得我的许可时请移除全国大学课程适配平台相关代码，其他模块代码你可以自由使用，参见[全国大学课程适配平台-授权须知](https://github.com/zfman/CourseAdapter)
- 6.基于此项目进行的二次开发的软件尽量注明原项目的出处，示例如下：
> 此项目基于[zfman](https://github.com/zfman)的[怪兽课表](https://github.com/zfman/hputimetable)进行二次开发

特别要注意的是：未授权时请务必移除"适配平台"相关页面以及代码，违者必究!

## 编译失败

下载项目后直接编译会失败,请打开`build.gradle(Module:app)`删除如下代码片段
```gradle
// 签名配置
    signingConfigs {
        release {
            storeFile file("./keystore/my.kestore")
            storePassword "5271314"
            keyAlias "liu"
            keyPassword "5271314"
        }
    }

    buildTypes {
        release {
            signingConfig signingConfigs.release
        }
    }
```

## 如何修改Bugly配置

Bugly提供崩溃日志上报、热修复、全量更新等功能，如果你基于此项目进行二次开发，务必要修改Bugly的配置。

- 在[Bugly官网](https://bugly.qq.com/v2/index)申请appid、appkey
- 找到`MyApplicationLike.java`,修改appid,如下：
```java
    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Beta.canNotifyUserRestart = true;
        Bugly.init(getApplication(), "换成自己的", false);
    }
```
- 找到`build.gradle(Module:app)`,修改appid、appkey,如下：
```java
bugly {
    appId = '注册时分配的AppID'
    appKey = '注册时分配的AppKey'
}
```

## Images

### v1.1.1

<img src="https://raw.githubusercontent.com/zfman/hputimetable/master/resource/images/v1.1.1/img1.jpg" width="30%"/><img src="https://raw.githubusercontent.com/zfman/hputimetable/master/resource/images/v1.1.1/img2.jpg" width="30%"/><img src="https://raw.githubusercontent.com/zfman/hputimetable/master/resource/images/v1.1.1/img3.jpg" width="30%"/>

### v1.1.1 `2018/11/14`

- 网页可缩放
- 新增情侣模式
- 新增调试控制台
- 优化小部件日视图
- 优化设置页面
- 减少主界面刷新频率
- 减少数据刷新延迟时间

### v1.1.0 `2018/10/29`

- 启动速度优化
- 优化UI、简化操作、完善小部件
- 简化[同类型教务系统导入]流程,不再需要输入网址
- 封装课程适配组件,课程适配平台对外开放
- 申请适配时URL自动填充
- 修复默认课表未创建导致的空指针异常
- 基本修复内存紧张时页面被回收时的崩溃问题
- 修复剪切板空指针异常
- 修复编辑时教室信息不保存问题

### v1.0.9 `2018/10/19`

- 修复源码上传不全的问题
- 日视图点击进入详情页
- 自动检查更新功能优化

### v1.0.8 `2018/10/18`

- 全面提升加载性能
- 修复课表显示错乱问题
- 修复超表导入失败问题
- 简单修复桌面插件重复的问题,可调整大小
- 优化UI，主页为滑动的两个Tab,主页显示日视图
- 简化流程，导入成功后提醒是否设置为当前课表
- 增加学校课程导入和申请适配功能，可导入同类型教务系统
- 课表详情页可编辑和删除
- 增加适配公告，哪个学校被适配了一目了然
- 河南理工大学专属服务被隐藏，可通过在搜索框查找[河南理工]找到

`v1.0.7`是预览版，在每个正式版发布之前我都会邀请一部分人参与测试，`v1.0.7`导入专业以及导入分享功能有BUG

### v1.0.6 `2018/8/31`

- 修复导入超表时出现的崩溃
- 修复补丁应用时出现的崩溃
- 修复应用更新失败的问题
- 修复桌面插件不更新问题

### v1.0.5 `2018/8/25`

- 修复课表页面可能会出现的崩溃问题（周次大于20时）；
- 修复多课表的详情不显示周次问题；
- 增加非本周隐藏设置、主题设置；
- 切换班级页面加载数据时显示提示；

### v1.0.4 `2018/8/24`

- 增强外校用户的使用体验！
- 新增从超表课程码中导入；
- 新增多课表管理；
- 优化视觉效果

## Resource

- [效果展示](https://github.com/zfman/hputimetable/wiki/%E6%95%88%E6%9E%9C%E5%B1%95%E7%A4%BA)

- [在酷安下载安装包](https://www.coolapk.com/apk/com.zhuangfei.hputimetable)

- [TimetableView](https://github.com/zfman/TimetableView)
  > 一个开源的、完善的、简洁的课程表控件

- [全国大学生课程适配平台](https://github.com/zfman/CourseAdapter)
  > 使用请需要先向开发者申请授权

- [全网第二好的Android课程表控件](https://blog.csdn.net/column/details/22816.html)
  > 课程表控件实现原理以及使用手册(10篇+)

- [timetable](https://github.com/zfman/api-demo/tree/master/timetable)
  > 河南理工大学课程库(非官方)对外开放接口

- [全国大学课程适配平台-授权须知](https://github.com/zfman/CourseAdapter)
  > 课程适配平台，接入前需要取得开发者授权

## 开源库

这里简单的列出来，需要的话去搜索详细信息就可以了

```
    //View、事件绑定库
    api 'com.jakewharton:butterknife:8.5.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'

    //图文混排
    api 'com.zhuangfei:ExpandEditText:1.0.0'
    api 'com.zhuangfei:GeneralPage:1.0.0'

    //课表相关
    api 'com.zhuangfei:TimetableView:2.0.6'//课表控件
    compile 'com.zhuangfei:SuperBox:1.0.5' //超级课程表授权库
    compile 'cn.yipianfengye.android:zxing-library:2.2'//扫码库

    api 'cn.aigestudio.wheelpicker:WheelPicker:1.1.2'
    api 'com.contrarywind:Android-PickerView:3.2.7' //节次选择库

    //网络请求库
    api 'com.squareup.retrofit2:retrofit:2.0.2'
    api 'com.squareup.retrofit2:converter-gson:2.0.2'

    //数据库
    api 'org.litepal.android:core:1.6.1'

    //Bugly相关库
    // 多dex配置
    api 'com.android.support:multidex:1.0.1'
    //注释掉原有bugly的仓库
    //api 'com.tencent.bugly:crashreport:latest.release'//其中latest.release指代最新版本号，也可以指定明确的版本号，例如1.3.4
    api 'com.tencent.bugly:crashreport_upgrade:1.3.4'
    api 'com.tencent.bugly:nativecrashreport:latest.release'

    //权限库
    api 'com.lovedise:permissiongen:0.0.6'

    //Toast
    api 'com.github.GrenderG:Toasty:1.3.0'

    //cardView
    api 'com.android.support:cardview-v7:27.1.1'
```

## About-Me
- [https://blog.csdn.net/lzhuangfei](https://blog.csdn.net/lzhuangfei)
- [https://github.com/zfman](https://github.com/zfman)
- QQ:1193600556
