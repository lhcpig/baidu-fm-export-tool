# baidu-fm-export-tool
export favorite song list from fm.baidu.com

---

这个是将baidu.fm中保存的收藏列表导出为kgl格式的文件。kgl文件可以用于网易云音乐导入歌单

由于百度登录还要输入验证码，比较麻烦。本着小工具就是要快，导出歌单也是一次性功能，就没有登录功能。这个需要用户自己去获取baidu的BDUSS。

在浏览器中登录baidu后，在cookie中会有一个BDUSS。取出来，然后写入config.properties。
最后执行Main就可以了


##注
因为只是一个工具，所以代码写的比较随意，见谅
