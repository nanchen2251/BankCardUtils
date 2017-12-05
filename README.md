# BankCardUtils
根据银行卡号 获取 银行卡类型、银行名称和银行编码 自动格式化银行卡号、手机号、身份证号输入的工具类

## 效果图<br>
![](https://github.com/nanchen2251/BankCardUtils/blob/master/GIF.gif)

#### ⊙开源不易，希望给个star或者fork奖励
#### ⊙拥抱开源：https://github.com/nanchen2251/
#### ⊙交流群（拒绝无脑问）：118116509 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=e6ad4af66393684e1d0c9441403b049d2d5670ec0ce9f72150e694cbb7c16b0a"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="Android神技侧漏交流群" title="Android神技侧漏交流群"></a>( 点击图标即可加入 )<br>

## 特点
- 支持自动根据银行卡号获取出银行名称、银行编码、银行卡类型
- 银行相关信息都比较全，无需每次去都 xls 类型的 bin 文件
- 支持自动格式化手机号、身份证号、银行卡号输入
  - 手机号：xxx xxxx xxxx
  - 身份证号：xxxxxx xxxx xxxx xxxx
  - 银行卡号：xxxx xxxx xxxx xxxx
  
## 使用方法
#### 1、添加依赖<br>
##### Step 1. Add it in your root build.gradle at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
##### Step 2. Add the dependency
```java
dependencies {
	        compile 'com.github.nanchen2251:BankCardUtils:1.0.2'

	}
```
#### 2、使用方式最好参照 demo <br>
```java
BankInfoUtil mInfoUtil = new BankInfoUtil(cardNum); // 把银行卡号作为参数传入
String bankName = mInfoUtil.getBankName(); // 可以获取银行名称
String bankId = mInfoUtil.getBankId();  // 可以获取银行编码
String bankCardType = mInfoUtil.getCardType(); // 可以获取卡片类型
```

### 关于作者
    南尘<br>
    四川成都<br>
    [其它开源](https://github.com/nanchen2251/)<br>
    [个人博客](https://nanchen2251.github.io/)<br>
    [简书](http://www.jianshu.com/u/f690947ed5a6)<br>
    [博客园](http://www.cnblogs.com/liushilin/)<br>
    交流群：118116509<br>
    欢迎投稿(关注)我的唯一公众号，公众号搜索 nanchen 或者扫描下方二维码：<br>
    ![](http://images2015.cnblogs.com/blog/845964/201707/845964-20170718083641599-1963842541.jpg)


> 1024 - 梦想，永不止步!  
爱编程 不爱Bug  
爱加班 不爱黑眼圈  
固执 但不偏执  
疯狂 但不疯癫  
生活里的菜鸟  
工作中的大神  
身怀宝藏，一心憧憬星辰大海  
追求极致，目标始于高山之巅  
一群怀揣好奇，梦想改变世界的孩子  
一群追日逐浪，正在改变世界的极客  
你们用最美的语言，诠释着科技的力量  
你们用极速的创新，引领着时代的变迁  
  
------至所有正在努力奋斗的程序猿们！加油！！  
    
## Licenses
```
 Copyright 2017 nanchen(刘世麟)

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
