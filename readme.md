### ModelAction
---
[中文版戳这里](/readme_cn.md)
#### A light Android library to **validate**, **trace** and **cache** the data from network,and its inner http-implementation is alternative, here volley and retrofit are provided.
> It should be pointed out that the lib is just for ease the GET and POST in http for common condition and in some special ones,the raw function of http-lib should in use.
### Aim:
---
- to **validate,parse or lighten** the response data from network , to decrease the duty of controller;
- to **trace** the consistence of one request-response procedure,especially in high-frequency condition of the same url with the different request body.
- to **cache** the data just by the command code from controller, to ease usage and decrease the duty of controller.

### Usage:
---

1. to create a BaseAction class extends the MvcAction class ,which can do some common things,such as headers:

  		@Override
    	protected Map<String, String> getHeader() {
        	Map<String,String> headers = new HashMap<>();
       		headers.put("test1","one");
        	headers.put("test2","two");
        	return headers;
    	}
2. to create a javabean related to a json;
3. to create a action extends BaseAction;

        @Override
      	 protected String getUrl() {
           return Urls.BASE+Urls.FIND_COL;
      	 }

      	 @Override
      	 protected int getHttpMethod() {
      	     return Method_GET;
      	 }

       	@Override
      	 protected boolean getTargetDataFromJson(String aResult, long aTaskId) {
             if(valid(){
                   listener.onSuccess(targetResult,aTaskId);
                   return true;
             }
            return false;

       }
> **Note: the operation in getTargetDataFromJson() should be the same as above;**
4. should invoke MvcPointer.init(actionListener, true, httpProxy);
5. new GetAction(listener).params(map).taskId(100).execute(DataState.CACHE_FIRST);

		private TActionListener mActionListener = new TActionListener() {
        @Override
        public void onSuccess(Object obj, int taskId) {
            switch (taskId) {
                 case MultiArticlesAction.NORMAL:
                   //cache has done in action
                    break;
                case MultiArticlesAction.LOAD_MOER:
                   //do some work
                    break;
                case MultiArticlesAction.REFRESH:
                    //do some work
                    break;
            }
        }


### Customization:
---
- define one request's cache expiration by override in one sub Action class:

		@Override
    	protected int getKeepTime() {
        	return 10;//TimeUnits:s
    	}
- define a special header in a sub Action class by override:

	    protected Map<String, String> getHeader() {
        if (mHeader == null) {
            mHeader = new HashMap<>();
        }
        return mHeader;
    }

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
