### ModelAction
---
[click me](/readme.md)
#### 一个轻量级的安卓网络请求操作库，主要是用来跟踪网络请求、校验和缓存来自网络的数据；底层的http实现可以灵活替换，目前提供了volley和Retrofit的实现方式。
> 需要指出的是，本库主要是针对项目中相对通用的GET和POST请求，某些特殊情况，可以直接使用底层的http实现，不过仍然建议下述自定义中提供的方式。
### 目的:
---
- 减少controller层的职责，将数据校验、解析操作回归到Model层;
- 追踪每个请求，灵活控制请求，并能适应某些应用场景下单个url不同传参时的高频请求的数据一致性问题;
- 解脱缓存操作，只需在网络请求时指定缓存操作码.便可内部实现对应缓存机制，且能本地实现缓存时间控制；

### 使用:
---


> 依赖导入:
```
repositories {
    jcenter()
}
dependencies {
     compile 'com.cysion:Action:1.0.0'
    ...
}
```
1. 初始化MvcPointer.init(actionListener, debug, httpProxy);httpProxy，http处理策略类，可根据需要替换，默认提供Retrofit的通用实现,sample中有volley的实现；
2. 创建一个抽象BaseAction，继承MvcAction,并重写某些方法，指定通用的数据，比如header:

  		@Override
    	protected Map<String, String> getHeader() {
        	Map<String,String> headers = new HashMap<>();
       		headers.put("test1","one");
        	headers.put("test2","two");
        	return headers;
    	}
3. 创建javabean;
4. 创建某个子类action，继承BaseAction，实现以下方法;

        @Override
      	 protected String getUrl(int taskid) {
           return Urls.BASE+Urls.FIND_COL;
      	 }

      	 @Override
      	 protected int getHttpMethod(int taskid) {
      	     return Method_GET;
      	 }

       	@Override
      	 protected boolean getTargetDataFromJson(String aResult, long aTaskId) {
             if(valid(){
                   listener.onSuccess(*targetResult*,aTaskId);
                   return true;
             }
            return false;

       }
> **注意: getTargetDataFromJson()方法的操作需要和上述状况一致，aResult可能来自网络，也可能是缓存;**

5. controller中调用：new GetAction(mActionListener).params(map).taskId(100).execute(DataState.CACHE_FIRST);

		private TActionListener mActionListener = new TActionListener() {
        @Override
        public void onSuccess(Object obj, long taskId) {
            switch ((int)taskId) {
                 case Action.NORMAL:
                   //根据缓存码，已在内部实现了缓存
                    break;
                case Action.LOAD_MOER:
                   //do some work
                    break;
                case Action.REFRESH:
                    //do some work
                    break;
            }
        }


### 自定义:
---
- 子类重写如下方法，来定义缓存有效期:

		@Override
    	protected int getKeepTime() {
        	return 10;//TimeUnits:s
    	}
- 为特定请求定义header，如下:

	    protected Map<String, String> getHeader() {
        if (mHeader == null) {
            mHeader = new HashMap<>();
        }
        return mHeader;
    }
- 重写下列方法，为某个请求设定特定的HttpProxy，如下:

	    protected HttpProxy getHttpProxy() {
              return MvcPointer.getHttpProxy();
       }
- todo...   

### License
---
Copyright 2017 CysionLiu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
