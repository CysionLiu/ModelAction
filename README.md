### ModelAction

#### This is a light library to valid, control and cache the data from net,and its inner http-implementation is alternative, and here volley and retrofit are provided.

#### The aim of the lib is as the follow three aspects:
- to valid,parse or lighten the response data from net , to decrease the work of controller such as activity;
- to maintain the consistence of one request-response procedure,especially in the condition of numbers of request by the same url with the different request body.
- to cache the data just by the execute code , decrease the coupling between controller and model in MVC.

### The steps to use action:
1. to create BaseAction class extends the MvcAction class ,which can do some common things,such as headers;
2. to create a javabean related to a json;
3. to create a action extends BaseAction;
 ~~
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
             if(valid){
                        listener.onSuccess(aResult,aTaskId);
                         return true;
                    }
                return false;

       }
 ~~
 4. new GetAction(this).taskId(100).execute(DataState.CACHE_FIRST);
